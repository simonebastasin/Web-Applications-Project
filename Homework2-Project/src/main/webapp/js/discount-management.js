const addDiscountButton = document.getElementById('addDiscountButton');
const addDiscountForm = document.getElementById('addDiscountForm');
const addDiscountModal = document.getElementById('addDiscountModal');
const addDiscountSubmit = document.getElementById('addDiscountSubmit');
let idDiscount;

addDiscountButton.addEventListener('click', (e) => {
    addDiscountSubmit.click(); // only  html5 required
});
addDiscountForm.addEventListener('submit', (e) => {
    if(!addDiscountForm.checkValidity()) return;

    e.preventDefault();
    let createDiscount = (idDiscount  === null);

    if(createDiscount) {
        //alert("send create");
    } else {
        //alert("send edit "+idDiscount);
    }
});

addDiscountModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    idDiscount = button.getAttribute('data-bs-whatever');
    let createDiscount = (idDiscount  === null);

    if(createDiscount) {
        let modalTitle = addDiscountModal.querySelector('.modal-title');
        modalTitle.textContent = 'Add discount';
        addDiscountButton.textContent = 'Add discount';
    } else {
        const xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", rootPath+"/rest/management/discountManagement/editDiscount/"+idDiscount, true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    const response = JSON.parse(xmlhttp.responseText).discountListProduct;
                    let fullProductList = response.productList; // for backup
                    const respondeElaborated = Object.assign({}, response, {productList : response.productList.map(it =>  it.alias)});
                    console.log(respondeElaborated);
                    populateForm(addDiscountForm, respondeElaborated);

                } else {
                    const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                    bootstrapAlert(xmlhttp.statusText !== "" ? 'Error: '+xmlhttp.status : 'Generic erro', 'danger', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addDiscountModal).hide();
                }
            }
        }
        xmlhttp.send();

        let modalTitle = addDiscountModal.querySelector('.modal-title');
        modalTitle.textContent = 'Edit discount ' + idDiscount;
        addDiscountButton.textContent = 'Edit discount';
    }
});
