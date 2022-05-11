function check_pass() {
    const password = document.getElementById("password").value;
    const confirm_password = document.getElementById("confirm_password").value;
    const message = document.getElementById("Message");
    const submit = document.getElementById('submit');
        if(password != confirm_password ) {
            ;
            submit.disabled = true;
            message.style.color = "Red";
            message.innerHTML = "Passwords do NOT match!"
        } else {;
            submit.disabled = false
            message.style.color = "Green";
            message.innerHTML = "Passwords match!"
        }
    if (confirm_password== ""){
        message.innerHTML = "";
        submit.disabled = true;
    }
}
const pw = document.getElementById("password");
const newpw = document.getElementById("confirm_password");

pw.addEventListener("blur",check_pass);
newpw.addEventListener("blur",check_pass);