var check = function() {
    if (document.getElementById('password').value ==
        document.getElementById('confirm_password').value) {
        document.getElementById('password').style.color = 'green';
        document.getElementById('confirm_password').style.color = 'green';
    } else {
        document.getElementById('confirm_password').style.color = 'red';
    }
    if (document.getElementById('login').value.match('(\\w|\\d)') == null){
        document.getElementById('login').style.color = 'red';
    } else {
        document.getElementById('login').style.color = 'green';
    }
}