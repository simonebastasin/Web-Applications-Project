const createTicketForm = document.getElementById("createTicketForm");
const createTicketModal = document.getElementById('createTicketModal');

const deleteOrderForm=document.getElementById("deleteOrderForm");
const deleteOrderModal = document.getElementById('deleteOrderModal');

let alias;

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
                let idTicket = (response?.[0] ?? response).id;
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

    const button = event.relatedTarget

    alias = button.getAttribute('data-id')

    const modalTitle = createTicketModal.querySelector('.modal-title')

    modalTitle.textContent = 'Create ticket for product ' + alias;
});

deleteOrderForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const orderId=deleteOrderForm.getAttribute("data-id");

    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", rootPath + "/buy/cancel/"+ orderId, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                bootstrapAlert("Order #" +orderId + "  was cancellated", 'success', document.getElementById("liveAlertPlaceholder"),-1);
                bootstrap.Modal.getOrCreateInstance(deleteOrderModal).hide();
                document.getElementById('orderStatus').innerHTML = 'Cancelled';
            } else {
                bootstrapAlert("Unable to cancel Order #" +orderId, 'danger', document.getElementById("liveAlertPlaceholder"),-1);
                bootstrap.Modal.getOrCreateInstance(deleteOrderModal).hide();
            }
        }
    }
    xmlhttp.send();
});