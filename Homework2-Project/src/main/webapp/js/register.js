const pillsLoginTab = document.getElementById('pillsLoginButton');
pillsLoginTab.addEventListener('shown.bs.tab', function (event) {
    //event.target // newly activated tab
    if(!event.relatedTarget) {
        history.replaceState(null, '', 'login');
    } else {
        history.pushState(null, '', 'login');
    }
    document.querySelector('title').textContent = 'Login | Electromechanics Shop';
});

const pillsRegisterTab = document.getElementById('pillsRegisterButton');
pillsRegisterTab.addEventListener('shown.bs.tab', function (event) {
    //event.target // newly activated tab
    if(!event.relatedTarget) {
        history.replaceState(null, '', 'register');
    } else {
        history.pushState(null, '', 'register');
    }
    document.querySelector('title').textContent = 'Register | Electromechanics Shop';
});

let loadPath = window.location.pathname.split('/').filter(Boolean).pop();
if(loadPath === 'register') {
    let tab = bootstrap.Tab.getOrCreateInstance(pillsRegisterTab);
    tab.show();
} else if(loadPath === 'login') {
    let tab = bootstrap.Tab.getOrCreateInstance(pillsLoginTab);
    tab.show();
}

