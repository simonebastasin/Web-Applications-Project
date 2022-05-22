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
                lis.classList.add('text-start');

                var resp = JSON.parse(xmlhttp.responseText);
                let date = resp[0].ticketStatusList.at(-1).tsDate.date.replaceAll('-','/').replace('T',' ').slice(0, -3);

                lis.innerHTML =
                    '<div class="row align-items-start">' +
                    '<div class="col"><b> Ticket Status :</b></div>' +
                    '<div class="col"><b> <span class = "ticket-' + formData.get('status') + '">' + formData.get('status') + '</span></b></div>' +
                    '</div>' +
                    '<div class="row align-items-start">' +
                    '<div class="col"></div>' +
                    '<div class="col"><i>'+ formData.get('description') + '</i></div>' +
                    '</div>' +
                    '<div class="row align-items-start">' +
                    '<div class="col"></div>' +
                    '<div class="col">'+ date + '</div>' +
                    '</div>';

                ul.appendChild(lis);

                bootstrapAlert("Ticket respond success", 'success', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(respondTicketModal).hide();

                if(formData.get('status') === "CLOSED") {
                    const newItem = document.createElement('span');
                    newItem.textContent = 'The ticket is closed';
                    newItem.classList.add('list-group-item');
                    button.parentNode.replaceChild(newItem, button);
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

    toogleWasValidated(respondTicketForm, false);
    respondTicketForm.reset();

    let modalTitle = respondTicketModal.querySelector('.modal-title');
    modalTitle.textContent = 'Respond Ticket ' + id;
})