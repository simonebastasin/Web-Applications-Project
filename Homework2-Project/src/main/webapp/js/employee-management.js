const employeeTable = document.getElementById('employeeTable');
const employeeTableBody = employeeTable.getElementsByTagName('tbody')[0] ?? employeeTable;

const addEmployeeButton = document.getElementById('addEmployeeButton');
const addEmployeeForm = document.getElementById('addEmployeeForm');
const addEmployeeModal = document.getElementById('addEmployeeModal');

const deleteCustomerButton = document.getElementById('deleteEmployeeButton');
const deleteCustomerForm = document.getElementById('deleteEmployeeForm');
const deleteCustomerModal = document.getElementById('deleteEmployeeModal');


let username;


addEmployeeForm.addEventListener('submit', (e) => {
    if(!addEmployeeForm.checkValidity()) return;

    e.preventDefault();
    let createEmployee = (username  === null);
    const formData = new FormData(addEmployeeForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    if(createEmployee) { // -> add
        xmlhttp.open("POST", rootPath + "/rest/management/employeeManagement/createEmployee", true);
        username = formData.get('username');
    }
    else { // -> edit
        //alert("send edit " + username);
        xmlhttp.open("POST", rootPath + "/rest/management/employeeManagement/editEmployee/" + username, true);
        //formData.delete('username');
    }

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td>'+username+'</td>'+
                    '<td>'+formData.get('name')+'</td>'+
                    '<td>'+formData.get('surname')+'</td>'+
                    '<td>'+formData.get('role')+'</td>'+
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addEmployeeModal" data-bs-whatever="'+username+'"><i class="fa-solid fa-pen-to-square text-primary"></i></button></td>' +
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteEmployeeModal" data-bs-whatever="'+username+'"><i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                if(createEmployee) { // -> add
                    bootstrapAlert("Employee " + username + " created", 'success', alertPlaceholder);
                    let tr = document.createElement('tr');
                    tr.id = username;
                    tr.innerHTML = newInnerHTML;
                    productTableBody.appendChild(tr);

                } else { // -> edit
                    bootstrapAlert("Employee " + username + " modified", 'success', alertPlaceholder);
                    document.getElementById(username).innerHTML = newInnerHTML;
                }

                bootstrap.Modal.getOrCreateInstance(addEmployeeModal).hide();
                evidenceRow( document.getElementById(username));

            } else {
                if(createEmployee) username = null;
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});


addEmployeeModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    username = button.getAttribute('data-bs-whatever');
    let createEmployee = (username === null);

    addEmployeeForm.classList.toggle('was-validated', false);
    addEmployeeForm.reset();

    document.getElementById('username').disabled = !createEmployee;

    if(createEmployee) { // -> add
        let modalTitle = addProductModal.querySelector('.modal-title');
        modalTitle.textContent = 'Add employee';
        addProductButton.textContent = 'Add employee';

    } else { // -> edit
        const xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", rootPath + "/rest/management/employeeManagement/editEmployee/" + username, true);
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if (xmlhttp.status === 200) {
                    const response = JSON.parse(xmlhttp.responseText);
                    populateForm(addEmployeeForm, response.employeeList?.[0] ?? response.employee ?? response);
                } else {
                    const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                    bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addEmployeeModal).hide();
                }
            }
        }
        xmlhttp.send();

        let modalTitle = addEmployeeModal.querySelector('.modal-title');
        modalTitle.textContent = 'Edit employee ' + username;
        addEmployeeButton.textContent = 'Edit employee';
    }
});