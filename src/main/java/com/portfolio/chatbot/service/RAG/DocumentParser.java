package com.portfolio.chatbot.service.RAG;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

@Service
public class DocumentParser {
    private final Tika tika = new Tika();
    public String parseDocument(MultipartFile file) throws IOException, TikaException {
        System.out.println("=== Starting Document Parsing ===: " + file.getOriginalFilename());
        if (file.getOriginalFilename() != null && file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            System.out.println("Parsing PDF file...");
            return parsePdf(file);
        } else {
            System.out.println("Parsing non-PDF file...");
            return parseNonPdf(file);
        }
    }
    private String parsePdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            if (document.isEncrypted()) {
                throw new IllegalArgumentException("The PDF is password-protected and cannot be parsed.");
            }
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println("Extracted Text Length: " + text.length());
            if (text.trim().isEmpty()) {
                throw new IllegalArgumentException("Parsed text is empty. The PDF may be image-only.");
            }
            return text;
        } catch (Exception e) {
            System.err.println("PDF Parsing Failed: " + e.getMessage());
            throw e;
        }
    }
    private String parseNonPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            XWPFDocument document = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String text = extractor.getText();

            if (text == null || text.trim().isEmpty()) {
                throw new IllegalArgumentException("DOCX file contains no readable text.");
            }
            return text;
        }
    }


}
