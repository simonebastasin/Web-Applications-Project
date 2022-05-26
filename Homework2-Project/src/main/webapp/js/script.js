const searchForm = document.getElementById('searchForm');
const searchAutocompleteInput = document.getElementById('searchAutocompleteInput');
const searchAutocompleteMenu = document.getElementById('searchAutocompleteMenu');
const logout=document.getElementById("logoutButton");
const cartButton=document.getElementById("navbarDropdownCart");
const dropdownMenuLogin = document.getElementById('dropdownMenuLogin');
const loginForBuy = document.getElementById('loginForBuy');
const carousels = document.querySelectorAll('.carousel-category');
const popperInstance = Popper.createPopper(searchAutocompleteInput, searchAutocompleteMenu, {
    placement: 'bottom-start',
});
const number=document.getElementById("numberOfElementCart");
let fromCart;
const addToCart=document.getElementById("addToCart");
const printInvoice = document.getElementById('printInvoice');
const loginFormDrop = document.getElementById("loginFormDrop");
const formConfirmPassword = document.querySelector('form[data-confirm-password]');
const loginButton=document.getElementById("loginButton");

function printElement(query) {
    document.body.innerHTML = document.querySelector(query).innerHTML;
    window.print();
}

function parseError(xmlhttp) {
    if(xmlhttp.responseText !== "") {
        let message = xmlhttp.responseText;
        if(message.startsWith("<!doctype html>")) {
            return parseServletError(message);
        }
        try{
            const json = JSON.parse(message);
            return "Error " + json.errorCode + ": " + json.message +  " ("+ json.errorDetails + ")";
        }
        catch (e) {

        }
    }
    return xmlhttp.statusText !== ""? 'HTTP Error: '+ xmlhttp.statusText : "Generic error";
}

function parseServletError(message) {
    const parser = new DOMParser();
    const floatingElement = parser.parseFromString(message, 'text/html');
    return [...floatingElement.body.getElementsByTagName('p')].map(i => i.innerText).join(" ");

}

function bootstrapAlert(message, type, placeholder, timeout=10000) {
    const wrapper = document.createElement('div');
    wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

    placeholder.append(wrapper);

    if(timeout!==-1)
        setTimeout(function(){
            wrapper.remove();
        }, timeout);
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
                let date = new Date(value);
                date.setMinutes(date.getMinutes() - date.getTimezoneOffset());
                element.value = date.toISOString().substring(0, 10);
                break;

            case 'datetime-local':
                let datetime = new Date(value);
                datetime.setMinutes(datetime.getMinutes() - datetime.getTimezoneOffset());
                element.value = datetime.toISOString().substring(0, 16);
                break;
        }

    }
}

function showElement(e) {
    searchAutocompleteInput.value = e.target.getAttribute('data-autocomplete');
}

function buyCart(e) {
    fromCart=true;
    let json = {cart: []};
    for (let i = 0; i < localStorage.length; i++) {
        if (localStorage.key(i).substring(0, 4).localeCompare("cart") == 0) {
            const element = localStorage.getItem(localStorage.key(i)).split(";")
            const qta = element[0];

            json.cart.push({quantity:qta,alias:localStorage.key(i).substring(4)});
        }
    }

    const send = JSON.stringify(json, undefined, 4);

    const xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {

            let id = JSON.parse(xmlhttp.responseText).resourceId;


            location.href = rootPath+"/buy/pay/"  + id;
            invalidate();
        }
    }

    xmlhttp.open("POST",rootPath+"/rest/buy/cart");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(send);
}

function invalidate(e) {
    if(fromCart==true) {
        localStorage.clear();
    }
    fromCart=false;
}

function presentCart(e) {
    let text = "<div id='wrapper'>";
    if (localStorage.length == 0)
        text += '<li><span class="dropdown-item-text" >Empty</span></li>';
    else {
        for (let i = 0; i < localStorage.length; i++) {

            if (localStorage.key(i).substring(0, 4).localeCompare("cart") == 0) {

                const element = localStorage.getItem(localStorage.key(i)).split(";")
                const qta = element[0];
                const name = element[1];
                text += '<li><span class="dropdown-item-text" > ' + name + '<br>' +
                    '<div class="d-inline p-2 "><i>quantity: </i>'+ qta +'</div>' +
                    '<div class="d-inline p-2 "><button class="btn cartButton" data-cart='+localStorage.key(i)+'>' +
                    '<i class="fa-solid fa-trash"></i></button><li><hr class="dropdown-divider"></div>' +
                    '</li>';
            }
        }
        text += '<li><span class="dropdown-item-text d-flex justify-content-center "><button id="buyButton" class="btn btn-outline-primary mt-1">\n' +
            '                                                    Buy <i class="fa-solid fa-money-check"></i>\n' +
            '                                                </button></span></li>';
    }
    text+="</div>";

    const list = document.getElementById("cart");
    list.innerHTML = text;
    const btns = document.getElementsByClassName("cartButton");
    [...btns].forEach(btn => {

        btn.addEventListener('click', (event) => {
            localStorage.removeItem(event.currentTarget.getAttribute("data-cart"));
            if(number!=null)
                number.innerHTML=localStorage.length.toString();
        });
        btn.addEventListener('click', presentCart);
    });

    const buyButton=document.getElementById("buyButton");
    buyButton?.addEventListener("click",buyCart);

}

function toogleWasValidated(form, force) {
    if(force === undefined)
        [...form.elements].map(e => e.parentNode).forEach(e => e.classList.toggle('was-validated'));
    else
        [...form.elements].map(e => e.parentNode).forEach(e => e.classList.toggle('was-validated', force));
}

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
                        let brand=product.brand;
                        let url = rootPath + '/products/details/'+alias;
                        inner+="<li><a class='dropdown-item' href='"+url+"'><i>"+name+":</i> <small> "+brand+"</small></a></li>";
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

(function() {
    'use strict'

    const forms = document.querySelectorAll('.needs-validation');

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                toogleWasValidated(form, true);
            }, false)
        })
})();

(function() {
    if(number!=null)
        number.innerHTML=localStorage.length.toString();
})();

addToCart?.addEventListener("click",(e) => {
    const form=document.getElementById("formSend");

    if (form.checkValidity()) {
        const alias = form.getAttribute("data-product-alias");
        const name = form.getAttribute("data-product-name");

        const qt = new FormData(form).get("quantity");

        localStorage.setItem("cart" + alias, qt.toString() + ";" + name);

        if (number != null)
            number.innerHTML = localStorage.length.toString();
    }
    toogleWasValidated(form, true);
});

logout?.addEventListener("click",invalidate);

cartButton?.addEventListener("click",presentCart);

loginForBuy?.addEventListener('click', (e) => {dropdownMenuLogin.click()});

carousels?.forEach((it) => {
    let items = it.querySelectorAll('.carousel-item')

    items.forEach((el) => {
        const minPerSlide = 4 // number of slides per carousel-item

        let next = el.nextElementSibling
        for (let i=1; i<minPerSlide; i++) {
            if (!next) {
                // wrap carousel by using first child
                next = items[0]
            }
            if(next) {
                let cloneChild = next.cloneNode(true)
                let children = cloneChild.children[0];
                el.appendChild(children)
                if(i >= items.length) {
                    children.style.visibility = "hidden";
                }
                next = next.nextElementSibling
            }
        }
    })
});

printInvoice?.addEventListener('click', (e) =>{
    printElement('#invoice');

    location.reload();
});

loginFormDrop?.addEventListener('submit', (e) => {
    if(!loginFormDrop.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(loginFormDrop);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/session/login", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                location.reload();

            }
            else{
                const alertPlaceholder = document.getElementById('liveAlertPlaceholderDrop');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});

formConfirmPassword?.addEventListener('submit', (e) => {
    const formData = new FormData(formConfirmPassword);
    const password = document.getElementById("newPassword");
    const confirmPassword = document.getElementById("confirmPassword");
    if(formData.get('newPassword') !== formData.get('confirmPassword') ) {
        password.setCustomValidity('Passwords do NOT match!');
        confirmPassword.setCustomValidity('Passwords do NOT match!');
        document.getElementById('newPasswordFeedback').innerHTML =  'Passwords do NOT match!';
        document.getElementById('confirmPasswordFeedback').innerHTML =  'Passwords do NOT match!';

    } else if(password.validity.valueMissing && confirmPassword.validity.valueMissing) {
        password.setCustomValidity( 'Passwords is empty');
        confirmPassword.setCustomValidity( 'Confirm Passwords is empty');
        document.getElementById('newPasswordFeedback').innerHTML =  'Passwords is empty';
        document.getElementById('confirmPasswordFeedback').innerHTML =  'Confirm Passwords is empty';

    }else if(password.validity.valueMissing) {
        password.setCustomValidity( 'Passwords is empty');
        confirmPassword.setCustomValidity('');
        document.getElementById('newPasswordFeedback').innerHTML =  'Passwords is empty';

    } else if(confirmPassword.validity.valueMissing) {
        password.setCustomValidity('');
        confirmPassword.setCustomValidity( 'Confirm Passwords is empty');
        document.getElementById('confirmPasswordFeedback').innerHTML =  'Confirm Passwords is empty';
    } else{
        password.setCustomValidity('');
        confirmPassword.setCustomValidity('');
    }
    e.preventDefault();
});
loginButton?.addEventListener('click',(e)=>{localStorage.clear()});