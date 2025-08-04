package com.hackathon.invoicegenerator.controller;

import com.hackathon.invoicegenerator.entity.Invoice;
import com.hackathon.invoicegenerator.model.InvoiceRequest;
import com.hackathon.invoicegenerator.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
        System.out.println(request);
        try {
            Invoice invoice = invoiceService.createInvoiceWithPdfAndEmail(request);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            System.out.println("Failed to send email" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get/{invoiceId}")
    public ResponseEntity<?> getInvoice(@PathVariable Long invoiceId) {
        Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(invoiceId);
        if (optionalInvoice.isPresent()) {
            return ResponseEntity.ok(optionalInvoice.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invoice with ID " + invoiceId + " not found.");
        }
    }
}