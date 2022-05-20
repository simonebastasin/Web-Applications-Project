const customerTable = document.getElementById('customerTable');
const customerTableBody = customerTable.getElementsByTagName('tbody')[0] ?? customerTable;

const editCustomerButton = document.getElementById('editCustomerButton');
const editCustomerForm = document.getElementById('editCustomerForm');
const editCustomerModal = document.getElementById('editCustomerModal');

const deleteCustomerButton = document.getElementById('deleteCustomerButton');
const deleteCustomerForm = document.getElementById('deleteCustomerForm');
const deleteCustomerModal = document.getElementById('deleteCustomerModal');


let username;
let id;


editCustomerForm.addEventListener('submit', (e) => {
    if(!editCustomerForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(editCustomerForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    // -> edit
    xmlhttp.open("POST", rootPath + "/rest/management/customerManagement/editCustomer/" + username, true);
    //formData.delete('username');

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td>'+id+'</td>'+
                    '<td>'+username+'</td>'+
                    '<td>'+formData.get('name')+'</td>'+
                    '<td>'+formData.get('surname')+'</td>'+
                    '<td>'+formData.get('fiscalCode')+'</td>'+
                    '<td>'+formData.get('address')+'</td>'+
                    '<td>'+formData.get('email')+'</td>'+
                    '<td>'+formData.get('phoneNumber')+'</td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editCustomerModal" data-id="'+username+'"><i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteCustomerModal" data-id="'+username+'"><i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                // -> edit
                bootstrapAlert("Customer " + username + " modified", 'success', alertPlaceholder);
                document.querySelector('tr[data-id="'+username+'"]').innerHTML = newInnerHTML;

                evidenceRow(document.querySelector('tr[data-id="'+username+'"]'));
                bootstrap.Modal.getOrCreateInstance(editCustomerModal).hide();

            } else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});


editCustomerModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    username = button.getAttribute('data-id');

    editCustomerForm.classList.toggle('was-validated', false);
    editCustomerForm.reset();

    document.getElementById('id').readOnly = true;
    document.getElementById('username').readOnly = true;

    // -> edit
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath + "/rest/management/customerManagement/editCustomer/" + username, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                id = (response?.[0] ?? response).id;
                populateForm(editCustomerForm, response);
            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(editCustomerModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = editCustomerModal.querySelector('.modal-title');
    modalTitle.textContent = 'Edit customer ' + username;
    editCustomerButton.textContent = 'Edit customer';
});


deleteCustomerForm.addEventListener('submit', (e) => {
    if(!deleteCustomerForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(deleteCustomerForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open("POST", rootPath + "/rest/management/customerManagement/deleteCustomer/" + username, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("Customer " + username + " removed", 'success', alertPlaceholder);

                let row = document.querySelector('tr[data-id="'+username+'"]');
                row.children[5].innerHTML = "0";
                bootstrap.Modal.getOrCreateInstance(deleteCustomerModal).hide();

            } else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})


deleteCustomerModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    username = button.getAttribute('data-id');

    deleteCustomerForm.classList.toggle('was-validated', false);
    deleteCustomerForm.reset();

    document.getElementById('usernameDelete').readOnly = true;
    document.getElementById('nameDelete').readOnly = true;
    document.getElementById('surnameDelete').readOnly = true;
    document.getElementById('fiscalCodeDelete').readOnly = true;

    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath + "/rest/management/customerManagement/deleteCustomer/" + username, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                //id = (response?.[0] ?? response).id;
                populateForm(deleteCustomerForm, response);

            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(deleteCustomerModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = deleteCustomerModal.querySelector('.modal-title');
    modalTitle.textContent = 'Delete customer ' + username;
    deleteCustomerButton.textContent = 'Delete customer';
})