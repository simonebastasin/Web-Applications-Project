const formConfirmPassword = document.getElementById('formConfirmPassword');

formConfirmPassword.addEventListener('submit', (e) => {
    const formData = new FormData(formConfirmPassword);
    const password = document.getElementById("newPassword");
    const confirmPassword = document.getElementById("confirmPassword");
    if(formData.get('newPassword') !== formData.get('confirmPassword') ) {
        password.setCustomValidity('Passwords do NOT match!');
        confirmPassword.setCustomValidity('Passwords do NOT match!');
        document.getElementById('newPasswordFeedback').innerHTML =  'Passwords do NOT match!';
        document.getElementById('confirmPasswordFeedback').innerHTML =  'Passwords do NOT match!';
        e.preventDefault();
    } else if(password.validity.valueMissing && confirmPassword.validity.valueMissing) {
        password.setCustomValidity( 'Passwords is empty');
        confirmPassword.setCustomValidity( 'Confirm Passwords is empty');
        document.getElementById('newPasswordFeedback').innerHTML =  'Passwords is empty';
        document.getElementById('confirmPasswordFeedback').innerHTML =  'Confirm Passwords is empty';
        e.preventDefault();
    }else if(password.validity.valueMissing) {
        password.setCustomValidity( 'Passwords is empty');
        confirmPassword.setCustomValidity('');
        document.getElementById('newPasswordFeedback').innerHTML =  'Passwords is empty';
        e.preventDefault();
    } else if(confirmPassword.validity.valueMissing) {
        password.setCustomValidity('');
        confirmPassword.setCustomValidity( 'Confirm Passwords is empty');
        document.getElementById('confirmPasswordFeedback').innerHTML =  'Confirm Passwords is empty';
        e.preventDefault();
    } else{
        password.setCustomValidity('');
        confirmPassword.setCustomValidity('');
    }
});