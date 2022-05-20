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
