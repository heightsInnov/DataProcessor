/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.runner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 *
 * @author User
 */
public class WordDocumentReader {

    public StringBuffer extractWord(String documentUrl, String ext) {
        StringBuffer buffer = new StringBuffer();

        File file = null;
        WordExtractor extractor = null;
        try {

//            file = new File(documentUrl);
            file = new File("C:\\Users\\User\\Documents\\Externals\\test1.doc");
            try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
                if (ext.equals("doc")) {
                    HWPFDocument document = new HWPFDocument(fis);
                    extractor = new WordExtractor(document);
                    buffer.append(extractor.getText());
                } else {
                    XWPFDocument document = new XWPFDocument(fis);
                    List<XWPFParagraph> paragraphs = document.getParagraphs();
                    
                    for (XWPFParagraph para : paragraphs) {
                        buffer.append(para.getText());
                    }
                }
            }
            System.out.println("Buffer is >> \n" + buffer);
        } catch (Exception exep) {
            exep.printStackTrace();
        }

        return buffer;
    }
}
