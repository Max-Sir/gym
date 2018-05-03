var validColor = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
};

var notValidColor = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
};

var submit = document.getElementById("button_create");
var startDate = document.getElementById("purchase_date");
var endDate = document.getElementById("end_date");
var daysCount = document.getElementById("training_days");
var diet = document.getElementById("diet_area");

var jsRegEx = /[<>]+/;
var notFoundIndex = -1;

var startDateValid = false;
var endDateValid = false;
var daysCountValid = true;
var dietValid = false;

var submitChange = function () {
    if (startDateValid && endDateValid && daysCountValid && dietValid) {
        submit.disabled = false;
        submit.classList.add("active_button");
    } else {
        submit.disabled = true;
        submit.classList.remove("active_button");
    }
};

var checkStartDate = function () {
    var startDateValue = startDate.value;
    var start = new Date(startDateValue);
    start.setHours(0, 0, 0);
    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);

    if (start.getTime() >= currentDate.getTime()) {
        validColor(startDate);
        startDateValid = true;
    } else {
        notValidColor(startDate);
        startDateValid = false;
    }
    submitChange();
};

var checkEndDate = function () {
    var endDateValue = endDate.value;
    var end = new Date(endDateValue);
    end.setHours(0, 0, 0);

    var startDateValue = startDate.value;
    var start = new Date(startDateValue);
    start.setHours(0, 0, 0);

    var currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);

    if (start.getTime() >= end.getTime() && end.getTime() <= currentDate.getTime()) {
        notValidColor(endDate);
        endDateValid = false;
    } else {
        validColor(endDate);
        endDateValid = true;
    }

    submitChange();
};

validColor(daysCount);
var checkDaysCount = function () {
    if (daysCount.value < 2 || daysCount.value > 7) {
        notValidColor(daysCount);
        daysCountValid = false;
    } else {
        validColor(daysCount);
        daysCountValid = true;
    }
    submitChange();
};

var checkDiet = function () {
    if (diet.value.length < 12 || diet.value.search(jsRegEx) > notFoundIndex) {
        diet.classList.remove("valid_text_area");
        diet.classList.add("error_text_area");
        dietValid = false;
    } else {
        diet.classList.remove("error_text_area");
        diet.classList.add("valid_text_area");
        dietValid = true;
    }
    submitChange();
}
