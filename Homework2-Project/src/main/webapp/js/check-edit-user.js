const editUserForm = document.getElementById("editUserForm");
const editUserModal = document.getElementById("editUserModal");
let id;

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

                if(formData.has('address')) {
                    newInnerHTML =
                        '<li class="list-group-item">' + formData.get('name') + '</li>' +
                        '<li class="list-group-item">' + formData.get('surname') + '</li>' +
                        '<li class="list-group-item">' + formData.get('address') + '</li>' +
                        '<li class="list-group-item">' + formData.get('phoneNumber') + '</li>' +
                        '<li class="list-group-item">' + formData.get('email') + '</li>' +
                        '<li class="list-group-item">' + formData.get('fiscalCode') + '</li>';
                }
                else {
                    newInnerHTML =
                        '<li class="list-group-item">' + formData.get('name') + '</li>' +
                        '<li class="list-group-item">' + formData.get('surname') + '</li>'+
                        '<li class="list-group-item">' + formData.get('role') + '</li>';
                }

                const cancelchanges = document.getElementById('cancel');
                cancelchanges.innerHTML = "Go back to detail";

                bootstrapAlert("Details was modified successfully", 'success', alertPlaceholder);
                document.querySelector('ul[data-id="'+id+'"]').innerHTML = newInnerHTML;
                bootstrap.Modal.getOrCreateInstance(editUserModal).hide();

            }
            else{
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                const cancelchanges = document.getElementById("cancel");
                cancelchanges.innerHTML = "Cancel changes";
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);

})

editUserModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    id = button.getAttribute('data-id');

    editUserForm.classList.toggle('was-validated', false);
    editUserForm.reset();

    let modalTitle = editUserModal.querySelector('.modal-title');
    modalTitle.textContent = 'User ' + id;
})