const productTable = document.getElementById('productTable');
const productTableBody = productTable.getElementsByTagName('tbody')[0] ?? productTable;
const addProductButton = document.getElementById('addProductButton');
const addProductForm = document.getElementById('addProductForm');
const deleteProductForm = document.getElementById('deleteProductForm');
const addCategoryForm = document.getElementById('addCategoryForm');

const addProductModal = document.getElementById('addProductModal');

const deleteProductModal = document.getElementById('deleteProductModal');

const uploadImageForm = document.getElementById('uploadImageForm');
const uploadImageProgress = document.getElementById('uploadImageProgress');
const uploadImageProgressBar = document.getElementById('uploadImageProgressBar');

const toogleButton = document.getElementsByClassName('toggle');

let alias;

const ready = (xmlhttp) => {
    if(xmlhttp.status === 200) {
        const alertPlaceholder = document.getElementById('formAlertPlaceholder');
        const response = JSON.parse(xmlhttp.responseText)[0];
        const imageCheckBox = document.querySelectorAll('.image-check-box ul');
        [...imageCheckBox].forEach(
            (element) => {
                const li = document.createElement('li');
                li.innerHTML = '<input type="checkbox" id="media-'+response.resourceId+'" name="pictures" value="'+response.resourceId+'" form="addProductForm"/>'+
                    '<label for="media-'+response.resourceId+'"><img src="'+rootPath+"/media/thumb/"+response.resourceId+'" /></label>';
                element.appendChild(li);
            }
        );

        const imageCheckBoxItem = document.querySelectorAll('.image-check-box ul li:last-child input');
        [...imageCheckBoxItem].forEach((element) => element.click());

        bootstrapAlert(response.message, 'success', alertPlaceholder);
    } else {
        const alertPlaceholder = document.getElementById('formAlertPlaceholder');
        bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
        updateProgressBar(0, true);
    }
};


addProductForm.addEventListener('submit', (e) => {
    if(!addProductForm.checkValidity()) {
        document.getElementById('navProductInfo').click();
        return;
    }

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
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addProductModal" data-id="'+alias+'"> <i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-id="'+alias+'"> <i class="fa-solid fa-trash-can text-danger"></i></button></td>';
                if(createProduct) {
                    bootstrapAlert("The product " + alias + " has been created", 'success', alertPlaceholder);
                    let tr=document.createElement('tr');
                    tr.setAttribute('data-id',alias);
                    tr.innerHTML = newInnerHTML;
                    productTableBody.appendChild(tr);
                }
                else {
                    bootstrapAlert("The product " + alias + " has been modified", 'success', alertPlaceholder);
                    document.querySelector('tr[data-id="'+alias+'"]').innerHTML = newInnerHTML;
                }
                bootstrap.Modal.getOrCreateInstance(addProductModal).hide();

                evidenceRow(document.querySelector('tr[data-id="'+alias+'"]'));

            } else {
                if(createProduct) alias = null;
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);


});

uploadImageForm.addEventListener('submit', (e) => {
    if(!uploadImageForm.checkValidity()) return;

    e.preventDefault();

    const multipartData = new FormData(uploadImageForm);

    uploadFile(multipartData, ready);
});

addProductModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    alias = button.getAttribute('data-id');
    let createProduct = (alias  === null);


    addProductForm.classList.toggle('was-validated', false);
    addProductForm.reset();

    uploadImageForm.classList.toggle('was-validated', false);
    uploadImageForm.reset();

    addCategoryForm.classList.toggle('was-validated', false);
    addCategoryForm.reset();

    uploadImageProgress.classList.toggle('d-none', true);

    var content = document.getElementById('toggleCategory');
    content.classList.toggle('d-none', true);


    document.getElementById('alias').readOnly = !createProduct;
    document.getElementById('alias').disabled = !createProduct;

    if(createProduct) {
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
                    bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addProductModal).hide();
                }
            }
        }
        xmlhttp.send();

        let modalTitle = addProductModal.querySelector('.modal-title');
        modalTitle.textContent = 'Edit product ' + alias;
        addProductButton.textContent = 'Edit product';
    }

});

initDropArea(document.getElementById('tabProductMedia'), ready);

deleteProductForm.addEventListener('submit', (e) => {
    if(!deleteProductForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(deleteProductForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();


    xmlhttp.open("POST", rootPath + "/rest/management/productManagement/deleteProduct/" + alias, true);

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("The product " + alias + " has been removed", 'success', alertPlaceholder);

                let row = document.querySelector('tr[data-id="'+alias+'"]');

                row.children[5].innerHTML = "0";

                bootstrap.Modal.getOrCreateInstance(deleteProductModal).hide();

                evidenceRow(row);


            } else {

                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});

deleteProductModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    alias = button.getAttribute('data-id');


    deleteProductForm.classList.toggle('was-validated', false);
    deleteProductForm.reset();


    document.getElementById('aliasDelete').readOnly = true;
    document.getElementById('aliasDelete').disabled = true;
    document.getElementById('nameDelete').readOnly = true;
    document.getElementById('nameDelete').disabled = true;
    document.getElementById('brandDelete').readOnly = true;
    document.getElementById('brandDelete').disabled = true;

    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath+"/rest/management/productManagement/deleteProduct/"+alias, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                populateForm(deleteProductForm, response["product"] ?? response,);

            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(deleteProductModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = deleteProductModal.querySelector('.modal-title');
    modalTitle.textContent = 'Are you sure to delete this product?';

});

[...toogleButton].forEach(it => it.addEventListener('click', function (event) {

    // Prevent default link behavior
    event.preventDefault();

    let query = event.target.getAttribute('data-toogle');
    // Get the content
    var content = document.querySelector(query);
    if (!content) return;

    // Toggle the content
    content.classList.toggle('d-none');

}, false));

addCategoryForm.addEventListener('submit', (e) => {
    if(!addCategoryForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(addCategoryForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();


    xmlhttp.open("POST", rootPath + "/rest/management/productManagement/createCategory/", true);

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                let option=document.createElement('option');
                option.value = formData.get('name');
                option.text = formData.get('name');

                let category = document.getElementById('category');
                category.appendChild(option);
                category.value = formData.get('name');


                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert("The category has been added", 'success', alertPlaceholder);

                // Get the content
                var content = document.getElementById('toggleCategory');
                content.classList.toggle('d-none', true);

            } else {

                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});
