package com.currency.currency_module.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PdfGenerationService {

    public byte[] generatePdf(List<?> rowData, List<String> includedFields, Double totalPaidAmount,String sessionToken, String status,Long id) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 20f;

                float xPosition = margin;

                // Load image from resources/static/image
                File imageFile = ResourceUtils.getFile("classpath:static/img/logo/nbr.png");
                PDImageXObject image = PDImageXObject.createFromFileByContent(imageFile, document);

                // Set the position and size of the image
                float xImage = 200;
                float yImage = 700;
                float widthImage = 200;  // Adjust this value based on your image size
                float heightImage = 70;  // Adjust this value based on your image size

                // Draw the image on the page
                contentStream.drawImage(image, xImage, yImage, widthImage, heightImage);

                // Adjust the Y-coordinate after adding the image
                yPosition -= heightImage;

                // Display header text
                String headerText = "National Board Of Revenue, Bangladesh";
                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(headerText);
                contentStream.endText();
                yPosition -= rowHeight; // Adjust the Y-coordinate

                // Display total paid amount
                if (totalPaidAmount != null) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText("Total Paid Amount: " + totalPaidAmount);
                    contentStream.endText();
                    yPosition -= rowHeight; // Adjust the Y-coordinate
                }

                // Generate QR code
                String qrCodeData = "http://172.24.79.144:8080/baggagestart/confrimPage?id="+id+"&session_token="+sessionToken+"&status=success";
                ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);

                // Set the position and size of the QR code image
                float xQRCode = 250;
                float yQRCode = 200;
                float widthQRCode = 100;  // Adjust this value based on your QR code image size
                float heightQRCode = 100;  // Adjust this value based on your QR code image size

                // Draw the QR code on the page
                contentStream.drawImage(PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code"), xQRCode, yQRCode, widthQRCode, heightQRCode);

                // Adjust the Y-coordinate after adding the QR code
                yPosition -= heightQRCode;

                for (Object row : rowData) {
                    if (row instanceof Map) {
                        Map<String, Object> mapRow = (Map<String, Object>) row;

                        for (String fieldName : includedFields) {
                            if (mapRow.containsKey(fieldName)) {
                                Object value = mapRow.get(fieldName);
                                yPosition = drawField(contentStream, xPosition, yPosition, rowHeight, fieldName, value.toString());
                            } else {
                                System.err.println("Field not found: " + fieldName);
                            }
                        }

                        // Reset x-coordinate for the next row
                        xPosition = margin;
                    }
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new EmailServiceException("Failed to generate PDF", e);
        }
    }

    private ByteArrayOutputStream generateQRCode(String data) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200); // Adjust size as needed
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        return out;
    }

    private float drawField(PDPageContentStream contentStream, float x, float y, float rowHeight, String fieldName, String value) throws IOException {
        float lineSpacing = 5f;  // Adjust this value for the desired spacing between lines
        String label = "Unknown Label";

        switch (fieldName) {
            case "passenger_name":
                label = "Name of the Passenger";
                break;

            case "entry_point":
                label = "Place of Issue";
                break;
            case "flight_no":
                label = "Flight No";
                break;
            case "passport_number":
                label = "Passport Number";
                break;

            case "field3":
                label = "Total Paid";
                break;
        }
        label = (label != null) ? label : "Unknown Label";
        // Concatenate the label and value
        String labelText = label + ": " + value;

        System.out.println("================================" + labelText);

        // Draw a simple text for the field value with the label
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);  // Adjust X and Y coordinates
        contentStream.showText(labelText);
        contentStream.endText();

        // Adjust the Y-coordinate with additional line spacing
        return y - rowHeight - lineSpacing;
    }
}
