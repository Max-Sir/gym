var submit = document.getElementById("save_training_program");
var diet = document.getElementById("diet_area");
var sets_values = document.getElementsByName("sets_count");
var repeats_values = document.getElementsByName("repeats_count");

function mergeNodeLists(a, b) {
    var slice = Array.prototype.slice;
    return slice.call(a).concat(slice.call(b));
}

var values = mergeNodeLists(sets_values, repeats_values);

var jsRegEx = /[<>]+/;
var notFoundIndex = -1;
var isDietValid = true;
var isValuesValid = true;

var submitChange = function () {
    if (isValuesValid && isDietValid) {
        submit.disabled = false;
        submit.classList.add("active_button");
    } else {
        submit.disabled = true;
        submit.classList.remove("active_button");
    }
};

var validColor = function (element) {
    element.classList.add("valid");
    element.classList.remove("notValid");
};

var notValidColor = function (element) {
    element.classList.add("notValid");
    element.classList.remove("valid");
};

for (i = 0; i < values.length; i++) {
    if (values[i].value >= 1) {
        validColor(values[i]);
    } else {
        isValuesValid = false;
        notValidColor(values[i]);
    }
}

submitChange();

var check = function () {
    var isValid = true;
    for (i = 0; i < values.length; i++) {
        if (values[i].value >= 1) {
            validColor(values[i]);
        } else {
            isValid = false;
            notValidColor(values[i]);
        }
    }
    isValuesValid = isValid;
    submitChange();
}

for (i = 0; i < values.length; i++) {
    values[i].addEventListener("input", function () {
        check();
    });
}

var checkDiet = function () {
    if (diet.value.length < 12 || diet.value.search(jsRegEx) > notFoundIndex) {
        diet.classList.remove("valid_text_area");
        diet.classList.add("error_text_area");
        isDietValid = false;
    } else {
        diet.classList.remove("error_text_area");
        diet.classList.add("valid_text_area");
        isDietValid = true;
    }
    submitChange();
}
