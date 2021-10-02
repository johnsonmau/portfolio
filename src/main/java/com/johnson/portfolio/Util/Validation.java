package com.johnson.portfolio.Util;

import com.johnson.portfolio.Models.Contact;

import java.util.ArrayList;
import java.util.List;

public class Validation {

    public static List<String> validateContactForm(Contact contact){
        List<String> errorList = new ArrayList<>();

        if (contact != null) {
            if (contact.getFirstName() == null | contact.getFirstName().equals("")) {
                errorList.add("First name is required.");
            }
            if (contact.getLastName() == null | contact.getLastName().equals("")) {
                errorList.add("Last name is required.");
            }
            if (contact.getEmail() == null | contact.getEmail().equals("")) {
                errorList.add("Email is required.");
            }
            if (contact.getMessageSubject() == null | contact.getMessageSubject().equals("")) {
                errorList.add("Message subject is required.");
            }
            if (contact.getMessageBody() == null | contact.getMessageBody().equals("")) {
                errorList.add("Message body is required.");
            }
        }

        return errorList;
    }
}
