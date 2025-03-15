package com.johnson.portfolio.Controllers;

import com.johnson.portfolio.Models.Contact;
import com.johnson.portfolio.Models.ContactResponse;
import com.johnson.portfolio.Services.EmailServiceImpl;
import com.johnson.portfolio.Util.Validation;
import com.johnson.portfolio.annotation.RateLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    private String emailTo = System.getenv("portfolioMailEmail");

    @PostMapping(value = "/v1/contact", consumes = "application/json", produces = "application/json")
    @RateLimit
    public ResponseEntity<ContactResponse> sendEmail(@RequestBody Contact contact) {

        try {

            List<String> validationErrors = Validation.validateContactForm(contact);

            if (validationErrors.size() > 0){

                return new ResponseEntity<>(new ContactResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), "Validation Error", validationErrors), HttpStatus.BAD_REQUEST);
            }

            emailServiceImpl.sendSimpleMessage(emailTo, contact);

            return new ResponseEntity<>(new ContactResponse(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), "Email sent successfully.", null), HttpStatus.OK);

        } catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new ContactResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Error sending email: "+ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
