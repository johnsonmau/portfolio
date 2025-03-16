$( document ).ready(function() {
    var spinner = $('#spinner');
    var emailFormWarn = $('#emailFormWarn');
    spinner.hide();  // Use jQuery's hide() method to hide the spinner
    emailFormWarn.hide();
});

function isValidEmail(email) {
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
}

function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function customSubmit(event) {
    event.preventDefault();  // This stops the form submission/redirect

    var contactBody = {
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        email: $("#email").val(),
        messageSubject: $("#subject").val(),
        messageBody: $("#message").val()
    };

    var firstNameLabel = document.getElementById('fName');
    var lastNameLabel = document.getElementById('lName');
    var emailLabel = document.getElementById('emailLabel');
    var subLabel = document.getElementById('subLabel');
    var messageLabel = document.getElementById('messageLabel');

    validationError = false;

    if (contactBody.firstName.trim().length < 1){
        firstNameLabel.style.color = 'red';  // Example: change text color
        firstNameLabel.style.fontWeight = 'bold';  // Example: make text bold
        validationError = true;
    }  else {
        firstNameLabel.style.color = 'black';  // Example: change text color
        firstNameLabel.style.fontWeight = 'normal';
    }
    if (contactBody.lastName.trim().length < 1){
        lastNameLabel.style.color = 'red';  // Example: change text color
        lastNameLabel.style.fontWeight = 'bold';  // Example: make text bold
        validationError = true;
    }  else {
        lastNameLabel.style.color = 'black';  // Example: change text color
        lastNameLabel.style.fontWeight = 'normal';
    }
    if (contactBody.email.trim().length < 1){
        emailLabel.style.color = 'red';  // Example: change text color
        emailLabel.style.fontWeight = 'bold';  // Example: make text bold
        validationError = true;
    }  else {
        var emailFormWarn = $('#emailFormWarn');
        if (isValidEmail(contactBody.email.trim()) == false){
            emailLabel.style.color = 'red';  // Example: change text color
            emailLabel.style.fontWeight = 'bold';  // Example: make text bold
            emailFormWarn.show();
            validationError = true;
        } else {
            emailFormWarn.hide();
            emailLabel.style.color = 'black';  // Example: change text color
            emailLabel.style.fontWeight = 'normal';
        }
    }
    if (contactBody.messageSubject.trim().length < 1){
        subLabel.style.color = 'red';  // Example: change text color
        subLabel.style.fontWeight = 'bold';  // Example: make text bold
        validationError = true;
    }  else {
        subLabel.style.color = 'black';  // Example: change text color
        subLabel.style.fontWeight = 'normal';
    }
    if (contactBody.messageBody.trim().length < 1){
        messageLabel.style.color = 'red';  // Example: change text color
        messageLabel.style.fontWeight = 'bold';  // Example: make text bold
        validationError = true;
    }  else {
        messageLabel.style.color = 'black';  // Example: change text color
        messageLabel.style.fontWeight = 'normal';
    }

    if (validationError) return;

    var sendContactButton = $('#sendContactButton');
    var spinner = $('#spinner');

    sendContactButton.hide();
    spinner.show();  // Use jQuery's hide() method to hide the spinner

    $.ajax({
        url: getContextPath()+"/v1/contact",
        type: "POST",
        data: JSON.stringify(contactBody),
        contentType: "application/json; charset=utf-8",
        success: function (successData) {
            spinner.hide();  // Use jQuery's hide() method to hide the spinner
            sendContactButton.show();
            alert("Message sent successfully. Check your email for confirmation.")
            location.reload();
        },
        error: function (errorData) {
            spinner.hide();  // Use jQuery's hide() method to hide the spinner
            sendContactButton.show();

            statusCd = errorData.responseJSON.statusCd;

            if (statusCd == 429){
                alert("Too many requests. Please try again later.")
            } else {
                alert("Something went wrong. Please try again.")
            }
        },
        complete: function() {

        }
    });
}




