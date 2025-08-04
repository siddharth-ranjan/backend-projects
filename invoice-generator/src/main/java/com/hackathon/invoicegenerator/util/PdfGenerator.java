package com.hackathon.invoicegenerator.util;

import com.hackathon.invoicegenerator.entity.Invoice;
import com.hackathon.invoicegenerator.entity.InvoiceItem;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

@Service
public class PdfGenerator {

    public static File generateInvoicePdf(Invoice invoice) throws Exception {
        String filename = "/tmp/invoice_" + invoice.getInvoiceId() + ".pdf";
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        String today = dateFormat.format(new Date());

        // Header
        Paragraph title = new Paragraph("INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // User details
        document.add(new Paragraph("Invoice ID: " + invoice.getInvoiceId(), normalFont));
        document.add(new Paragraph("Username: " + invoice.getUsername(), normalFont));
        document.add(new Paragraph("Email: " + invoice.getEmail(), normalFont));
        document.add(new Paragraph("Date: " + today, normalFont));
        document.add(Chunk.NEWLINE);

        // Table for invoice items
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 1, 2, 2, 2});

        Stream.of("Item", "Qty", "Unit Price", "Discount", "Total").forEach(header -> {
            PdfPCell cell = new PdfPCell(new Phrase(header, labelFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell);
        });

        double grandTotal = 0.0;

        for (InvoiceItem item : invoice.getItems()) {
            double itemTotal = item.getQuantity() * item.getUnitPrice() - item.getDiscount();
            grandTotal += itemTotal;

            table.addCell(new Phrase(item.getItem(), normalFont));
            table.addCell(new Phrase(String.valueOf(item.getQuantity()), normalFont));
            table.addCell(new Phrase(currencyFormat.format(item.getUnitPrice()), normalFont));
            table.addCell(new Phrase(currencyFormat.format(item.getDiscount()), normalFont));
            table.addCell(new Phrase(currencyFormat.format(itemTotal), normalFont));
        }

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Grand total
        Paragraph total = new Paragraph("Grand Total: " + currencyFormat.format(grandTotal), labelFont);
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.add(Chunk.NEWLINE);

        // Footer quote
        Paragraph quote = new Paragraph("Note: All prices include applicable taxes.", footerFont);
        quote.setAlignment(Element.ALIGN_CENTER);
        document.add(quote);

        document.close();
        return new File(filename);
    }

}