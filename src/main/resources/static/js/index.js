$( document ).ready(function() {

    $('#sendContactButton').on( "click", function(e) {

        e.preventDefault();


        var contactBody = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email: $('#email').val(),
            messageSubject: $('#subject').val(),
            messageBody: $('#message').val()
        }

        $.ajax({
            url : '/v1/contact',
            type : 'POST',
            data : JSON.stringify(contactBody),
            contentType: "application/json; charset=utf-8",
            success : function(successData) {
                console.log(successData)
            },
            error : function(errorData) {

                $("#fName").attr('title', 'First name is required');
                $('[data-toggle="tooltip"]').tooltip({trigger: 'manual'}).tooltip('show');

/*                if (errorData != null) {
                    if (errorData.responseJSON != null) {
                        if (errorData.responseJSON.errors != null){
                            var errorArray = errorData.responseJSON.errors;
                            var errorArrayStr = "";

                            for (var i = 0; i < errorArray.length; i++){
                                document.getElementById("errorModalBody").innerHTML += "<h4>- "+errorArray[i]+"</h4><br>"
                            }
                            console.log(errorArrayStr)
                            $("#errorModal").modal('show')
                        }
                    }
                }*/
                console.log(errorData.responseJSON)

                setTimeout(function () {
                    $('[data-toggle="tooltip"]').tooltip({trigger: 'manual'}).tooltip('hide');
                }, 5000);
            }
        });

    });


});