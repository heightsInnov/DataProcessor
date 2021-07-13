/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor;

import java.util.Scanner;
import java.util.UnknownFormatFlagsException;
import languagefileprocessor.reader.PDFReader;
import languagefileprocessor.cleaner.RemoveUnwantedChar;
import languagefileprocessor.reader.TextReader;
import languagefileprocessor.reader.WordDocumentReader;
import languagefileprocessor.statistics.CalculateStatistics;
import languagefileprocessor.writer.WriteCSV;

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
        String fileExt = documentUrl.substring(documentUrl.lastIndexOf(".")+1);
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
        String cleanedText = RemoveUnwantedChar.cleanText(buffer.toString());
        String[] textArray = RemoveUnwantedChar.splitText(cleanedText);
        
        System.out.println("Total number of characters is: "+ CalculateStatistics.getCharacterCount(cleanedText));
        System.out.println("Total number of words is: "+ textArray.length);
        System.out.println("=============== Word Frquency =============");
        
        
        // Loop to iterate over the
        // elements of the map
        CalculateStatistics.getWordCount(textArray).entrySet().forEach(entry -> {
                System.out.format("%15s%10d \n", entry.getKey(), entry.getValue());
        });
           
        //Send Final Data Array into a CSV File
        WriteCSV.writeToCsv(textArray, documentUrl.substring(0, documentUrl.lastIndexOf("\\")));
    }
}
