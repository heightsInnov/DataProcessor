/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.reader;

import java.io.File;
import java.io.FileInputStream;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


/**
 *
 * @author User
 */
public class PDFReader {
    
    public StringBuffer extractPdf(String docUrl){
        
        StringBuffer buffer = new StringBuffer();
        File file = new File(docUrl);
        try{
            try (FileInputStream fileStream = new FileInputStream(file)) {
                PDDocument doc1 = Loader.loadPDF(fileStream);
                
                PDFTextStripper stripper = new PDFTextStripper();
                buffer.append(stripper.getText(doc1));
                
                System.out.println("Other response is "+buffer);
            }
        }catch(Exception e){
          return buffer;  
        }
        return buffer;
    }
}
