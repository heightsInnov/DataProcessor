/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor;

import java.io.File;
import java.util.Scanner;
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
    
    Scanner sc = new Scanner(System.in);
    StringBuffer buffer = new StringBuffer();
    String cleanedText = "";
    int sampleLength = 0;
    
    private void Processor() {
        
        File directory = new File(System.getProperty("user.dir")+"/output");
        if(!directory.exists())
        {
            if(directory.mkdir())
                System.out.println("============ Output folder created successfully! =============");
            else
            {
                System.out.println("============ Output folder not created! =============");
                return;
            }
        }else
            System.out.println("============ Output folder already created! =============");

        //======= Receive file path ===========
        System.out.println("Supply full document path: ");
        String documentUrl = sc.nextLine().trim();
        
        if(documentUrl == null){
            System.out.println("Supply full document path: ");
            documentUrl = sc.nextLine().trim();
        }
        
        //======= Receive character length for sampling ===========
        System.out.println("Supply character length for sampling: ");
        sampleLength = sc.nextInt();
        
        if(sampleLength <= 0){
            System.out.println("Supply character length for sampling: ");
            sampleLength = sc.nextInt();
        }
        
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
                System.out.println("File format not permitted.");
        }
        
        if(buffer.toString().isEmpty())
        {
            System.out.println("Unable to process file, process returned an empty string!");
        }
        
        checkCount(sampleLength);
        
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
        WriteCSV.writeToCsv(textArray, directory.getPath());
    }
    
    void checkCount(int charCount){
        try{
         cleanedText = RemoveUnwantedChar.cleanText(buffer.toString()).substring(0, sampleLength);
        }catch(StringIndexOutOfBoundsException ex){
            System.out.println("Total character shorter than specified length! Total count of characters in file is "+ buffer.toString().length());
            System.out.println("Supply character length <= total characters for sampling: ");
            sampleLength = sc.nextInt();
            checkCount(sampleLength);
        }
    }
    
    public static void main(String[] args) {
        LanguageFileProcessor p = new LanguageFileProcessor();
        p.Processor();
    }
}
