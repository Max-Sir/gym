var passChecked = false;
var firstNameChecked = false;
var lastNameChecked = false;
var loginChecked = false;

var logRegEx = /\W/;
var passwordRegEx = /\d\W/;
var punct = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_\s<>]+/;
var digit = /[0-9]+/;

var minLoginLength = 5;
var minPasswordLength = 4;
var notFoundIndex = -1;

var passwordId = "password";
var confirmPasswordId = "confirm_password";
var submitId = "submit";
var loginId = "login";
var firstNameId = "first_name";
var lastNameId = "last_name";

var submitChange = function () {
    if (firstNameChecked && lastNameChecked && passChecked && loginChecked){
        document.getElementById(submitId).disabled = false;
        document.getElementById("submit").classList.add("active");
    }
};

var checkPassword = function() {
    if (document.getElementById(passwordId).value.search(passwordRegEx) > notFoundIndex ||
        document.getElementById(passwordId).value.length < minPasswordLength) {
        document.getElementById(passwordId).style.backgroundColor = '#eca9bb';
    } else {
        document.getElementById(passwordId).style.backgroundColor = 'rgb(138, 219, 219)';
    }
    if (document.getElementById(passwordId).value == document.getElementById(confirmPasswordId).value) {
        document.getElementById(confirmPasswordId).style.backgroundColor = 'rgb(138, 219, 219)';
        passChecked = true;
    } else {
        document.getElementById(confirmPasswordId).style.backgroundColor = '#eca9bb';
    }
    submitChange();
};

var checkName = function() {
    if (document.getElementById(firstNameId).value.search(punct) > notFoundIndex ||
        document.getElementById(firstNameId).value.search(digit) > notFoundIndex ||
        document.getElementById(firstNameId).value.length < 1) {
        document.getElementById(firstNameId).style.backgroundColor = '#eca9bb';
        firstNameChecked = false;
    } else {
        document.getElementById(firstNameId).style.backgroundColor = 'rgb(138, 219, 219)';
        firstNameChecked = true;
    }
    if (document.getElementById(lastNameId).value.search(punct) > notFoundIndex ||
        document.getElementById(lastNameId).value.search(digit) > notFoundIndex ||
        document.getElementById(lastNameId).value.length < 1) {
        document.getElementById(lastNameId).style.backgroundColor = '#eca9bb';
        lastNameChecked = false;
    } else {
        document.getElementById(lastNameId).style.backgroundColor = 'rgb(138, 219, 219)';
        lastNameChecked = true;
    }
    submitChange();
};

var checkLogin = function() {
    if (document.getElementById(loginId).value.search(logRegEx) > notFoundIndex ||
        document.getElementById(loginId).value.length <= minLoginLength) {
        document.getElementById(loginId).style.backgroundColor = '#eca9bb';
        loginChecked = false;
    } else {
        document.getElementById(loginId).style.backgroundColor = 'rgb(138, 219, 219)';
        loginChecked = true;
    }
    submitChange();
};