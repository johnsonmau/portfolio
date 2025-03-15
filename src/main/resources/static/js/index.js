$( document ).ready(function() {

});

function customSubmit(event) {
    event.preventDefault();  // This stops the form submission/redirect

    var sendButton = document.getElementById('sendContactButton');
    sendButton.disabled = true;

    var contactBody = {
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        email: $("#email").val(),
        messageSubject: $("#subject").val(),
        messageBody: $("#message").val()
    };

    $.ajax({
        url: "/v1/contact",
        type: "POST",
        data: JSON.stringify(contactBody),
        contentType: "application/json; charset=utf-8",
        success: function (successData) {
            console.log("Success:", successData);
            $('#successModal').modal('show');
        },
        error: function (errorData) {
            createWarningAlert()
        },
        complete: function() {
            // Re-enable the button after the request is complete (success or error)
            sendButton.disabled = false;
        }
    });
}

function createSuccessAlert() {
    // Create the div element for the alert
    const alertDiv = document.createElement('div');

    // Add the alert classes
    alertDiv.classList.add('alert', 'alert-success');

    // Set the role attribute
    alertDiv.setAttribute('role', 'alert');

    // Add the content inside the alert
    alertDiv.innerHTML = 'Message sent successfully! Be on the lookout for a response soon.';

    // Append the alert div to the body (or any other desired container)
    document.body.appendChild(alertDiv);
}


function createWarningAlert() {
    // Create the div element for the alert
    const alertDiv = document.createElement('div');

    // Add the alert classes
    alertDiv.classList.add('alert', 'alert-danger');

    // Set the role attribute
    alertDiv.setAttribute('role', 'alert');

    // Add the content inside the alert
    alertDiv.innerHTML = 'Something went wrong. Please try again.';

    // Append the alert div to the body (or any other desired container)
    document.body.appendChild(alertDiv);
}
