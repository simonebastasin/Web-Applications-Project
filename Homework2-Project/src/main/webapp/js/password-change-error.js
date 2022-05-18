const formConfirmPassword2 = document.getElementById("formConfirmPassword");
formConfirmPassword2.addEventListener('submit', (e) => {
    if(!formConfirmPassword2.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(formConfirmPassword2);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/user/password", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {

                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("Password was modified", 'success', alertPlaceholder);

            }
            else{
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);

});