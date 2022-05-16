const discountTable = document.getElementById('discountTable');
const addDiscountButton = document.getElementById('addDiscountButton');
const addDiscountForm = document.getElementById('addDiscountForm');
const addDiscountModal = document.getElementById('addDiscountModal');
let idDiscount;

addDiscountForm.addEventListener('submit', (e) => {
    if(!addDiscountForm.checkValidity()) return;

    e.preventDefault();
    let createDiscount = (idDiscount  === null);
    const formData = new FormData(addDiscountForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();


    if(createDiscount) {
       //alert("send create");
        xmlhttp.open("POST", rootPath + "/rest/management/discountManagement/createDiscount", true);
    } else {
        //alert("send edit "+idDiscount);
        xmlhttp.open("POST", rootPath + "/rest/management/discountManagement/editDiscount/" + idDiscount, true);

    }

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                let newInnerHTML =
                    '<td>'+formData.get('percentage')+'</td>'+
                    '<td>'+formData.get('start')+'</td>'+
                    '<td>'+formData.get('end')+'</td>'+
                    '<td>'+formData.getAll('productList').join(' ')+'</td>'+
                    '<td><button type="button" class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#addDiscountModal" data-bs-whatever="'+idDiscount+'">Edit</button></td>'+
                    '<td><button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteDiscountModal" data-bs-whatever="'+idDiscount+'">Delete</button></td>';
                if(createDiscount) {
                    bootstrapAlert("The discount was created", 'success', alertPlaceholder);
                    let tr = document.createElement('tr');
                    tr.id = idDiscount;
                    tr.innerHTML = newInnerHTML;
                    discountTable.appendChild(tr);
                }
                else {
                    bootstrapAlert("The discount was modified", 'success', alertPlaceholder);
                    document.getElementById(idDiscount).innerHTML = newInnerHTML;
                }
                bootstrap.Modal.getOrCreateInstance(addDiscountModal).hide();
            }else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(xmlhttp.responseText !== "" ? xmlhttp.responseText : (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});

addDiscountModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract info from data-bs-* attributes
    idDiscount = button.getAttribute('data-bs-whatever');
    let createDiscount = (idDiscount  === null);

    addDiscountForm.classList.toggle('was-validated', false);
    addDiscountForm.reset();
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
