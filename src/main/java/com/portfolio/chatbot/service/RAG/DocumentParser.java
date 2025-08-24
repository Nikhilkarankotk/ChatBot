package com.portfolio.chatbot.service.RAG;

import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class DocumentParser {
    private final Tika tika = new Tika();

//    @SneakyThrows
//    public String parseDocument(MultipartFile file) throws IOException, TikaException {
//          AutoDetectParser parser = new AutoDetectParser();
//          BodyContentHandler handler = new BodyContentHandler(-1);
//          Metadata metadata = new Metadata();
//          ParseContext context = new ParseContext();
//        context.set(TesseractOCRConfig.class, new TesseractOCRConfig());
//        try(InputStream is = file.getInputStream()){
////            String text = tika.parseToString(is);
//            parser.parse(is, handler, metadata, new ParseContext());
//            String text = handler.toString();
//            System.out.println("Document Metadata: " + Arrays.toString(metadata.names()));
//            System.out.println("Extracted Text: " + text);
//            return text;
//        }
//        catch (Exception e){
//            System.err.println("Parsing Error: "+e.getMessage());
//            throw  e;
//        }
////        return tika.parseToString(file.getInputStream());
//    }
//public String parseDocument(MultipartFile file) throws IOException, TikaException {
//    System.out.println("=== Starting Document Parsing ===");
//    System.out.println("File: " + file.getOriginalFilename() + " (Size: " + file.getSize() + " bytes)");
//    // 1. Configure Parser with OCR Support
//    AutoDetectParser parser = new AutoDetectParser();
//    BodyContentHandler handler = new BodyContentHandler(-1); // -1 = no size limit
//    Metadata metadata = new Metadata();
//    ParseContext context = new ParseContext();
//    // 2. Configure Tesseract OCR
//    TesseractOCRConfig ocrConfig = new TesseractOCRConfig();
//    ocrConfig.setLanguage("eng"); // English
//    context.set(TesseractOCRConfig.class, ocrConfig);
//    // 3. Configure PDF Parsing (if needed)
//    PDFParserConfig pdfConfig = new PDFParserConfig();
//    pdfConfig.setExtractInlineImages(true); // Required for OCR
//    context.set(PDFParserConfig.class, pdfConfig);
//    try (InputStream inputStream = file.getInputStream()) {
//        // 4. Parse the Document
//        parser.parse(inputStream, handler, metadata, context);
//
//        // 5. Get Extracted Text
//        String text = handler.toString();
//        System.out.println("Metadata: " + Arrays.toString(metadata.names()));
//        System.out.println("Extracted Text Length: " + text.length());
//
//        if (text.trim().isEmpty()) {
//            throw new IllegalArgumentException("Parsed text is empty. The document may be image-only.");
//        }
//
//        return text;
//
//    } catch (Exception e) {
//        System.err.println("Parsing Failed: " + e.getMessage());
//        throw e;
//    }
//public String parseDocument(MultipartFile file) throws IOException, TikaException {
//    System.out.println("=== Starting Document Parsing ===");
//    System.out.println("File: " + file.getOriginalFilename() + " (Size: " + file.getSize() + " bytes)");
//    try {
//        // Parse the document using Apache Tika
//        String text = tika.parseToString(file.getInputStream());
//        System.out.println("Extracted Text Length: " + text.length());
//        if (text.trim().isEmpty()) {
//            throw new IllegalArgumentException("Parsed text is empty. The document may be unsupported or corrupted.");
//        }
//        return text;
//    } catch (Exception e) {
//        System.err.println("Parsing Failed: " + e.getMessage());
//        throw e;
//    }
//        public String parseDocument(MultipartFile file) throws IOException, TikaException {
//            System.out.println("=== Starting Document Parsing ===");
//            System.out.println("File: " + file.getOriginalFilename() + " (Size: " + file.getSize() + " bytes)");
//            try {
//                // Parse the document using Apache Tika
//                String text = tika.parseToString(file.getInputStream());
//                System.out.println("Extracted Text Length: " + text.length());
//                if (text.trim().isEmpty()) {
//                    throw new IllegalArgumentException("Parsed text is empty. The document may be unsupported or corrupted.");
//                }
//                return text;
//            } catch (Exception e) {
//                System.err.println("Parsing Failed: " + e.getMessage());
//                throw e;
//            }
//        }
public String parseDocument(MultipartFile file) throws IOException {
    System.out.println("=== Starting Document Parsing ===");
    System.out.println("File: " + file.getOriginalFilename() + " (Size: " + file.getSize() + " bytes)");
    try (InputStream inputStream = file.getInputStream()) {
        // Read the file content as a string
        String text = new String(inputStream.readAllBytes());
        System.out.println("Raw File Content: " + text);
        System.out.println("Extracted Text Length: " + text.length());
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Parsed text is empty. The document may be unsupported or corrupted.");
        }
        return text;
    } catch (Exception e) {
        System.err.println("Parsing Failed: " + e.getMessage());
        throw e;
    }
}
}
