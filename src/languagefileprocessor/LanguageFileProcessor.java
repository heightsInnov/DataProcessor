/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor;

import java.util.Scanner;
import java.util.UnknownFormatFlagsException;
import languagefileprocessor.runner.PDFReader;
import languagefileprocessor.runner.RemoveUnwantedChar;
import languagefileprocessor.runner.TextReader;
import languagefileprocessor.runner.WordDocumentReader;

/**
 *
 * @author User
 */
public class LanguageFileProcessor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StringBuffer buffer = new StringBuffer();

        System.out.println("Supply full document path: ");
        String documentUrl = sc.nextLine();
        String fileExt = documentUrl.substring(documentUrl.lastIndexOf("."));
        switch (fileExt.toLowerCase()) {
            case "pdf":
                PDFReader pdfReader = new PDFReader();
                buffer = pdfReader.extractPdf(documentUrl);
                break;
            case "txt":
                TextReader txtReader = new TextReader();
                buffer = txtReader.ReadFileUrl(documentUrl);
                break;
            case "doc":
            case "docx":
                WordDocumentReader wordReader = new WordDocumentReader();
                buffer = wordReader.extractWord(documentUrl,fileExt);
                break;
            default:
                throw new UnknownFormatFlagsException("File format not permitted.");
        }
        String[] finals = RemoveUnwantedChar.cleanText(buffer.toString());
        
    }

}
