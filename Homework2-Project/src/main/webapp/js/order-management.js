const orderTable = document.getElementById('orderTable');
const orderTableBody = orderTable.getElementsByTagName('tbody')[0] ?? orderTable;

const editOrderButton = document.getElementById('editOrderButton');
const editOrderForm = document.getElementById('editOrderForm');
const editOrderModal = document.getElementById('editOrderModal');

const deleteOrderButton = document.getElementById('deleteOrderButton');
const deleteOrderForm = document.getElementById('deleteOrderForm');
const deleteOrderModal = document.getElementById('deleteOrderModal');


let id;


editOrderForm.addEventListener('submit', (e) => {
    if(!editOrderForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(editOrderForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    // -> edit
    xmlhttp.open("POST", rootPath + "/rest/management/orderManagement/editOrder/" + id, true);
    //formData.delete('id');

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td>'+id+'</td>'+
                    '<td>'+formData.get('idCustomer')+'</td>'+
                    '<td>'+formData.get('ooDateTime')+'</td>'+
                    '<td>'+formData.get('products')+'</td>'+
                    '<td>'+formData.get('status')+'</td>'+
                    '<td>'+formData.get('osDateTime')+'</td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editOrderModal" data-id="'+id+'"><i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" data-id="'+id+'"><i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                // -> edit
                bootstrapAlert("Order " + id + " modified", 'success', alertPlaceholder);
                document.querySelector('tr[data-id="'+id+'"]').innerHTML = newInnerHTML;

                evidenceRow(document.querySelector('tr[data-id="'+id+'"]'));
                bootstrap.Modal.getOrCreateInstance(editOrderModal).hide();

            } else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});


editOrderModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    id = button.getAttribute('data-id');

    editOrderForm.classList.toggle('was-validated', false);
    editOrderForm.reset();

    document.getElementById('idOrder').readOnly = true;
    document.getElementById('idCustomer').readOnly = true;
    document.getElementById('ooDateTime').readOnly = true;
    document.getElementById('osDateTime').readOnly = true;

    // -> edit
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath + "/rest/management/orderManagement/editOrder/" + id, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                /*
                console.log("1)");
                console.log(response);
                console.log("2)");
                console.log(response?.[0]);
                console.log("3)");
                console.log(response?.[0].status);
                */
                populateForm(editOrderForm, response?.[0] ?? response);
            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(editOrderModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = editOrderModal.querySelector('.modal-title');
    modalTitle.textContent = 'Edit Order ' + id;
    editOrderButton.textContent = 'Edit Order';
});


deleteOrderForm.addEventListener('submit', (e) => {
    if(!deleteOrderForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(deleteOrderForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open("POST", rootPath + "/rest/management/orderManagement/deleteOrder/" + id, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td>'+id+'</td>'+
                    '<td>'+formData.get('idCustomer')+'</td>'+
                    '<td>'+formData.get('ooDateTime')+'</td>'+
                    '<td>'+formData.get('products')+'</td>'+
                    '<td>'+formData.get('status')+'</td>'+
                    '<td>'+formData.get('osDateTime')+'</td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#editOrderModal" data-id="'+id+'"><i class="fa-solid fa-pen-to-square text-primary"></i></button></td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteOrderModal" data-id="'+id+'"><i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                bootstrapAlert("Order " + id + " cancelled", 'success', alertPlaceholder);
                document.querySelector('tr[data-id="'+id+'"]').innerHTML = newInnerHTML;

                evidenceRow(document.querySelector('tr[data-id="'+id+'"]'));

                //let row = document.querySelector('tr[data-id="'+id+'"]');
                //row.children[5].innerHTML = "0";
                //evidenceRow(row);
                bootstrap.Modal.getOrCreateInstance(deleteOrderModal).hide();

            } else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})


deleteOrderModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    id = button.getAttribute('data-id');

    deleteOrderForm.classList.toggle('was-validated', false);
    deleteOrderForm.reset();

    document.getElementById('idOrderDelete').readOnly = true;
    document.getElementById('idCustomerDelete').readOnly = true;
    document.getElementById('ooDateTimeDelete').readOnly = true;
    document.getElementById('osDateTimeDelete').readOnly = true;

    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath + "/rest/management/orderManagement/deleteOrder/" + id, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                populateForm(deleteOrderForm, response);
            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(deleteOrderModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = deleteOrderModal.querySelector('.modal-title');
    modalTitle.textContent = 'Delete Order ' + id;
    deleteOrderButton.textContent = 'Delete Order';
})