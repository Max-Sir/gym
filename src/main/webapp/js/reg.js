var passChecked = false;
var firstNameChecked = false;
var lastNameChecked = false;
var loginChecked = false;

var checkPass = function() {
    if (document.getElementById('password').value.length < 4){
        document.getElementById('password').style.color = 'red';
        document.getElementById('submit').disabled = true;
    } else {
        document.getElementById('password').style.color = 'green';
    }
    if (document.getElementById('password').value == document.getElementById('confirm_password').value) {

        document.getElementById('confirm_password').style.color = 'green';
        passChecked = true;
        enableSubmit()

    } else {
        document.getElementById('confirm_password').style.color = 'red';
        document.getElementById('submit').disabled = true;
    }

}

var punct = /\W|_/;
var digigt =/\d/;

var checkName = function () {
    if (document.getElementById('first_name').value.search(punct) > -1
        || document.getElementById('first_name').value.search(digigt) > -1
        || document.getElementById('first_name').value.length < 1){

        document.getElementById('first_name').style.color = 'red';
        firstNameChecked = false;
        document.getElementById('submit').disabled = true;

    } else {

        document.getElementById('first_name').style.color = 'green';
        firstNameChecked = true;
        enableSubmit()

    }
    if (document.getElementById('last_name').value.search(punct) > -1
        || document.getElementById('last_name').value.search(digigt) > -1
        || document.getElementById('last_name').value.length < 1){

        document.getElementById('last_name').style.color = 'red';
        lastNameChecked = false;
        document.getElementById('submit').disabled = true;

    } else {

        document.getElementById('last_name').style.color = 'green';
        lastNameChecked = true;
        enableSubmit()

    }

}

var logRegEx = /\W/;

var checkLoginForPattern = function(){
    if (document.getElementById('login').value.search(logRegEx) > -1){

        document.getElementById('login').style.color = 'red';
        document.getElementById('submit').disabled = true;
        loginChecked = false;

    } else {

        document.getElementById('login').style.color = 'green';
        loginChecked = true;
        enableSubmit()

    }

}

var enableSubmit = function () {
    if (firstNameChecked && lastNameChecked && passChecked && loginChecked){
        document.getElementById('submit').disabled = false;
    }
}