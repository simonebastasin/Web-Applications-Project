function printDiv(divId) {
    var printContents = document.getElementById(divId).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}

function bootstrapAlert(message, type, placeholder) {
    var wrapper = document.createElement('div');
    wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

    placeholder.append(wrapper);

    setTimeout(function(){
        wrapper.style.display = "none"

    }, 3000);
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