package com.currency.currency_module.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;

import com.currency.currency_module.model.BaggageCurrencyAdd;
import com.currency.currency_module.model.CurrencyDeclaration;
import com.currency.currency_module.model.UserActivityManagement;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.cloud.storage.Blob;
import com.google.firebase.FirebaseOptions;
import java.security.Principal;
import java.text.SimpleDateFormat;

import java.sql.Timestamp;
import java.util.Date;

import com.google.firebase.cloud.StorageClient;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


@Service
public class PdfGenerationService {


    @Autowired
   private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserActivityManagementService userActivityManagementService;
    

//payment by admin 
public byte[] generatePdfPayByAdmin(List<Map<String, Object>>allProductQuery,List<?> rowData, List<String> includedFields, Double totalPaidAmount,Double paidAmount,Long id, String passangerName,String applicationSubmitDate,String paymentId) throws IOException {

    byte[] logo  = firebaselogo("nbr_logo.png");
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

            float margin = 40;
            float yStart = page.getMediaBox().getHeight() - margin;
            System.out.println(page.getMediaBox().getHeight());
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 13f;

            float xPosition = margin;

        PDImageXObject logo1 = PDImageXObject.createFromByteArray(document, logo, "Firebase logo");

            // Set the position and size of the image
            float xImage = 200;
            float yImage = 700;
            float widthImage = 200;  // Adjust this value based on your image size
            float heightImage = 70;  // Adjust this value based on your image size

            // Draw the image on the page
            contentStream.drawImage(logo1, xImage, yImage, widthImage, heightImage);

            float xTable = 100;
            float yTable = page.getMediaBox().getHeight()-7*margin-100; 
            // Adjust the Y-coordinate for the start of the table
            float tableWidth1 = page.getMediaBox().getWidth() - 2 * margin;
            float tableHeight = 20f;
            
            float red = 220 / 255f;
            float green = 76 / 255f;
            float blue = 100 / 255f;
            // Draw table header
            contentStream.setLineWidth(1f);
            contentStream.setNonStrokingColor(red,green,blue); 
            contentStream.addRect(xTable-39, yTable-7, 500,22);
            contentStream.fill();
            contentStream.setNonStrokingColor(0,0,0); 
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10); // Adjust font and size if needed
            contentStream.beginText();
            contentStream.newLineAtOffset(xTable, yTable);
            contentStream.showText("Product Name");
            contentStream.newLineAtOffset(130, 0);
            contentStream.showText("Unit");
            contentStream.newLineAtOffset(86, 0);
            contentStream.showText("Quantity");
            contentStream.newLineAtOffset(86, 0);
            contentStream.showText("Value");
            contentStream.newLineAtOffset(86, 0);
            contentStream.showText("Tax Amount");
            contentStream.endText();
            yTable -= 20; // Adjust the Y-coordinate for the table content
            contentStream.setNonStrokingColor(0,0,0);



            for (Map<String, Object> row : allProductQuery) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // Adjust font size if needed
            
                // Draw each column in the row
                contentStream.beginText();
                contentStream.newLineAtOffset(xTable-5, yTable);
                contentStream.showText(row.get("item_name").toString());
                contentStream.newLineAtOffset(140, 0);
                contentStream.showText(row.get("unit_name").toString());
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText(row.get("qty").toString());
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText(row.get("value").toString());
                contentStream.newLineAtOffset(90, 0);
                contentStream.showText(row.get("tax_amount").toString());
                contentStream.endText();
            
                yTable -= tableHeight; // Adjust the Y-coordinate for the next row
            }
            




            // Generate QR code


            // Set the position and size of the QR code image
            float xQRCode = 240;
            float yQRCode = 50;
            float widthQRCode = 150;  // Adjust this value based on your QR code image size
            float heightQRCode = 150;  // Adjust this value based on your QR code image size

            // Draw the QR code on the page
            String qrCodeData = "http://13.232.110.60:8080/baggagestart/confrimPage?id="+id;
            ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);
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
                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText("Payment id: " + paymentId);
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
            
            if (paidAmount < 0){
                double creditAmount = Math.abs(paidAmount);
                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText("Refund amount is: " + creditAmount);
                contentStream.endText();
                yPosition -= rowHeight; // Adjust the Y-coordinate
              }else{
                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText("Payable amount : " + paidAmount);
                contentStream.endText();
                yPosition -= rowHeight; // Adjust the Y-coordinate

             }
        float xusersign= 450;
        float yusersign = 160;
        float widthUserSign= 90;  // Adjust this value based on your image size
        float heightUserSign = 90; 

       String userSignNameText = passangerName;
        contentStream.beginText();
        contentStream.newLineAtOffset(xusersign, yusersign);
        contentStream.showText(userSignNameText);
        contentStream.endText();
        widthUserSign = heightUserSign; 






        float xuser= 450;
        float yuser = 140;
        float widthUser= 90;  // Adjust this value based on your image size
        float heightUser = 90; 

       String userNameText = "[Signature of Declarant]";

        contentStream.setLineWidth(2.5f);  
        contentStream.moveTo(xuser, yuser + 13);  
        contentStream.lineTo(xuser + widthUser, yuser + 13); 
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(xuser, yuser);
        contentStream.showText(userNameText);
        contentStream.endText();
        widthUser = heightUser; 

        float xSubmitDate=450;
        float ySubmitDate = 120;
        float widthSubmitDate= 90;  // Adjust this value based on your image size
        float heightSubmitDate = 90; 

        contentStream.beginText();
        contentStream.newLineAtOffset(xSubmitDate, ySubmitDate);
        contentStream.showText("Submit date: "+applicationSubmitDate);
        contentStream.endText();
        widthSubmitDate = heightSubmitDate; 


        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        return baos.toByteArray();
    } catch (Exception e) {
        throw new EmailServiceException("Failed to generate PDF", e);
    }
}



//for baggage approve 
public byte[] generatePdf(List<Map<String, Object>>allProductQuery,List<?> rowData, List<String> includedFields, Double totalPaidAmount,Integer id,Principal principal,String passangerName,String approveDate,String applicationSubmitDate,String paymentId) throws IOException {
    byte[] imageData = firebaseImage(principal);
    byte[] logo  = firebaselogo("nbr_logo.png");

    
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 13f;

            float xPosition = margin;

            // Load image from resources/static/image
            try {
        PDImageXObject logo1 = PDImageXObject.createFromByteArray(document, logo, "Firebase logo");
            
           
            // Set the position and size of the image
            float xImage = 200;
            float yImage = 700;
            float widthImage = 200;  // Adjust this value based on your image size
            float heightImage = 70;  // Adjust this value based on your image size

            // Draw the image on the page
            contentStream.drawImage(logo1, xImage, yImage, widthImage, heightImage);
       
            // Adjust the Y-coordinate after adding the image
            yPosition -= heightImage;

            // Display header text
            String headerText = "congratulation !! your baggage application has been approved";
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(headerText);
            contentStream.endText();
            yPosition -= rowHeight; // Adjust the Y-coordinate



             PDImageXObject signature = PDImageXObject.createFromByteArray(document, imageData, "Firebase Image");

            float xSignature= 60;
            float ySignature = 100;
            float widthSignature= 100;  // Adjust this value based on your image size
            float heightSignature = 100;  // Adjust this value based on your image size

            // Draw the image on the page
            contentStream.drawImage(signature, xSignature, ySignature, widthSignature, heightSignature);

            float xadmin= 60;
            float yadmin = 90;
            float widthAdmin= 90;  // Adjust this value based on your image size
            float heightAdmin = 90; 

           String adminNameText = principal.getName();
            contentStream.beginText();
            contentStream.newLineAtOffset(xadmin, yadmin);
            contentStream.showText(adminNameText);
            contentStream.endText();
            widthAdmin = heightAdmin; 

            float xSign= 60;
            float ySign = 70;
            float widthSign= 80;  // Adjust this value based on your image size
            float heightSign = 70; 

           String signatureText = "[Signature of Customs Official]";
            contentStream.setLineWidth(2.5f);  
            contentStream.moveTo(xSign, ySign + 13);  
            contentStream.lineTo(xSign + widthSign, ySign + 13); 
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(xSign, ySign);
            contentStream.showText(signatureText);
            contentStream.endText();
            widthSign = heightSign; 
           
            float xusersign= 450;
            float yusersign = 90;
            float widthUserSign= 90;  // Adjust this value based on your image size
            float heightUserSign = 90; 

           String userSignNameText = passangerName;
            contentStream.beginText();
            contentStream.newLineAtOffset(xusersign, yusersign);
            contentStream.showText(userSignNameText);
            contentStream.endText();
            widthUserSign = heightUserSign; 


            float xApproveDate=60;
            float yApproveDate = 40;
            float widthApproveDate= 90;  // Adjust this value based on your image size
            float heightApproveDate = 90; 

            contentStream.beginText();
            contentStream.newLineAtOffset(xApproveDate, yApproveDate);
            contentStream.showText("Approve date: "+approveDate);
            contentStream.endText();
            widthApproveDate = heightApproveDate; 










            float xuser= 450;
            float yuser = 70;
            float widthUser= 90;  // Adjust this value based on your image size
            float heightUser = 90; 

           String userNameText = "[Signature of Declarant]";

            contentStream.setLineWidth(2.5f);  
            contentStream.moveTo(xuser, yuser + 13);  
            contentStream.lineTo(xuser + widthUser, yuser + 13); 
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(xuser, yuser);
            contentStream.showText(userNameText);
            contentStream.endText();
            widthUser = heightUser; 


            float xSubmitDate=450;
            float ySubmitDate = 40;
            float widthSubmitDate= 90;  // Adjust this value based on your image size
            float heightSubmitDate = 90; 

            contentStream.beginText();
            contentStream.newLineAtOffset(xSubmitDate, ySubmitDate);
            contentStream.showText("Submit date: "+applicationSubmitDate);
            contentStream.endText();
            widthSubmitDate = heightSubmitDate; 

                float xTable = 100;
                float yTable = page.getMediaBox().getHeight()-7*margin-100; 
                // Adjust the Y-coordinate for the start of the table
                float tableWidth1 = page.getMediaBox().getWidth() - 2 * margin;
                float tableHeight = 20f;
                
                float red = 220 / 255f;
                float green = 76 / 255f;
                float blue = 100 / 255f;
                // Draw table header
                contentStream.setLineWidth(1f);
                contentStream.setNonStrokingColor(red,green,blue); 
                contentStream.addRect(xTable-39, yTable-7, 500,22);
                contentStream.fill();
                contentStream.setNonStrokingColor(0,0,0); 
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10); // Adjust font and size if needed
                contentStream.beginText();
                contentStream.newLineAtOffset(xTable, yTable);
                contentStream.showText("Product Name");
                contentStream.newLineAtOffset(130, 0);
                contentStream.showText("Unit");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Quantity");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Value");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Tax Amount");
                contentStream.endText();
                yTable -= 20; // Adjust the Y-coordinate for the table content
                contentStream.setNonStrokingColor(0,0,0);



                for (Map<String, Object> row : allProductQuery) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // Adjust font size if needed
                
                    // Draw each column in the row
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xTable-5, yTable);
                    contentStream.showText(row.get("item_name").toString());
                    contentStream.newLineAtOffset(140, 0);
                    contentStream.showText(row.get("unit_name").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("qty").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("value").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("tax_amount").toString());
                    contentStream.endText();
                
                    yTable -= tableHeight; // Adjust the Y-coordinate for the next row
                }
                



            // Set the position and size of the QR code image
            float xQRCode = 250;
            float yQRCode = 50;
            float widthQRCode = 100;  // Adjust this value based on your QR code image size
            float heightQRCode = 100;  // Adjust this value based on your QR code image size

            // Draw the QR code on the page
            String qrCodeData = "http://13.232.110.60:8080/baggagestart/confrimPage?id="+id;
            ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);
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

            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText("Payment id: " + paymentId);
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

            
            }catch (IOException e) {
                // Handle the exception
            }
            
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        return baos.toByteArray();
    } catch (Exception e) {
        throw new EmailServiceException("Failed to generate PDF", e);
    }
}








public byte[] firebaseImage(Principal principal) throws IOException{
 UserActivityManagement user=userActivityManagementService.findUserWithUserName(principal.getName());

//    var storage = StorageClient.getInstance().bucket();
                
//                 String usernameSession = principal.getName();
//             // Fetch image data from Firebase Storage
//             String imageName = "signatures/"+usernameSession; // Adjust this to the actual image name
//             Blob blob = storage.get(imageName);

//             // Check if the blob (image) exists
//             if (blob == null) {
//                 throw new RuntimeException("Image not found in Firebase Storage: " + imageName);
//             }

//             byte[] imageData = blob.getContent();
//             return imageData;
  
 String imageUrl =user.getSignature(); // Replace with the actual URL
    return getImageFromURL(imageUrl);
}

public byte[] firebaselogo(String name){
         var storage = StorageClient.getInstance().bucket();
                
                String usernameSession = name;
            // Fetch image data from Firebase Storage
            String imageName = "nbrassests/"+usernameSession; // Adjust this to the actual image name
            Blob blob = storage.get(imageName);

            // Check if the blob (image) exists
            if (blob == null) {
                throw new RuntimeException("Image not found in Firebase Storage: " + imageName);
            }

            byte[] imageData = blob.getContent();
            return imageData;
}

public byte[] getImageFromURL(String imageUrl) throws IOException {
    URL url = new URL(imageUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    // Check if the request was successful (status code 200)
    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException("Failed to fetch image from URL: " + imageUrl
                + ", HTTP error code: " + connection.getResponseCode());
    }

    // Read the image data from the input stream
    try (InputStream inputStream = connection.getInputStream()) {
        byte[] imageData = inputStream.readAllBytes();
        return imageData;
    } finally {
        connection.disconnect();
    }
}



public byte[] firebaseImageSignature(String usernameSession) throws IOException{
    UserActivityManagement user=userActivityManagementService.findUserWithUserName(usernameSession);
//    var storage = StorageClient.getInstance().bucket();
                
              
//             // Fetch image data from Firebase Storage
//             String imageName = "signatures/"+usernameSession; // Adjust this to the actual image name
//             Blob blob = storage.get(imageName);

//             // Check if the blob (image) exists
//             if (blob == null) {
//                 throw new RuntimeException("Image not found in Firebase Storage: " + imageName);
//             }

//             byte[] imageData = blob.getContent();
//             return imageData;
 String imageUrl =user.getSignature(); // Replace with the actual URL
    return getImageFromURL(imageUrl);
}
//payment not at this time
    public byte[] generatePdfPaymentNotAtThisTime(List<Map<String, Object>>allProductQuery,List<?> rowData, List<String> includedFields, Double totalPaidAmount,Long id,String applicationSubmitDate,String passangerName,String paymentId) throws IOException {

        byte[] logo  = firebaselogo("nbr_logo.png");
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

                float margin = 40;
                float yStart = page.getMediaBox().getHeight() - margin;
                System.out.println(page.getMediaBox().getHeight());
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 13f;

                float xPosition = margin;

                // Load image from resources/static/image
                // File imageFile = ResourceUtils.getFile("classpath:static/img/logo/nbr.png");
                // PDImageXObject image = PDImageXObject.createFromFileByContent(imageFile, document);
            PDImageXObject logo1 = PDImageXObject.createFromByteArray(document, logo, "Firebase logo");

                // Set the position and size of the image
                float xImage = 200;
                float yImage = 700;
                float widthImage = 200;  // Adjust this value based on your image size
                float heightImage = 70;  // Adjust this value based on your image size

                // Draw the image on the page
                contentStream.drawImage(logo1, xImage, yImage, widthImage, heightImage);

                // Adjust the Y-coordinate after adding the image
                // yPosition -= heightImage;


                float xTable = 100;
                float yTable = page.getMediaBox().getHeight()-7*margin-100; 
                // Adjust the Y-coordinate for the start of the table
                float tableWidth1 = page.getMediaBox().getWidth() - 2 * margin;
                float tableHeight = 20f;
                
                float red = 220 / 255f;
                float green = 76 / 255f;
                float blue = 100 / 255f;
                // Draw table header
                contentStream.setLineWidth(1f);
                contentStream.setNonStrokingColor(red,green,blue); 
                contentStream.addRect(xTable-39, yTable-7, 500,22);
                contentStream.fill();
                contentStream.setNonStrokingColor(0,0,0); 
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10); // Adjust font and size if needed
                contentStream.beginText();
                contentStream.newLineAtOffset(xTable, yTable);
                contentStream.showText("Product Name");
                contentStream.newLineAtOffset(130, 0);
                contentStream.showText("Unit");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Quantity");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Value");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Tax Amount");
                contentStream.endText();
                yTable -= 20; // Adjust the Y-coordinate for the table content
                contentStream.setNonStrokingColor(0,0,0);



                for (Map<String, Object> row : allProductQuery) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // Adjust font size if needed
                
                    // Draw each column in the row
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xTable-5, yTable);
                    contentStream.showText(row.get("item_name").toString());
                    contentStream.newLineAtOffset(140, 0);
                    contentStream.showText(row.get("unit_name").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("qty").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("value").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("tax_amount").toString());
                    contentStream.endText();
                
                    yTable -= tableHeight; // Adjust the Y-coordinate for the next row
                }
                

            float xusersign= 450;
            float yusersign = 160;
            float widthUserSign= 90;  // Adjust this value based on your image size
            float heightUserSign = 90; 

           String userSignNameText = passangerName;
            contentStream.beginText();
            contentStream.newLineAtOffset(xusersign, yusersign);
            contentStream.showText(userSignNameText);
            contentStream.endText();
            widthUserSign = heightUserSign; 






            float xuser= 450;
            float yuser = 140;
            float widthUser= 90;  // Adjust this value based on your image size
            float heightUser = 90; 

           String userNameText = "[Signature of Declarant]";

            contentStream.setLineWidth(2.5f);  
            contentStream.moveTo(xuser, yuser + 13);  
            contentStream.lineTo(xuser + widthUser, yuser + 13); 
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(xuser, yuser);
            contentStream.showText(userNameText);
            contentStream.endText();
            widthUser = heightUser; 

            float xSubmitDate=450;
            float ySubmitDate = 120;
            float widthSubmitDate= 90;  // Adjust this value based on your image size
            float heightSubmitDate = 90; 

            contentStream.beginText();
            contentStream.newLineAtOffset(xSubmitDate, ySubmitDate);
            contentStream.showText("Submit date: "+applicationSubmitDate);
            contentStream.endText();
            widthSubmitDate = heightSubmitDate; 

                // Generate QR code
                // Set the position and size of the QR code image
                float xQRCode = 240;
                float yQRCode = 50;
                float widthQRCode = 150;  // Adjust this value based on your QR code image size
                float heightQRCode = 150;  // Adjust this value based on your QR code image size

                // Draw the QR code on the page
                String qrCodeData = "http://13.232.110.60:8080/baggagestart/confrimPage?id="+id;
                ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);
                contentStream.drawImage(PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code"), xQRCode, yQRCode, widthQRCode, heightQRCode);

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
                // Display total paid amount
                
                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText("Payment id: " + paymentId);
                contentStream.endText();
                yPosition -= rowHeight; // Adjust the Y-coordinate
                
                
                if (totalPaidAmount != null) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText("Total Due Amount: " + totalPaidAmount);
                    contentStream.endText();
                    yPosition -= rowHeight; // Adjust the Y-coordinate
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new EmailServiceException("Failed to generate PDF", e);
        }
    } 


    public byte[] generatePdf(List<Map<String, Object>>allProductQuery,List<?> rowData, List<String> includedFields, Double totalPaidAmount,Long id,String applicationSubmitDate,String passangerName,String paymentId) throws IOException {

        byte[] logo  = firebaselogo("nbr_logo.png");
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

                float margin = 40;
                float yStart = page.getMediaBox().getHeight() - margin;
                System.out.println(page.getMediaBox().getHeight());
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 13f;

                float xPosition = margin;

                // Load image from resources/static/image
                // File imageFile = ResourceUtils.getFile("classpath:static/img/logo/nbr.png");
                // PDImageXObject image = PDImageXObject.createFromFileByContent(imageFile, document);
            PDImageXObject logo1 = PDImageXObject.createFromByteArray(document, logo, "Firebase logo");

                // Set the position and size of the image
                float xImage = 200;
                float yImage = 700;
                float widthImage = 200;  // Adjust this value based on your image size
                float heightImage = 70;  // Adjust this value based on your image size

                // Draw the image on the page
                contentStream.drawImage(logo1, xImage, yImage, widthImage, heightImage);

                // Adjust the Y-coordinate after adding the image
                // yPosition -= heightImage;


                float xTable = 100;
                float yTable = page.getMediaBox().getHeight()-7*margin-100; 
                // Adjust the Y-coordinate for the start of the table
                float tableWidth1 = page.getMediaBox().getWidth() - 2 * margin;
                float tableHeight = 20f;
                
                float red = 220 / 255f;
                float green = 76 / 255f;
                float blue = 100 / 255f;
                // Draw table header
                contentStream.setLineWidth(1f);
                contentStream.setNonStrokingColor(red,green,blue); 
                contentStream.addRect(xTable-39, yTable-7, 500,22);
                contentStream.fill();
                contentStream.setNonStrokingColor(0,0,0); 
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10); // Adjust font and size if needed
                contentStream.beginText();
                contentStream.newLineAtOffset(xTable, yTable);
                contentStream.showText("Product Name");
                contentStream.newLineAtOffset(130, 0);
                contentStream.showText("Unit");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Quantity");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Value");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Tax Amount");
                contentStream.endText();
                yTable -= 20; // Adjust the Y-coordinate for the table content
                contentStream.setNonStrokingColor(0,0,0);



                for (Map<String, Object> row : allProductQuery) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // Adjust font size if needed
                
                    // Draw each column in the row
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xTable-5, yTable);
                    contentStream.showText(row.get("item_name").toString());
                    contentStream.newLineAtOffset(140, 0);
                    contentStream.showText(row.get("unit_name").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("qty").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("value").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("tax_amount").toString());
                    contentStream.endText();
                
                    yTable -= tableHeight; // Adjust the Y-coordinate for the next row
                }
                



            float xusersign= 450;
            float yusersign = 160;
            float widthUserSign= 90;  // Adjust this value based on your image size
            float heightUserSign = 90; 

           String userSignNameText = passangerName;
            contentStream.beginText();
            contentStream.newLineAtOffset(xusersign, yusersign);
            contentStream.showText(userSignNameText);
            contentStream.endText();
            widthUserSign = heightUserSign; 

            float xuser= 450;
            float yuser = 140;
            float widthUser= 90;  // Adjust this value based on your image size
            float heightUser = 90; 

           String userNameText = "[Signature of Declarant]";

            contentStream.setLineWidth(2.5f);  
            contentStream.moveTo(xuser, yuser + 13);  
            contentStream.lineTo(xuser + widthUser, yuser + 13); 
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(xuser, yuser);
            contentStream.showText(userNameText);
            contentStream.endText();
            widthUser = heightUser; 

            float xSubmitDate=450;
            float ySubmitDate = 120;
            float widthSubmitDate= 90;  // Adjust this value based on your image size
            float heightSubmitDate = 90; 

            contentStream.beginText();
            contentStream.newLineAtOffset(xSubmitDate, ySubmitDate);
            contentStream.showText("Submit date: "+applicationSubmitDate);
            contentStream.endText();
            widthSubmitDate = heightSubmitDate; 

                // Generate QR code


                // Set the position and size of the QR code image
                float xQRCode = 240;
                float yQRCode = 50;
                float widthQRCode = 150;  // Adjust this value based on your QR code image size
                float heightQRCode = 150;  // Adjust this value based on your QR code image size

                // Draw the QR code on the page
                String qrCodeData = "http://13.232.110.60:8080/baggagestart/confrimPage?id="+id;
                ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);
                contentStream.drawImage(PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code"), xQRCode, yQRCode, widthQRCode, heightQRCode);

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


                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText("Payment id: " + paymentId);
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


            case "dateofarrival":
                label = "Date of Arrival";
                break;
            
            case "previous_country":
                label = "Country from where coming";
                break;

            case "email":
                label = "Email";
                break;


            case "mobile_no":
                label = "Phone";
                break;
                
            case "field3":
                label = "Total Paid";
                break;
        }
        label = (label != null) ? label : "Unknown Label";
        // Concatenate the label and value
        String labelText = label + ": " + value;



        // Draw a simple text for the field value with the label
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        System.out.println(x);
        System.out.println(y);  // Adjust X and Y coordinates
        contentStream.showText(labelText);
        contentStream.endText();

        // Adjust the Y-coordinate with additional line spacing
        return y - rowHeight - lineSpacing;
    }

//Bank Auto Request Controller
    public byte[] generatePdf(List<Map<String, Object>>allProductQuery,List<?> rowData, List<String> includedFields, Double totalPaidAmount,Long id) throws IOException {

        byte[] logo  = firebaselogo("nbr_logo.png");
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

                float margin = 40;
                float yStart = page.getMediaBox().getHeight() - margin;
                System.out.println(page.getMediaBox().getHeight());
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float rowHeight = 13f;

                float xPosition = margin;

                // Load image from resources/static/image
                // File imageFile = ResourceUtils.getFile("classpath:static/img/logo/nbr.png");
                // PDImageXObject image = PDImageXObject.createFromFileByContent(imageFile, document);
            PDImageXObject logo1 = PDImageXObject.createFromByteArray(document, logo, "Firebase logo");

                // Set the position and size of the image
                float xImage = 200;
                float yImage = 700;
                float widthImage = 200;  // Adjust this value based on your image size
                float heightImage = 70;  // Adjust this value based on your image size

                // Draw the image on the page
                contentStream.drawImage(logo1, xImage, yImage, widthImage, heightImage);

                // Adjust the Y-coordinate after adding the image
                // yPosition -= heightImage;


                float xTable = 100;
                float yTable = page.getMediaBox().getHeight()-7*margin-100; 
                // Adjust the Y-coordinate for the start of the table
                float tableWidth1 = page.getMediaBox().getWidth() - 2 * margin;
                float tableHeight = 20f;
                
                float red = 220 / 255f;
                float green = 76 / 255f;
                float blue = 100 / 255f;
                // Draw table header
                contentStream.setLineWidth(1f);
                contentStream.setNonStrokingColor(red,green,blue); 
                contentStream.addRect(xTable-39, yTable-7, 500,22);
                contentStream.fill();
                contentStream.setNonStrokingColor(0,0,0); 
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10); // Adjust font and size if needed
                contentStream.beginText();
                contentStream.newLineAtOffset(xTable, yTable);
                contentStream.showText("Product Name");
                contentStream.newLineAtOffset(130, 0);
                contentStream.showText("Unit");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Quantity");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Value");
                contentStream.newLineAtOffset(86, 0);
                contentStream.showText("Tax Amount");
                contentStream.endText();
                yTable -= 20; // Adjust the Y-coordinate for the table content
                contentStream.setNonStrokingColor(0,0,0);



                for (Map<String, Object> row : allProductQuery) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // Adjust font size if needed
                
                    // Draw each column in the row
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xTable-5, yTable);
                    contentStream.showText(row.get("item_name").toString());
                    contentStream.newLineAtOffset(140, 0);
                    contentStream.showText(row.get("unit_name").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("qty").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("value").toString());
                    contentStream.newLineAtOffset(90, 0);
                    contentStream.showText(row.get("tax_amount").toString());
                    contentStream.endText();
                
                    yTable -= tableHeight; // Adjust the Y-coordinate for the next row
                }
                




                // Generate QR code


                // Set the position and size of the QR code image
                float xQRCode = 240;
                float yQRCode = 50;
                float widthQRCode = 150;  // Adjust this value based on your QR code image size
                float heightQRCode = 150;  // Adjust this value based on your QR code image size

                // Draw the QR code on the page
                String qrCodeData = "http://13.232.110.60:8080/baggagestart/confrimPage?id="+id;
                ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);
                contentStream.drawImage(PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code"), xQRCode, yQRCode, widthQRCode, heightQRCode);

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



                // Display total paid amount
                if (totalPaidAmount != null) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPosition, yPosition);
                    contentStream.showText("Total Paid Amount: " + totalPaidAmount);
                    contentStream.endText();
                    yPosition -= rowHeight; // Adjust the Y-coordinate
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new EmailServiceException("Failed to generate PDF", e);
        }
    } 

   



public byte[] generatePdfCurrency(CurrencyDeclaration currencyDeclaration, List<BaggageCurrencyAdd> baggageCurrencyAdd,String username) throws IOException {
    byte[] imageData = firebaseImageSignature(username);
    byte[] logo  = firebaselogo("nbr_logo.png");

    
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;


            try {

          PDImageXObject logo1 = PDImageXObject.createFromByteArray(document, logo, "Firebase logo");
            
           
            // Set the position and size of the image
            float xImage = 200;
            float yImage = 700;
            float widthImage = 200;  // Adjust this value based on your image size
            float heightImage = 70;  // Adjust this value based on your image size

            // Draw the image on the page
            contentStream.drawImage(logo1, xImage, yImage, widthImage, heightImage);
       

            String headerText = "congratulation !! your Currency application has been approved";
            contentStream.beginText();
            contentStream.newLineAtOffset(60, 670);
            contentStream.showText(headerText);
            contentStream.endText();

;
            contentStream.beginText();
          
            contentStream.newLineAtOffset(60, 640);         
            contentStream.showText("Name of the Passenger : "+currencyDeclaration.getPassengerName());

            contentStream.endText();


            contentStream.beginText();        
            contentStream.newLineAtOffset(60, 615);         
            contentStream.showText("Passport No : "+currencyDeclaration.getPassportNumber());
            contentStream.endText();


 
            contentStream.beginText();         
            contentStream.newLineAtOffset(60, 590);         
            contentStream.showText("Entry Point : "+currencyDeclaration.getEntryPoint());
            contentStream.endText();

        //     Date date = new Date(currencyDeclaration.getEntryAt().getTime());

        // // Define the desired date format
        // SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

        // // Format the date as a string
        // String formattedDate = dateFormat.format(date);

        //     contentStream.setNonStrokingColor(0.2f,0.2f,0.2f);
        //     contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
        //     contentStream.beginText();         
        //     contentStream.newLineAtOffset(60, 610);         
        //     contentStream.showText("Date:               "+formattedDate);
        //     contentStream.endText();


            // yPosition -= rowHeight; // Adjust the Y-coordinate



        PDImageXObject signature = PDImageXObject.createFromByteArray(document, imageData, "Firebase Image");

            float xSignature= 60;
            float ySignature = 100;
            float widthSignature= 100;  // Adjust this value based on your image size
            float heightSignature = 100;  // Adjust this value based on your image size

            // Draw the image on the page
            contentStream.drawImage(signature, xSignature, ySignature, widthSignature, heightSignature);

            // Generate QR code

            

                float xTable = 135;
                float yTable = page.getMediaBox().getHeight()-4*margin-100; 
                // Adjust the Y-coordinate for the start of the table
                float tableWidth1 = page.getMediaBox().getWidth() - 2 * margin;
                float tableHeight = 20f;
                
                float red = 220 / 255f;
                float green = 76 / 255f;
                float blue = 100 / 255f;
                // Draw table header
                contentStream.setLineWidth(1f);
                contentStream.setNonStrokingColor(red,green,blue); 
                contentStream.addRect(xTable, yTable-7, 400,22);
                contentStream.fill();
                contentStream.setNonStrokingColor(0,0,0); 
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10); // Adjust font and size if needed
                contentStream.beginText();
                contentStream.newLineAtOffset(145, yTable);
                contentStream.showText("Description Of Currency");
                contentStream.newLineAtOffset(160, 0);
                contentStream.showText("Note Type");
                contentStream.newLineAtOffset(95, 0);
                contentStream.showText("Number of Note");
                contentStream.newLineAtOffset(95, 0);
                contentStream.showText("Amount");
                contentStream.endText();
                yTable -= 20; // Adjust the Y-coordinate for the table content
                contentStream.setNonStrokingColor(0,0,0);



                for (BaggageCurrencyAdd baggageCurrencyAddeach : baggageCurrencyAdd) {
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10); // Adjust font size if needed
                
                    // Draw each column in the row
                    contentStream.beginText();
                    contentStream.newLineAtOffset(xTable+5, yTable);
                    contentStream.showText(baggageCurrencyAddeach.getCurrencyName());
                    contentStream.newLineAtOffset(165, 0);
                    contentStream.showText(baggageCurrencyAddeach.getCurrencyNoteType());
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(baggageCurrencyAddeach.getNumberOfNote());
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(baggageCurrencyAddeach.getCurrencyAmount());
              
                    contentStream.endText();
                
                    yTable -= tableHeight; // Adjust the Y-coordinate for the next row
                }
                



            // Set the position and size of the QR code image
            float xQRCode = 280;
            float yQRCode = 50;
            float widthQRCode = 100;  // Adjust this value based on your QR code image size
            float heightQRCode = 100;  // Adjust this value based on your QR code image size

            // Draw the QR code on the page
            String qrCodeData = "http://13.232.110.60:8080/currencystart/confirmgenaral?id="+currencyDeclaration.getId();
            ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeData);
            contentStream.drawImage(PDImageXObject.createFromByteArray(document, qrCodeStream.toByteArray(), "QR Code"), xQRCode, yQRCode, widthQRCode, heightQRCode);

            // Adjust the Y-coordinate after adding the QR code
           

              }catch (IOException e) {
                // Handle the exception
            }

            // for (Object row : rowData) {
            //     if (row instanceof Map) {
            //         Map<String, Object> mapRow = (Map<String, Object>) row;

            //         for (String fieldName : includedFields) {
            //             if (mapRow.containsKey(fieldName)) {
            //                 Object value = mapRow.get(fieldName);
            //                 yPosition = drawField(contentStream, xPosition, yPosition, rowHeight, fieldName, value.toString());
            //             } else {
            //                 System.err.println("Field not found: " + fieldName);
            //             }
            //         }

            //         // Reset x-coordinate for the next row
            //         xPosition = margin;
            //     }
            // }
            // // Display total paid amount
            // if (totalPaidAmount != null) {
            //     contentStream.beginText();
            //     contentStream.newLineAtOffset(xPosition, yPosition);
            //     contentStream.showText("Total Paid Amount: " + totalPaidAmount);
            //     contentStream.endText();
            //     yPosition -= rowHeight; // Adjust the Y-coordinate
            // }

            
          
            
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        return baos.toByteArray();
    } catch (Exception e) {
        throw new EmailServiceException("Failed to generate PDF", e);
    }
}






}