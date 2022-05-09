<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="bg-dark bg-gradient text-white">
    <div class="container footer-container">
        <p class="p-3 m-0">
            Electromechanics shop &copy; 2022 - S. Bastasin, S. Bortolin, G. Ceccon, D. Colussi, G. Czaczkes, M. Lando, G. Nicoletti, A. Pastore, M. Pozzer
        </p>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
<script src="<c:url value="/js/script.js"/>"></script>
<script>
    const searchForm = document.getElementById('searchForm');
    const searchAutocompleteInput = document.getElementById('searchAutocompleteInput');
    const searchAutocompleteMenu = document.getElementById('searchAutocompleteMenu');

    const popperInstance = Popper.createPopper(searchAutocompleteInput, searchAutocompleteMenu, {
        placement: 'bottom-start',
    });

    searchAutocompleteInput.addEventListener('keyup', (e) => {
        let formData = new FormData(searchForm);
        if (formData.get('q').length < 3) {
            searchAutocompleteMenu.innerHTML="";
            searchAutocompleteMenu.classList.remove("show");
            return;
        }
        let xmlhttp=new XMLHttpRequest();
        xmlhttp.onreadystatechange=function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    let jsonData = JSON.parse(this.responseText.toString());
                    if(jsonData.products.length > 0) {
                        let inner = "";
                        let keys = jsonData.products.flatMap(x => {
                            let split = x.name.split(' ');
                            console.log(split);
                            return split.map((x, i) => split.slice(0,i).join(' '));
                        }).filter(x => x !== null && x.length > 0);
                        keys = new Set(keys);
                        for (const key of keys) {
                            inner += "<li><a class='dropdown-item' href='#' data-autocomplete='"+key+"' >" + key + "</a></li>";
                        }
                        if(keys.size > 0) {
                            inner += "<li><hr class='dropdown-divider'></li>";
                        }
                        inner += "<li><h6 class='dropdown-header'>Product</h6></li>";

                        for (const product of jsonData.products) {
                            let alias = product.alias;
                            let name = product.name;
                            let price = product.finalSalePrice;
                            let url = '<c:url value="/products/details/"/>'+alias;
                            inner+="<li><a class='dropdown-item' href='"+url+"'><i>"+name+"</i> <small>Price: "+price+"€</small></a></li>";
                        }
                        searchAutocompleteMenu.innerHTML = inner;
                        searchAutocompleteMenu.classList.add("show");

                        popperInstance.update();
                        searchAutocompleteMenu.querySelectorAll("a[data-autocomplete]").forEach(li => {
                            li.addEventListener('click', (e) => showElement(e));
                        });
                    }
                }
            }
        };
        xmlhttp.open("POST",'<c:url value="/products/suggest"/>',true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send(new URLSearchParams(formData));
    });
    function showElement(e) {
        searchAutocompleteInput.value = e.target.getAttribute('data-autocomplete');
        //searchForm.submit();
    }
</script>