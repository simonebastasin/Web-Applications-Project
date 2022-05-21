const ready = (xmlhttp) => {
    if(xmlhttp.status === 200) {
        const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
        const response = JSON.parse(xmlhttp.responseText)[0];
        const imageCheckBox = document.querySelectorAll('.image-check-box ul');
        [...imageCheckBox].forEach(
            (element) => {
                const li = document.createElement('li');
                li.innerHTML =
                    '<label><img src="'+rootPath+"/media/thumb/"+response.resourceId+'" /></label>';
                element.appendChild(li);
            }
        );

        bootstrapAlert(response.message, 'success', alertPlaceholder);
    } else {
        const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
        bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
        updateProgressBar(0, true);
    }
};

initDropArea(document.getElementById('dropArea'), ready);