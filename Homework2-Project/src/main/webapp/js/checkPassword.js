function check_pass() {
    var password = document.getElementById("password");
    var confirm_password = document.getElementById("confirm_password");
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
            document.getElementById('submit').disabled = true;
            document.getElementById("Message").style.color = "Red";
            document.getElementById("Message").innerHTML = "Passwords do NOT match!"
        } else {
            confirm_password.setCustomValidity('');
            document.getElementById('submit').disabled = false
            document.getElementById("Message").style.color = "Green";
            document.getElementById("Message").innerHTML = "Passwords match!"
        }
    if (confirm_password.value == ""){
        document.getElementById("Message").innerHTML = "";
        document.getElementById('submit').disabled = false;
    }
}