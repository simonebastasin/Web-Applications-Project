const createTicketForm = document.getElementById("createTicketForm");
const createTicketModal = document.getElementById('createTicketModal')
var alias;
createTicketForm.addEventListener('submit', (e) => {
    if(!createTicketForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(createTicketForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/rest/ticket/create/"+ alias, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                idTicket = (response?.[0] ?? response).id;
                bootstrapAlert("Ticket #" +idTicket + " for product " + alias + " was created <a href='"+rootPath +"/ticket/list'"+">View Ticket </a>", 'info', document.getElementById("liveAlertPlaceholder"),-1);
                bootstrap.Modal.getOrCreateInstance(createTicketModal).hide();

            }
            else{
                const alertPlaceholder = document.getElementById('formAlertPlaceholderCreateTicket');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);

});


createTicketModal.addEventListener('show.bs.modal', function (event) {

    var button = event.relatedTarget

    alias = button.getAttribute('data-id')

    var modalTitle = createTicketModal.querySelector('.modal-title')

    modalTitle.textContent = 'Create ticket for product ' + alias;
});