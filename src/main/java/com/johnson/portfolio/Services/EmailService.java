package com.johnson.portfolio.Services;

import com.johnson.portfolio.Models.Contact;

public interface EmailService {
    void sendSimpleMessage(String to, Contact contact);
}
