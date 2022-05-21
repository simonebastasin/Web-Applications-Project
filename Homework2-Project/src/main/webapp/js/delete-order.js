const form=document.getElementById("deleteForm");

form.addEventListener('submit', (e) => {


    e.preventDefault();
    const alias=form.getAttribute("data-id");

    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/buy/cancel/"+ alias, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                location.href=rootPath+"/order/detail/" +alias;
            }
        }
    }
    xmlhttp.send();

});
