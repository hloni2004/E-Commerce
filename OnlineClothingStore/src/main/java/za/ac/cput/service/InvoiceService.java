package za.ac.cput.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {

    public byte[] generateInvoicePdf(Order order) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Header
            document.add(new Paragraph("STYLEHUB")
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("INVOICE")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Order Details
            document.add(new Paragraph("Order Number: " + order.getOrderId()).setBold());
            document.add(new Paragraph("Order Date: " + 
                    order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"))));
            document.add(new Paragraph("Status: " + order.getStatus()));
            document.add(new Paragraph("\n"));

            // Customer Details
            document.add(new Paragraph("Customer Information").setBold());
            document.add(new Paragraph("Name: " + order.getUser().getUsername()));
            document.add(new Paragraph("Email: " + order.getUser().getEmail()));
            document.add(new Paragraph("Phone: " + (order.getUser().getPhoneNumber() != null ? 
                    order.getUser().getPhoneNumber() : "N/A")));
            document.add(new Paragraph("\n"));

            // Shipping Address
            if (order.getShippingAddress() != null) {
                document.add(new Paragraph("Shipping Address").setBold());
                document.add(new Paragraph(order.getShippingAddress().getStreet()));
                document.add(new Paragraph(order.getShippingAddress().getCity() + ", " + 
                        order.getShippingAddress().getProvince() + " " + 
                        order.getShippingAddress().getPostalCode()));
                document.add(new Paragraph(order.getShippingAddress().getCountry()));
                document.add(new Paragraph("\n"));
            }

            // Order Items Table
            document.add(new Paragraph("Order Items").setBold());
            float[] columnWidths = {4, 1, 2, 2};
            Table table = new Table(columnWidths);
            table.setWidth(500);

            // Table Headers
            table.addHeaderCell(new Cell().add(new Paragraph("Product").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Qty").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Price").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Total").setBold()));

            // Table Data
            if (order.getItems() != null) {
                for (OrderItem item : order.getItems()) {
                    table.addCell(item.getProduct().getName());
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell("R" + String.format("%.2f", item.getPrice()));
                    table.addCell("R" + String.format("%.2f", item.getPrice() * item.getQuantity()));
                }
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Total
            document.add(new Paragraph("Total Amount: R" + String.format("%.2f", order.getTotalPrice()))
                    .setFontSize(14)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT));

            // Footer
            document.add(new Paragraph("\n\nThank you for shopping with StyleHub!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
            return new byte[0];
        }
    }
}
