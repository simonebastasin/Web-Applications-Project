const formConfirmPassword2 = document.getElementById("formConfirmPassword");
formConfirmPassword2.addEventListener('submit', (e) => {
    if(!formConfirmPassword2.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(formConfirmPassword2);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/session/register", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                location.reload();

            }
            else{
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);

    });

const loginForm = document.getElementById("loginForm");
loginForm.addEventListener('submit', (e) => {
    if(!loginForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(loginForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/session/login", true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                location.reload();

            }
            else{
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);

});

