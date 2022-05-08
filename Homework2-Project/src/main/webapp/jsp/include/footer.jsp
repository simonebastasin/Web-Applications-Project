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
                        for (const product of jsonData.products) {
                            let alias = product.alias;
                            let name = product.name;
                            let url = '<c:url value="/products/details/"/>'+alias;
                            inner+="<li><a class='dropdown-item' href='"+url+"'>"+name+"</a></li>";
                            //inner += "<li><a class='dropdown-item' href='#' onclick='showElement(\"" + name + "\")'>" + name + "</a></li>";
                        }
                        searchAutocompleteMenu.innerHTML = inner;
                        searchAutocompleteMenu.classList.add("show");
                        popperInstance.update();
                    }
                }
            }
        };
        xmlhttp.open("POST",'<c:url value="/products/suggest"/>',true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send(new URLSearchParams(formData));
    });
    function showElement(str) {
        searchAutocompleteInput.value = str;
        //document.getElementById("form").submit();
    }
</script>