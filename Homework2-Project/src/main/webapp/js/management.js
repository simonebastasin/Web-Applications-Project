function updateProgressBar(progress, isError) {
    uploadImageProgress.classList.toggle("d-none", false);
    uploadImageProgressBar.style.width = (isError ? 100 : progress) + "%";
    uploadImageProgressBar.ariaValueNow = isError ? 100 : progress;
    uploadImageProgressBar.classList.toggle('progress-bar-animated', progress < 100 && !isError);
    uploadImageProgressBar.classList.toggle('progress-bar-striped', progress < 100 && !isError);
    uploadImageProgressBar.classList.toggle('bg-danger', isError);
}
