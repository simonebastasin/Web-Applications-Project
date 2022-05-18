const productTable = document.getElementById('productTable');
const productTableBody = productTable.getElementsByTagName('tbody')[0] ?? productTable;
const addProductButton = document.getElementById('addProductButton');
const deleteProductButton = document.getElementById('deleteProductButton');
const addProductForm = document.getElementById('addProductForm');
const deleteProductForm = document.getElementById('deleteProductForm');
const addCategoryForm = document.getElementById('addCategoryForm');

const addProductModal = document.getElementById('addProductModal');

const deleteProductModal = document.getElementById('deleteProductModal');

const uploadImageForm = document.getElementById('uploadImageForm');
const uploadImageProgress = document.getElementById('uploadImageProgress');
const uploadImageProgressBar = document.getElementById('uploadImageProgressBar');

//const plusButton =
let alias;


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
                    '<td class="bg-primary" ><a class="text-white" href="'+ rootPath + '/products/details/'+alias+'">'+formData.get('name')+'</a></td>'+
                    '<td class="bg-primary">'+alias+'</td>'+
                    '<td class="bg-primary">'+formData.get('brand')+'</td>'+
                    '<td class="bg-primary">'+formData.get('category')+'</td>'+
                    '<td class="bg-primary">'+formData.get('sale')+'</td>'+
                    '<td class="bg-primary">'+formData.get('quantity')+'</td>'+
                    '<td class="bg-primary">'+formData.get('evidence')+'</td>'+
                    '<td class="bg-primary"><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-pen-to-square text-light"></i></button></td>'+
                    '<td class="bg-primary"><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-trash-can text-light"></i></button></td>';
                if(createProduct) {
                    bootstrapAlert("The product was created", 'success', alertPlaceholder);
                    let tr=document.createElement('tr');
                    tr.id = alias;
                    tr.innerHTML = newInnerHTML;
                    productTableBody.appendChild(tr);
                }
                else {
                    bootstrapAlert("The product was modified", 'success', alertPlaceholder);
                    document.getElementById(alias).innerHTML = newInnerHTML;
                }
                bootstrap.Modal.getOrCreateInstance(addProductModal).hide();


                setTimeout(function(){
                    let newInnerHTML =
                        '<td><a href="'+ rootPath + '/products/details/'+alias+'">'+formData.get('name')+'</a></td>'+
                        '<td>'+alias+'</td>'+
                        '<td>'+formData.get('brand')+'</td>'+
                        '<td>'+formData.get('category')+'</td>'+
                        '<td>'+formData.get('sale')+'</td>'+
                        '<td>'+formData.get('quantity')+'</td>'+
                        '<td>'+formData.get('evidence')+'</td>'+
                        '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                        '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-trash-can text-danger"></i></button></td>';
                    document.getElementById(alias).innerHTML = newInnerHTML;

                }, 1700);

            } else {
                if(createProduct) alias = null;
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
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
                        li.innerHTML = '<input type="checkbox" id="media-'+response.resourceId+'" name="pictures" value="'+response.resourceId+'"/>'+
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


    addProductForm.classList.toggle('was-validated', false);
    addProductForm.reset();

    uploadImageForm.classList.toggle('was-validated', false);
    uploadImageForm.reset();


    uploadImageProgress.style.display = "none";
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
                    document.getElementById('alias').readOnly = true;
                    document.getElementById('alias').disabled = true;

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

})




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
                const alertPlaceholder = document.getElementById('liveAlertPlaceholderDelete');
                bootstrapAlert("The product was remove", 'success', alertPlaceholder);


                let newInnerHTML =
                    '<td class="bg-primary"><a class="text-white" href="'+ rootPath + '/products/details/'+alias+'">'+formData.get('name')+'</a></td>'+
                    '<td class="bg-primary">'+alias+'</td>'+
                    '<td class="bg-primary">'+formData.get('brand')+'</td>'+
                    '<td class="bg-primary">'+formData.get('category')+'</td>'+
                    '<td class="bg-primary">'+formData.get('sale')+'</td>'+
                    '<td class="bg-primary">0</td>'+
                    '<td class="bg-primary">'+formData.get('evidence')+'</td>'+
                    '<td class="bg-primary"><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-pen-to-square text-light"></i></button></td>'+
                    '<td class="bg-primary"><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-trash-can text-light"></i></button></td>';

                document.getElementById(alias).innerHTML = newInnerHTML;
                bootstrap.Modal.getOrCreateInstance(deleteProductModal).hide();
                setTimeout(function(){
                    let newInnerHTML =
                        '<td><a href="'+ rootPath + '/products/details/'+alias+'">'+formData.get('name')+'</a></td>'+
                        '<td>'+alias+'</td>'+
                        '<td>'+formData.get('brand')+'</td>'+
                        '<td>'+formData.get('category')+'</td>'+
                        '<td>'+formData.get('sale')+'</td>'+
                        '<td>0</td>'+
                        '<td>'+formData.get('evidence')+'</td>'+
                        '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                        '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteProductModal" data-bs-whatever="'+alias+'"> <i class="fa-solid fa-trash-can text-danger"></i></button></td>';
                    document.getElementById(alias).innerHTML = newInnerHTML;

                }, 1700);
            } else {

                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ): (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})



deleteProductModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    alias = button.getAttribute('data-bs-whatever');


    deleteProductForm.classList.toggle('was-validated', false);
    deleteProductForm.reset();


    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath+"/rest/management/productManagement/deleteProduct/"+alias, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                populateForm(deleteProductForm, response["product"] ?? response,)




            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholderDelete');
                bootstrapAlert(xmlhttp.statusText !== "" ? 'Error: '+xmlhttp.status : 'Generic error', 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(deleteProductModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = deleteProductModal.querySelector('.modal-title');
    modalTitle.textContent = 'Delete ' + alias;
    deleteProductButton.textContent = 'Delete product';

})


var show = function (elem) {
    elem.style.display = 'block';
};

var hide = function (elem) {
    elem.style.display = 'none';
};

var toggle = function (elem) {
// Otherwise, show it
    if (window.getComputedStyle(elem).display === 'none') {
        show(elem);
        return;
    }

    // If the element is visible, hide it

    hide(elem);

};



// Listen for click events
document.addEventListener('click', function (event) {

    // Make sure clicked element is our toggle
    if (!event.target.classList.contains('toggle')) return;

    // Prevent default link behavior
    event.preventDefault();

    // Get the content
    var content = document.querySelector(event.target.hash);
    if (!content) return;

    // Toggle the content
    toggle(content);

}, false);


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
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("The category was added", 'success', alertPlaceholder);


                let newInnerHTML =
                    '<option value="' +formData.get('name')+'">'
                        +formData.get('name')+
                    '</option>;'
                let tr=document.createElement('tr');
                tr.id = alias;
                tr.innerHTML = newInnerHTML;

                document.getElementById(category).appendChild(tr);
                bootstrap.Modal.getOrCreateInstance(deleteProductModal).hide();

            } else {

                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ): (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})
