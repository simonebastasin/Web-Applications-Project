const productTable = document.getElementById('productTable');
const addProductButton = document.getElementById('addProductButton');
const addProductForm = document.getElementById('addProductForm');
const addProductModal = document.getElementById('addProductModal');
const uploadImageForm = document.getElementById('uploadImageForm');
const uploadImageProgress = document.getElementById('uploadImageProgress');
const uploadImageProgressBar = document.getElementById('uploadImageProgressBar');
const addProductSubmit = document.getElementById('addProductSubmit');
let alias;
addProductButton.addEventListener('click', (e) => {
    addProductSubmit.click(); // only  html5 required
})
addProductForm.addEventListener('submit', (e) => {
    if(!addProductForm.checkValidity()) return;

    e.preventDefault();
    let createProduct = (alias  === null);
    const formData = new FormData(addProductForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    if(createProduct) {
        xmlhttp.open("POST", rootPath + "/rest/management/productManagement/createProduct", true);
        alias = formData.get('alias');
    }
    else {
        xmlhttp.open("POST", rootPath + "/rest/management/productManagement/editProduct/" + alias, true);
        formData.delete('alias');
    }
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td><a href="'+ rootPath + '/products/details/'+alias+'">'+formData.get('name')+'</a></td>'+
                    '<td>'+alias+'</td>'+
                    '<td>'+formData.get('brand')+'</td>'+
                    '<td>'+formData.get('category')+'</td>'+
                    '<td>'+formData.get('sale')+'</td>'+
                    '<td>'+formData.get('quantity')+'</td>'+
                    '<td>'+formData.get('evidence')+'</td>'+
                    '<td>'+formData.getAll('pictures').join(' ')+'</td>'+
                    '<td><button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="'+alias+'">Edit</button></td>'+
                    '<td><button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="'+alias+'">Delete</button></td>';
                if(createProduct) {
                    bootstrapAlert("The product was created", 'success', alertPlaceholder);
                    let tr=document.createElement('tr');
                    tr.id = alias;
                    tr.innerHTML = newInnerHTML;
                    productTable.appendChild(tr);
                }
                else {
                    bootstrapAlert("The product was modified", 'success', alertPlaceholder);
                    document.getElementById(alias).innerHTML = newInnerHTML;
                }
                bootstrap.Modal.getOrCreateInstance(addProductModal).hide();
            } else {
                if(createProduct) alias = null;
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ): (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})
uploadImageForm.addEventListener('submit', (e) => {
    if(!uploadImageForm.checkValidity()) return;

    e.preventDefault();

    const multipart_data = new FormData(uploadImageForm);

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/media/upload", true);
    xmlhttp.upload.addEventListener("progress", function(e) {
        let progress = ((e.loaded * 100.0 / e.total) || 100);
        updateProgressBar(progress, false);
    });
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                const response = JSON.parse(xmlhttp.responseText)[0];
                const imageCheckBox = document.querySelectorAll('.image-check-box ul');
                [...imageCheckBox].forEach(
                    (element) => {
                        const li = document.createElement('li');
                        li.innerHTML = '<input type="checkbox" id="media-'+response.resourceId+'" name="media" value="'+response.resourceId+' checked"/>'+
                            '<label for="media-'+response.resourceId+'"><img src="'+rootPath+"/media/thumb/"+response.resourceId+'" /></label>';
                        element.appendChild(li);
                    }
                );
                bootstrapAlert(response.message, 'success', alertPlaceholder);
            } else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ) : (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
                updateProgressBar(0, true);
            }
        }
    }
    xmlhttp.send(multipart_data);
    updateProgressBar(0, false);
})
addProductModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    alias = button.getAttribute('data-bs-whatever');
    let createProduct = (alias  === null);
    document.getElementById('alias').readOnly = !createProduct;
    document.getElementById('alias').disabled = !createProduct;

    if(createProduct) {
        addProductForm.reset();


        let modalTitle = addProductModal.querySelector('.modal-title');
        modalTitle.textContent = 'Add product';
        addProductButton.textContent = 'Add product';
    }
    else {
        const xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", rootPath+"/rest/management/productManagement/editProduct/"+alias, true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    const response = JSON.parse(xmlhttp.responseText);
                    populateForm(addProductForm, response["product"] ?? response,)

                } else {
                    const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                    bootstrapAlert(xmlhttp.statusText !== "" ? 'Error: '+xmlhttp.status : 'Generic error', 'danger', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addProductModal).hide();
                }
            }
        }
        xmlhttp.send();

        let modalTitle = addProductModal.querySelector('.modal-title');
        modalTitle.textContent = 'Edit product ' + alias;
        addProductButton.textContent = 'Edit product';
    }

})


