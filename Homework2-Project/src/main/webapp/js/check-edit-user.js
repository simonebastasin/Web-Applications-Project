const editUserForm = document.getElementById("editUserForm");
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
                const cancelchanges = document.getElementById('cancel');
                cancelchanges.innerHTML = "Go back to detail";
                bootstrapAlert("Details was modified successfully", 'success', alertPlaceholder);

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

});