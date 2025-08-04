package com.hackathon.invoicegenerator.model;

import java.util.List;

public class InvoiceRequest {

    private String username;
    private String email;
    private List<InvoiceItemDTO> items;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<InvoiceItemDTO> getItems() {
        return items;
    }

    public void setInvoiceItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }
}
