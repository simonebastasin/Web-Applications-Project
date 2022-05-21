const pillsLoginTab = document.getElementById('pillsLoginButton');
const pillsRegisterTab = document.getElementById('pillsRegisterButton');
let loadPath = window.location.pathname.split('/').filter(Boolean).pop();
const registerForm = document.getElementById("formConfirmPassword");
const loginForm = document.getElementById("loginForm");


pillsLoginTab.addEventListener('shown.bs.tab', function (event) {
    //event.target // newly activated tab
    if(!event.relatedTarget) {
        history.replaceState(null, '', 'login');
    } else {
        history.pushState(null, '', 'login');
    }
    document.querySelector('title').textContent = 'Login | Electromechanics Shop';
});

pillsRegisterTab.addEventListener('shown.bs.tab', function (event) {
    //event.target // newly activated tab
    if(!event.relatedTarget) {
        history.replaceState(null, '', 'register');
    } else {
        history.pushState(null, '', 'register');
    }
    document.querySelector('title').textContent = 'Register | Electromechanics Shop';
});

(function () {
    if (loadPath === 'register') {
        let tab = bootstrap.Tab.getOrCreateInstance(pillsRegisterTab);
        tab.show();
    } else if (loadPath === 'login') {
        let tab = bootstrap.Tab.getOrCreateInstance(pillsLoginTab);
        tab.show();
    }
})();

registerForm.addEventListener('submit', (e) => {
    if(!registerForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(registerForm);
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


