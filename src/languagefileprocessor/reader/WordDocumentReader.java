/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.reader;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

/**
 *
 * @author User
 */
public class WordDocumentReader {

    public StringBuffer extractWord(String documentUrl, String ext) {
        StringBuffer buffer = new StringBuffer();

        File file;
        WordExtractor docExtractor;
        XWPFWordExtractor docxExtractor;
        try {
            file = new File(documentUrl);
            try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
                if (ext.equals("doc")) {
                    HWPFDocument document = new HWPFDocument(fis);
                    docExtractor = new WordExtractor(document);
                    buffer.append(docExtractor.getText());
                } else {
                    XWPFDocument document = new XWPFDocument(fis);
                    docxExtractor = new XWPFWordExtractor(document);
                    buffer.append(docxExtractor.getText());
                }
            }
        } catch (Exception exep) {
            exep.printStackTrace();
        }
        return buffer;
    }
}
