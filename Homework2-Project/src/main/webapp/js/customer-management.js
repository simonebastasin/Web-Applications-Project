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
    //alert("send edit " + username);
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
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editCustomerModal" data-bs-whatever="'+username+'"> <i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteCustomerModal" data-bs-whatever="'+username+'"> <i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                // -> edit
                bootstrapAlert("Customer " + username + " modified", 'success', alertPlaceholder);
                document.getElementById(username).innerHTML = newInnerHTML;

                evidenceRow( document.getElementById(username));
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
    username = button.getAttribute('data-bs-whatever');

    editCustomerForm.classList.toggle('was-validated', false);
    editCustomerForm.reset();


    document.getElementById('id').disabled = true;
    document.getElementById('username').disabled = true;

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