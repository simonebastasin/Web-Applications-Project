const customerTable = document.getElementById('customerTable');
const customerTableBody = customerTable.getElementsByTagName('tbody')[0] ?? customerTable;

const editCustomerButton = document.getElementById('editCustomerButton');
const editCustomerForm = document.getElementById('editCustomerForm');
const editCustomerModal = document.getElementById('editCustomerModal');

//const deleteCustomerButton = document.getElementById('deleteCustomerButton');
//const deleteCustomerForm = document.getElementById('deleteCustomerForm');
//const deleteCustomerModal = document.getElementById('deleteCustomerModal');


let username;


editCustomerForm.addEventListener('submit', (e) => {
    if(!editCustomerForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(editCustomerForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    // -> edit
    //alert("send edit " + username);
    xmlhttp.open("POST", rootPath + "/rest/management/customerManagement/editCustomer/" + username, true);

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td class="bg-primary">'+formData.get('id')+'</td>'+
                    '<td class="bg-primary">'+username+'</td>'+
                    '<td class="bg-primary">'+formData.get('name')+'</td>'+
                    '<td class="bg-primary">'+formData.get('surname')+'</td>'+
                    '<td class="bg-primary">'+formData.get('fiscalCode')+'</td>'+
                    '<td class="bg-primary">'+formData.get('address')+'</td>'+
                    '<td class="bg-primary">'+formData.get('email')+'</td>'+
                    '<td class="bg-primary">'+formData.get('phoneNumber')+'</td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editCustomerModal" data-bs-whatever="'+username+'"> <i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteCustomerModal" data-bs-whatever="'+username+'"> <i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                // -> edit
                bootstrapAlert("Customer modified", 'success', alertPlaceholder);
                document.getElementById(username).innerHTML = newInnerHTML;

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

    alert("USERNAME: " + username);

    editCustomerForm.classList.toggle('was-validated', false);
    editCustomerForm.reset();

    // -> edit
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath + "/rest/management/customerManagement/editCustomer/" + username, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                populateForm(editCustomerForm, response);
                document.getElementById('id').disabled = true;
                document.getElementById('username').disabled = true;
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