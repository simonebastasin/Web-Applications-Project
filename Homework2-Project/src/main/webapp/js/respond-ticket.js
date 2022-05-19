const respondTicketModal = document.getElementById('respondTicketModal');
const respondTicketForm = document.getElementById('respondTicketForm');
let id;

respondTicketForm.addEventListener('submit', (e) => {
    if (!respondTicketForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(respondTicketForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open("POST", rootPath + "/rest/ticket/respond/" + id, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let div = document.querySelector('div[data-id="'+id+'"]');
                let ul = div.getElementsByTagName('ul')[0];
                let button = div.getElementsByTagName('button')?.[0];

                let lis = document.createElement("li");
                lis.classList.add('list-group-item');
                lis.innerHTML  = '<b> Ticket Status : <span class = "ticket-'+ formData.get('status') +'">'+ formData.get('status') +'</span></b>';
                ul.appendChild(lis);

                let lid = document.createElement("li");
                lid.classList.add('list-group-item');
                lid.innerHTML  = '<i>'+ formData.get('description') +'</i>';
                ul.appendChild(lid);

                let lit = document.createElement("li");
                lit.classList.add('list-group-item');
                var resp = JSON.parse(xmlhttp.responseText);
                lit.innerHTML  = resp[0].ticketStatusList.at(-1).tsDate.date.replaceAll('-','/').replace('T',' ').slice(0, -3);
                ul.appendChild(lit);

                bootstrapAlert("Ticket respond success", 'success', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(respondTicketModal).hide();

                if(formData.get('status') === "CLOSED") {
                    button.remove();
                }

            }
            else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ): (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
})

respondTicketModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    id = button.getAttribute('data-id');

    respondTicketForm.classList.toggle('was-validated', false);
    respondTicketForm.reset();

    let modalTitle = respondTicketModal.querySelector('.modal-title');
    modalTitle.textContent = 'Respond Ticket ' + id;
})