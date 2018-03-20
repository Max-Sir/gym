var passChecked = false;
var firstNameChecked = false;
var lastNameChecked = false;
var loginChecked = false;

var logRegEx = /\W/;
var passwordRegEx = /\d\W/;
var punct = /[.,!?()\\|\[\]`@$^*-+=:;№#"'_]+/;
var digigt =/[0-9]+/;

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
    } else {
        document.getElementById(submitId).disabled = true;
    }
};

var checkPass = function() {
    if (document.getElementById(passwordId).value.search(passwordRegEx) > notFoundIndex
        || document.getElementById(passwordId).value.length < minPasswordLength){
        document.getElementById(passwordId).style.color = 'red';
    } else {
        document.getElementById(passwordId).style.color = 'green';
    }
    if (document.getElementById(passwordId).value == document.getElementById(confirmPasswordId).value) {
        document.getElementById(confirmPasswordId).style.color = 'green';
        passChecked = true;
    } else {
        document.getElementById(confirmPasswordId).style.color = 'red';
    }
    submitChange();
};

var checkName = function () {
    if (document.getElementById(firstNameId).value.search(punct) > notFoundIndex
        || document.getElementById(firstNameId).value.search(digigt) > notFoundIndex
        || document.getElementById(firstNameId).value.length < 1){
        document.getElementById(firstNameId).style.color = 'red';
        firstNameChecked = false;
    } else {
        document.getElementById(firstNameId).style.color = 'green';
        firstNameChecked = true;
    }
    if (document.getElementById(lastNameId).value.search(punct) > notFoundIndex
        || document.getElementById(lastNameId).value.search(digigt) > notFoundIndex
        || document.getElementById(lastNameId).value.length < 1){
        document.getElementById(lastNameId).style.color = 'red';
        lastNameChecked = false;
    } else {
        document.getElementById(lastNameId).style.color = 'green';
        lastNameChecked = true;
    }
    submitChange();
};

var checkLoginForPattern = function(){
    if (document.getElementById(loginId).value.search(logRegEx) > notFoundIndex){
        document.getElementById(loginId).style.color = 'red';
        loginChecked = false;
    } else {
        document.getElementById(loginId).style.color = 'green';
        loginChecked = true;
    }
    submitChange();
};
