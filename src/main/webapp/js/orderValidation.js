var validColor = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
}

var notValidColor = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
}

var checkDate = function () {
    var orderDateValue = document.getElementById("purchase_date").value;
    var orderDate = new Date(orderDateValue);
    orderDate.setHours(0, 0, 0);
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);

    if (orderDate.getTime() >= currentDate.getTime()) {
        validColor(document.getElementById("purchase_date"));
        document.getElementById("button_confirm").disabled = false;
        document.getElementById("button_confirm").classList.add("active_button");
    } else {
        notValidColor(document.getElementById("purchase_date"));
        document.getElementById("button_confirm").disabled = true;
        document.getElementById("button_confirm").classList.remove("active_button");
    }
}
