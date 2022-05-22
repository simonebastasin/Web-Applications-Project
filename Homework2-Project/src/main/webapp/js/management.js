function updateProgressBar(progress, isError) {
    uploadImageProgress.classList.toggle("d-none", false);
    uploadImageProgressBar.style.width = (isError ? 100 : progress) + "%";
    uploadImageProgressBar.ariaValueNow = isError ? 100 : progress;
    uploadImageProgressBar.classList.toggle('progress-bar-animated', progress < 100 && !isError);
    uploadImageProgressBar.classList.toggle('progress-bar-striped', progress < 100 && !isError);
    uploadImageProgressBar.classList.toggle('bg-danger', isError);
}

function evidenceRow(row,timeout=1700) {
    [...row.children].forEach(
        (element) => {
            element.classList.add('bg-primary')
            element.classList.add('text-white')
        }
    );
    [...row.querySelectorAll('a, button, i')].forEach(
        (element) => {
            element.classList.add('text-white')
        }
    );

    if(timeout!==-1)
    setTimeout(function(){
            [...row.children].forEach(
                (element) => {
                    element.classList.remove('bg-primary')
                    element.classList.remove('text-white')
                }
            );
            [...row.querySelectorAll('a, button, i')].forEach(
                (element) => {
                    element.classList.remove('text-white')
                }
            )
        }, timeout);
}

function uploadFile(multipartData) {
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/media/upload", true);
    xmlhttp.upload.addEventListener("progress", function (e) {
        let progress = ((e.loaded * 100.0 / e.total) || 100);
        updateProgressBar(progress, false);
    });
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            ready(xmlhttp, multipartData.get('file').name);
        }
    }
    xmlhttp.send(multipartData);
    updateProgressBar(0, false);
}

function initDropArea(dropArea, ready) {

    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, preventDefaults, false)
    });
    dropArea.addEventListener('dragenter', highlight, false);
    dropArea.addEventListener('dragover', forceHighlight, false);
    dropArea.addEventListener('dragleave', unHighlight, false);
    dropArea.addEventListener('drop', handleDrop, false);
    dropArea.addEventListener('drop', forceUnHighlight, false);
    dropArea.addEventListener('paste', (event) => {
        let files = (event.clipboardData || window.clipboardData).files;
        if(files) {
            handleFiles(files);
        }
    });

    var count = 0;

    function highlight(e) {
        count++;
        dropArea.classList.toggle('highlight', true)
        dropArea.classList.toggle('no-highlight', false)
    }

    function forceHighlight(e) {
        dropArea.classList.toggle('highlight', true)
        dropArea.classList.toggle('no-highlight', false)
    }

    function unHighlight(e) {
        count--;
        if (count === 0) {
            dropArea.classList.toggle('highlight', false)
            dropArea.classList.toggle('no-highlight', true)
        }
    }

    function forceUnHighlight(e) {
        count = 0;
        dropArea.classList.toggle('highlight', false)
        dropArea.classList.toggle('no-highlight', true)
    }

    function preventDefaults(e) {
        e.preventDefault()
        e.stopPropagation()
    }

    function handleDrop(e) {
        let dt = e.dataTransfer
        let files = dt.files

        handleFiles(files)
    }

    function handleFiles(files) {
        ([...files]).map(file => {
            let formData = new FormData();
            formData.append('file', file)
            return formData;
        }).forEach(uploadFile);

    }
}

function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(date) {
    return [
        padTo2Digits(date.getDate()),
        padTo2Digits(date.getMonth() + 1),
        date.getFullYear(),
    ].join('/');
}

function formatDateTime(date) {
    return [[
        padTo2Digits(date.getDate()),
        padTo2Digits(date.getMonth() + 1),
        date.getFullYear(),
    ].join('/'),[padTo2Digits(date.getHours()), padTo2Digits(date.getMinutes())].join(':')].join(' ');
}

