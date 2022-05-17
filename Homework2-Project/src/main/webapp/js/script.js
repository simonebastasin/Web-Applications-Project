function printDiv(divId) {
    var printContents = document.getElementById(divId).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}

function parseServletError(message) {
    const parser = new DOMParser();
    const floatingElement = parser.parseFromString(message, 'text/html');
    return [...floatingElement.body.getElementsByTagName('p')].map(i => i.innerText).join(" ");
}

function bootstrapAlert(message, type, placeholder) {
    var wrapper = document.createElement('div');
    wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

    placeholder.append(wrapper);

    setTimeout(function(){
        wrapper.remove();
    }, 10000);
}

function populateForm(form, data, basename) {
    for (const key in data) {
        if (!data.hasOwnProperty(key)) {
            continue;
        }

        let name = key;
        let value = data[key];

        if ('undefined' === typeof value)
            value = '';

        if (null === value)
            value = '';

        // add basename
        if (typeof(basename) !== "undefined")
            name = basename + "-" + key;

        if (value.constructor === Array) {
            if(typeof(basename) !== "undefined")
                name += '[]';
        } else if(typeof value == "object") {
            if(Object.keys(value).length === 1) {
                if (typeof(basename) !== "undefined")
                    name += "-" + Object.keys(value)[0];
                value = value[Object.keys(value)[0]];
            } else {
                if (typeof(basename) !== "undefined")
                    populateForm(form, value, name);
                else
                    populateForm(form, value);
                continue;
            }
        }

        // only proceed if element is set
        let element = form.elements.namedItem(name);
        if (! element) {
            continue;
        }

        let type = element.type || element[0].type;

        switch(type ) {
            default:
                element.value = value;
                break;

            case 'radio':
            case 'checkbox': {
                let values = value.constructor === Array ? value : [value];
                for (let j = 0; j < element.length; j++) {
                    element[j].checked = (values.findIndex(it => it.toString() === element[j].value) > -1);
                }
                break;
            }
            case 'select-multiple': {
                let values = value.constructor === Array ? value : [value];
                for (let j = 0; j < element.options.length; j++) {
                    element.options[j].selected = (values.findIndex(it => it.toString() === element.options[j].value) > -1);
                }
                break;
            }
            case 'select':
            case 'select-one':
                element.value = value.toString() || value;
                break;

            case 'date':
                element.value = new Date(value).toISOString().split('T')[0];
                break;
        }

    }
}

const searchForm = document.getElementById('searchForm');
const searchAutocompleteInput = document.getElementById('searchAutocompleteInput');
const searchAutocompleteMenu = document.getElementById('searchAutocompleteMenu');
const logout=document.getElementById("logoutButton");
const cartButton=document.getElementById("navbarDropdownCart");
const payment=document.getElementById("confirmPayment");
const dropdownMenuLogin = document.getElementById('dropdownMenuLogin');
const loginForBuy = document.getElementById('loginForBuy');
let carousels = document.querySelectorAll('.carousel-category');

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
                        let url = rootPath + '/products/details/'+alias;
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
    xmlhttp.open("POST",rootPath + '/products/suggest',true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send(new URLSearchParams(formData));
});
function showElement(e) {
    searchAutocompleteInput.value = e.target.getAttribute('data-autocomplete');
    //searchForm.submit();
}

(function () {
    'use strict'

    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

function invalidate()
{
    localStorage.clear();
}
const number=document.getElementById("numberOfElementCart");
if(number!=null)
number.innerHTML=localStorage.length.toString();
function presentCart() {
    var text = "";


    if (localStorage.length == 0)
        text = '<li><span class="dropdown-item-text" >Empty</span></li>';
    else {
        for (let i = 0; i < localStorage.length; i++) {
            console.log(localStorage.length)
            if (localStorage.key(i).substring(0, 4).localeCompare("cart") == 0) {
                console.log(localStorage.getItem(localStorage.key(i)));
                const element = localStorage.getItem(localStorage.key(i)).split(";")
                const qta = element[0];
                const name = element[1];
                text += '<li><span class="dropdown-item-text" >' + name + '<br>'+'<i>quantity: </i>'+ qta + '</a></li> <button class="btn"><i class="fa-solid fa-trash"></i></button><li><hr class="dropdown-divider"></li>';
            }
        }
        text += '<li><span class="dropdown-item-text"><button  class="btn btn-primary" id="buyButton" >buy</button></span></li>';
    }
    const list = document.getElementById("cart");
    list.innerHTML = text;
    const buyButton=document.getElementById("buyButton");
    buyButton?.addEventListener("click",buyCart);

}
function buyCart() {
    var json={cart:[]};
    for (let i = 0; i < localStorage.length; i++) {
        if (localStorage.key(i).substring(0, 4).localeCompare("cart") == 0) {
            const element = localStorage.getItem(localStorage.key(i)).split(";")
            const qta = element[0];
            const name = element[1];
            console.log(i);
            json.cart.push({quantity:qta,alias:localStorage.key(i).substring(4)});
        }
    }

    const send = JSON.stringify(json, undefined, 4);
    console.log(send);
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            console.log(xmlhttp.responseText);
            let id = JSON.parse(xmlhttp.responseText).resourceId;
            console.log(id);

            location.href = rootPath+"/buy/pay/"  + id;
        }
    }

    xmlhttp.open("POST",rootPath+"/rest/buy/cart");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(send);
}

function cart()
{
    const form=document.getElementById("formSend");
    const alias=form.getAttribute("data-product-alias");
    const name=form.getAttribute("data-product-name");
    form.addEventListener("submit",(e) => {
        e.preventDefault();
        const qt=new FormData(form).get("quantity");
        console.log(qt.toString()+ " "+name);
        localStorage.setItem("cart"+alias,qt.toString()+";"+name);
        console.log(localStorage.length);
        if(number!=null)
        number.innerHTML=localStorage.length.toString();
    });
    if(number!=null)
    number.innerHTML=localStorage.length.toString();
}
const addToCart=document.getElementById("addToCart");
addToCart?.addEventListener("click",cart);
logout?.addEventListener("click",invalidate);
if(number!=null)
number.innerHTML=localStorage.length.toString();
cartButton?.addEventListener("click",presentCart);

payment?.addEventListener("click",invalidate);

loginForBuy?.addEventListener('click', (e) => {dropdownMenuLogin.click()});

carousels?.forEach((it) => {
    let items = it.querySelectorAll('.carousel-item')

    items.forEach((el) => {
        const minPerSlide = 4 // number of slides per carousel-item

        let next = el.nextElementSibling
        for (var i=1; i<minPerSlide && i< items.length; i++) {
            if (!next && items.length > 1) {
                // wrap carousel by using first child
                next = items[0]
            }
            if(next) {
                let cloneChild = next.cloneNode(true)
                el.appendChild(cloneChild.children[0])
                next = next.nextElementSibling
            }
        }
    })
})