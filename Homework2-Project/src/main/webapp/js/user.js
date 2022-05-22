const editUserForm = document.getElementById("editUserForm");
const editUserModal = document.getElementById("editUserModal");
const editPasswordForm = document.getElementById("formConfirmPassword");
const editPasswordModal = document.getElementById("changePasswordModal");

editUserForm.addEventListener('submit', (e) => {
    if(!editUserForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(editUserForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/user/modify", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML;

                let item = document.querySelector('ul[data-id]');

                if(formData.has('address')) {
                    newInnerHTML =
                        '<li class="list-group-item">Name: ' + formData.get('name') + '</li>' +
                        '<li class="list-group-item">Surname: ' + formData.get('surname') + '</li>' +
                        '<li class="list-group-item">Address: ' + formData.get('address') + '</li>' +
                        '<li class="list-group-item">Phone number: ' + formData.get('phoneNumber') + '</li>' +
                        '<li class="list-group-item">' + item.children[4].textContent + '</li>' +
                        '<li class="list-group-item">Fiscal code: ' + formData.get('fiscalCode') + '</li>';
                }
                else {

                    newInnerHTML =
                        '<li class="list-group-item">Name: ' + formData.get('name') + '</li>' +
                        '<li class="list-group-item">Surname: ' + formData.get('surname') + '</li>'+
                        '<li class="list-group-item">' + item.children[2].textContent + '</li>';
                }

                item.innerHTML = newInnerHTML;

                bootstrapAlert("Details was modified successfully", 'success', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(editUserModal).hide();

            }
            else{
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);

});

editUserModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    id = button.getAttribute('data-id');

    toogleWasValidated(editUserForm, false);
    editUserForm.reset();

    let modalTitle = editUserModal.querySelector('.modal-title');
    modalTitle.textContent = 'User ' + id;
});

editPasswordForm.addEventListener('submit', (e) => {
    if(!editPasswordForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(editPasswordForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/user/password", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("Password was modified successfully", 'success', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(editPasswordModal).hide();
            }
            else{
                const alertPlaceholder = document.getElementById('formPasswordAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});