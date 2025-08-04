package com.hackathon.invoicegenerator.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private String username;
    private String email;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("invoice")
    private List<InvoiceItem> items;

    public Invoice() {
    }

    public Invoice(Long invoiceId, String username, String email, List<InvoiceItem> items) {
        this.invoiceId = invoiceId;
        this.username = username;
        this.email = email;
        this.items = items;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", items=" + items +
                '}';
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

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

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
        this.items.forEach(item -> item.setInvoice(this));
    }
}
