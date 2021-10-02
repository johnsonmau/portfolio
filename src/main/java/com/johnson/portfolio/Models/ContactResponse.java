package com.johnson.portfolio.Models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ContactResponse {

    private long statusCd;
    private String status;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;


    public ContactResponse(long statusCd, String status, String description, List<String> errors) {
        this.statusCd = statusCd;
        this.status = status;
        this.description = description;
        this.errors = errors;
    }

    public ContactResponse() {
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public long getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(long statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
