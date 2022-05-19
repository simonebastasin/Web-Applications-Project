const loginFormDrop = document.getElementById("loginFormDrop");
loginFormDrop.addEventListener('submit', (e) => {
    if(!loginFormDrop.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(loginFormDrop);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/session/login", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                location.reload();

            }
            else{
                const alertPlaceholder = document.getElementById('liveAlertPlaceholderDrop');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});