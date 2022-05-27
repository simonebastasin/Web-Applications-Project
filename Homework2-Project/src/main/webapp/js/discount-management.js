const discountTable = document.getElementById('discountTable');
const discountTableBody = discountTable.getElementsByTagName('tbody')[0] ?? discountTable;
const addDiscountButton = document.getElementById('addDiscountButton');
const addDiscountForm = document.getElementById('addDiscountForm');
const addDiscountModal = document.getElementById('addDiscountModal');

const deleteDiscountForm = document.getElementById('deleteDiscountForm');
const deleteDiscountModal = document.getElementById('deleteDiscountModal');
const deleteDiscountButton = document.getElementById('deleteDiscountButton');

let arrayName = [];
let idDiscount;

var startDateAttribute;

async function asyncResolveNameProduct(product) {
    var url = rootPath + '/rest/products/details/'+product;
    const response = await fetch(url);
    const json = await response.json();
    return {alias: product, name: json[0].name };
}

addDiscountForm.addEventListener('submit', (e) => {
    let createDiscount = (idDiscount  === null);

    if(!addDiscountForm.checkValidity()) {
        document.getElementById('navDiscountInfo').click();

        return;
    }
    e.preventDefault();

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
    xmlhttp.onreadystatechange = async function () {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                if (createDiscount) {
                    const response = JSON.parse(xmlhttp.responseText);
                    idDiscount = (response?.[0] ?? response).resourceId;
                }
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                const response = JSON.parse(xmlhttp.responseText);

                idDiscount = response.resourceId;

                const array = formData.getAll('productList');

                let promise = await Promise.all(array.map(element => asyncResolveNameProduct(element)));
                let arrayHtml = promise.map(element => '<a href=' + rootPath + '/products/details/' + element.alias + '>' + element.name + '</a>').join(', ');

                let newInnerHTML =
                    '<td>' + response.resourceId + '</td>' +
                    '<td>' + formData.get('percentage') + '%</td>' +
                    '<td>' + formatDate(new Date(formData.get('startDate'))) + '</td>' +
                    '<td>' + formatDate(new Date(formData.get('endDate'))) + '</td>' +
                    '<td>' + arrayHtml + '</td>' +
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#addDiscountModal" data-id="' + response.resourceId + '"> <i class="fa-solid fa-pen-to-square text-primary"></i></button></td>' +
                    '<td><button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#deleteDiscountModal" data-id="' + response.resourceId + '"> <i class="fa-solid fa-trash-can text-danger"></i></button></td>';

                if (createDiscount) {
                    bootstrapAlert("Discount " + idDiscount + " created", 'success', alertPlaceholder);
                    let tr = document.createElement('tr');
                    tr.setAttribute('data-id', idDiscount);
                    tr.innerHTML = newInnerHTML;
                    discountTableBody.insertBefore(tr, discountTableBody.firstChild);
                } else {
                    bootstrapAlert("Discount " + idDiscount + " modified", 'success', alertPlaceholder);
                    document.querySelector('tr[data-id="' + idDiscount + '"]').innerHTML = newInnerHTML;

                    const start = new Date();
                    let monthCorrectto = start.getMonth()+1;
                    var day = "";
                    var month = "";
                    if(start.getDate()<10) {
                        day = "0" + start.getDate();
                    } else {
                        day = ""+start.getDate();
                    }

                    if(monthCorrectto<10) {
                        month = "0" + monthCorrectto;
                    } else {
                        month = ""+monthCorrectto;
                    }
                    startDate.setAttribute("min", start.getFullYear()+"-"+month+"-"+day);
                }
                bootstrap.Modal.getOrCreateInstance(addDiscountModal).hide();

                evidenceRow(document.querySelector('tr[data-id="' + idDiscount + '"]'));
            } else {
                const alertPlaceholder = document.getElementById('formAlertPlaceholder');
                bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});

addDiscountModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract id from data-id attributes
    idDiscount = button.getAttribute('data-id');
    let createDiscount = (idDiscount  === null);

    toogleWasValidated(addDiscountForm, false);
    addDiscountForm.reset();
    if(createDiscount) {
        let modalTitle = addDiscountModal.querySelector('.modal-title');
        modalTitle.textContent = 'Add Discount';
        addDiscountButton.textContent = 'Add Discount';
    } else {
        const xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", rootPath+"/rest/management/discountManagement/editDiscount/"+idDiscount, true);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === XMLHttpRequest.DONE) {
                if(xmlhttp.status === 200) {
                    const response = JSON.parse(xmlhttp.responseText).discountListProduct;
                    let fullProductList = response.productList; // for backup
                    let respondeElaborated = Object.assign({}, response, {productList : response.productList.map(it =>  it.alias)});
                    const response1 = JSON.parse(xmlhttp.responseText).discountListProduct;

                    startDateAttribute = new Date(respondeElaborated.discount.startDate.date);
                    populateForm(addDiscountForm, respondeElaborated);
                    if(!createDiscount){
                        var startDate = document.getElementById("startDate");

                        var monthCorrect = startDateAttribute.getMonth()+1;

                        var day = "";
                        var month = "";
                        if(startDateAttribute.getDate()<10) {
                            day = "0" + startDateAttribute.getDate();
                        } else {
                            day = ""+startDateAttribute.getDate();
                        }

                        if(monthCorrect<10) {
                            month = "0" + (monthCorrect);
                        } else {
                            month = ""+(monthCorrect);
                        }

                        startDate.setAttribute("min", startDateAttribute.getFullYear()+"-"+month+"-"+day);
                    }

                } else {
                    const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                    bootstrapAlert(parseError(xmlhttp), 'danger', alertPlaceholder);
                    bootstrap.Modal.getOrCreateInstance(addDiscountModal).hide();
                }
            }
        }
        xmlhttp.send();

        let modalTitle = addDiscountModal.querySelector('.modal-title');
        modalTitle.textContent = 'Edit Discount: ' + idDiscount;
        addDiscountButton.textContent = 'Edit Discount';
    }
});

deleteDiscountForm.addEventListener('submit', (e) => {
    if(!deleteDiscountForm.checkValidity()) return;

    e.preventDefault();
    const formData = new FormData(deleteDiscountForm);
    const urlencodedData = new URLSearchParams(formData);
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open("POST", rootPath + "/rest/management/discountManagement/deleteDiscount/" + idDiscount, true);

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert("Discount " + idDiscount + " removed", 'success', alertPlaceholder);
                let row = document.querySelector('tr[data-id="'+idDiscount+'"]');
                row.remove();

                bootstrap.Modal.getOrCreateInstance(deleteDiscountModal).hide();

            } else {

                const alertPlaceholder = document.getElementById('formAlertPlaceholderDelete');
                bootstrapAlert(xmlhttp.responseText !== "" ? (xmlhttp.responseText.startsWith("<!doctype html>") ?  parseServletError(xmlhttp.response): xmlhttp.responseText ): (xmlhttp.statusText !== ""? 'Error: '+ xmlhttp.statusText : "Generic error"), 'danger', alertPlaceholder);
            }
        }
    }
    xmlhttp.send(urlencodedData);
});

deleteDiscountModal.addEventListener('show.bs.modal', (e) => {
    // Button that triggered the modal
    var button = e.relatedTarget;
    // Extract id from data-id attributes
    idDiscount = button.getAttribute('data-id');

    toogleWasValidated(deleteDiscountForm, false);
    deleteDiscountForm.reset();

    const xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", rootPath+"/rest/management/discountManagement/deleteDiscount/"+idDiscount, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if(xmlhttp.status === 200) {
                const response = JSON.parse(xmlhttp.responseText);
                populateForm(deleteDiscountForm, response["discount"] ?? response,);
                document.getElementById('percentageDelete').readOnly = true;
                document.getElementById('percentageDelete').disabled = true;
                document.getElementById('startDateDelete').readOnly = true;
                document.getElementById('startDateDelete').disabled = true;
                document.getElementById('endDateDelete').readOnly = true;
                document.getElementById('endDateDelete').disabled = true;

            } else {
                const alertPlaceholder = document.getElementById('liveAlertPlaceholder');
                bootstrapAlert(xmlhttp.statusText !== "" ? 'Error: '+xmlhttp.status : 'Generic error', 'danger', alertPlaceholder);
                bootstrap.Modal.getOrCreateInstance(deleteDiscountModal).hide();
            }
        }
    }
    xmlhttp.send();

    let modalTitle = deleteDiscountModal.querySelector('.modal-title');
    modalTitle.textContent = 'Delete Discount: ' + idDiscount;
    deleteDiscountButton.textContent = 'Delete Discount';
});