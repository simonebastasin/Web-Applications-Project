const discountTable = document.getElementById('discountTable');
const discountTableBody = discountTable.getElementsByTagName('tbody')[0] ?? discountTable;
const addDiscountButton = document.getElementById('addDiscountButton');
const addDiscountForm = document.getElementById('addDiscountForm');
const addDiscountModal = document.getElementById('addDiscountModal');


const deleteDiscountForm = document.getElementById('deleteDiscountForm');
const deleteDiscountModal = document.getElementById('deleteDiscountModal');
const deleteDiscountButton = document.getElementById('deleteDiscountButton');


let idDiscount;

function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(date) {
    return [
        padTo2Digits(date.getDate()),
        padTo2Digits(date.getMonth() + 1),
        date.getFullYear(),
    ].join('/');
}


addDiscountForm.addEventListener('submit', (e) => {
    if(!addDiscountForm.checkValidity()) {
        document.getElementById('navDiscountInfo').click();

        return;
    }

    e.preventDefault();
    let createDiscount = (idDiscount  === null);
    const formData = new FormData(addDiscountForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();




    if(createDiscount) {
       //alert("send create");
        xmlhttp.open("POST", rootPath + "/rest/management/discountManagement/createDiscount", true);
    } else {
        //alert("send edit "+idDiscount);
        xmlhttp.open("POST", rootPath + "/rest/management/discountManagement/editDiscount/" + idDiscount, true);

    }

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                if(createDiscount) {
                    const response = JSON.parse(xmlhttp.responseText);
                    idDiscount = (response?.[0] ?? response).resourceId;
                }

                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                const response = JSON.parse(xmlhttp.responseText);

                idDiscount = response.resourceId;

                let newInnerHTML =
                    '<td class="bg-primary">'+response.resourceId+'</td>'+

                    '<td class="bg-primary">'+formData.get('percentage')+'%</td>'+
                    '<td class="bg-primary">'+formatDate(new Date(formData.get('startDate')))+'</td>'+
                    '<td class="bg-primary">'+formatDate(new Date(formData.get('endDate')))+'</td>'+
                    '<td class="bg-primary">'+formData.getAll('productList').join(' ')+'</td>'+
                    '<td class="bg-primary"><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addDiscountModal" data-bs-whatever="'+response.resourceId+'"> <i class="fa-solid fa-pen-to-square text-light"></i></button></td>'+
                    '<td class="bg-primary"><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteDiscountModal" data-bs-whatever="'+response.resourceId+'"> <i class="fa-solid fa-trash-can text-light"></i></button></td>';

                if(createDiscount) {
                    bootstrapAlert("The discount was created", 'success', alertPlaceholder);
                    let tr = document.createElement('tr');
                    tr.setAttribute('data-id',idDiscount);
                    tr.innerHTML = newInnerHTML;
                    discountTableBody.insertBefore(tr, discountTableBody.firstChild);
                }
                else {
                    bootstrapAlert("The discount was modified", 'success', alertPlaceholder);
                    document.querySelector('tr[data-id="'+idDiscount+'"]').innerHTML = newInnerHTML;
                }
                bootstrap.Modal.getOrCreateInstance(addDiscountModal).hide();

                evidenceRow(document.querySelector('tr[data-id="'+idDiscount+'"]'));
            }else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});

addDiscountModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    idDiscount = button.getAttribute('data-id');
    let createDiscount = (idDiscount  === null);

    addDiscountForm.classList.toggle('was-validated', false);
    addDiscountForm.reset();
    if(createDiscount) {
        let modalTitle = addDiscountModal.querySelector('.modal-title');
        modalTitle.textContent = 'Add discount';
        addDiscountButton.textContent = 'Add discount';
    } else {
        const xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", rootPath+"/rest/management/discountManagement/editDiscount/"+idDiscount, true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    const response = JSON.parse(xmlhttp.responseText).discountListProduct;
                    let fullProductList = response.productList; // for backup
                    let respondeElaborated = Object.assign({}, response, {productList : response.productList.map(it =>  it.alias)});
                    const response1 = JSON.parse(xmlhttp.responseText).discountListProduct;

                    populateForm(addDiscountForm, respondeElaborated);

                } else {
                    const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                    bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addDiscountModal).hide();
                }
            }
        }
        xmlhttp.send();

        let modalTitle = addDiscountModal.querySelector('.modal-title');
        modalTitle.textContent = 'Edit discount ' + idDiscount;
        addDiscountButton.textContent = 'Edit discount';
    }
});


deleteDiscountForm.addEventListener('submit', (e) => {
    if(!deleteDiscountForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(deleteDiscountForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();


    xmlhttp.open("POST", rootPath + "/rest/management/discountManagement/deleteDiscount/" + idDiscount, true);

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("The discount " + idDiscount + " has been removed", 'success', alertPlaceholder);
                let row = document.querySelector('tr[data-id="'+idDiscount+'"]');
                row.remove();


                bootstrap.Modal.getOrCreateInstance(deleteDiscountModal).hide();

            } else {

                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ): (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})



deleteDiscountModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    idDiscount = button.getAttribute('data-id');


    deleteDiscountForm.classList.toggle('was-validated', false);
    deleteDiscountForm.reset();


    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath+"/rest/management/discountManagement/deleteDiscount/"+idDiscount, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                populateForm(deleteDiscountForm, response["discount"] ?? response,);
                document.getElementById('percentageDelete').readOnly = true;
                document.getElementById('percentageDelete').disabled = true;
                document.getElementById('startDateDelete').readOnly = true;
                document.getElementById('startDateDelete').disabled = true;
                document.getElementById('endDateDelete').readOnly = true;
                document.getElementById('endDateDelete').disabled = true;

            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(xmlhttp.statusText !== "" ? 'Error: '+xmlhttp.status : 'Generic error', 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(deleteDiscountModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = deleteDiscountModal.querySelector('.modal-title');
    modalTitle.textContent = 'Are you sure to delete this discount?';

})
