var feedback = document.getElementById("feedback_id");
var submit = document.getElementById("feedback_submit");

var jsRegEx = /[<>]+/;
var notFoundIndex = -1;

var checkFeedback = function () {

    if (feedback.value.length < 12 || feedback.value.search(jsRegEx) > notFoundIndex) {
        feedback.classList.remove("valid_text_area");
        feedback.classList.add("error_text_area");
        submit.disabled = true;
        submit.classList.remove("active_button");
    } else {
        feedback.classList.remove("error_text_area");
        feedback.classList.add("valid_text_area");
        submit.disabled = false;
        submit.classList.add("active_button");
    }
}
