package com.currency.currency_module.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGenerationService {

    public byte[] generatePdf() throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setNonStrokingColor(0, 0, 1);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello, this is a sample PDF!");
                contentStream.newLine(); // Move to the next line

                contentStream.setNonStrokingColor(1, 0, 0);
                contentStream.newLineAtOffset(0, -15);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.showText("Hello, Ruhul this is baggage confirmation!");
                contentStream.newLine(); // Move to the next line

                contentStream.endText(); // Don't forget to call endText()
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new EmailServiceException("Failed ", e);
        }
    }

    private PDColor Color(String string) {
        return null;
    }

    public static void main(String[] args) throws IOException {
        PdfGenerationService pdfGenerationService = new PdfGenerationService();
        byte[] pdfData = pdfGenerationService.generatePdf();
        System.out.println("PDF generated successfully.");
    }
}
