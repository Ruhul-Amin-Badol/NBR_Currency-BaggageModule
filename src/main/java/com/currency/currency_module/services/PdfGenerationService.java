package com.currency.currency_module.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PdfGenerationService {

    public byte[] generatePdf(List<?> rowData, List<String> includedFields, Double totalPaidAmount) throws IOException {
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

        System.out.println("================================"+labelText );

        // Draw a simple text for the field value with the label
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);  // Adjust X and Y coordinates

        contentStream.showText(labelText);
        contentStream.endText();

        // Adjust the Y-coordinate with additional line spacing
        return y - rowHeight - lineSpacing;
    }

}