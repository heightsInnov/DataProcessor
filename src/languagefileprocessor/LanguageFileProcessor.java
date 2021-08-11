/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import languagefileprocessor.reader.PDFReader;
import languagefileprocessor.cleaner.RemoveUnwantedChar;
import languagefileprocessor.reader.TextReader;
import languagefileprocessor.reader.WordDocumentReader;
import languagefileprocessor.statistics.CalculateStatistics;
import languagefileprocessor.statistics.GetVowelCount;
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
    List<List<String>> statistics = new ArrayList<>();

    private void Processor() {

        File directory = new File(System.getProperty("user.dir") + "/output");
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("============ Output folder created successfully! =============\n");
            } else {
                System.out.println("============ Output folder not created! =============");
                return;
            }
        } else {
            System.out.println("============ Output folder already created! =============\n");
        }

        //======= Receive file path ===========
        List<String> docPaths = new ArrayList<>();
        String docspath = "";

        System.out.println("Supply document path EN: \n");
        docspath = sc.nextLine().trim();
        if (!docspath.isEmpty()) {
            docPaths.add(docspath);
        } else {
            System.out.println("Supply document path EN: \n");
            docspath = sc.nextLine().trim();
        }

        System.out.println("Supply document path FR: \n");
        docPaths.add(sc.nextLine().trim());

        System.out.println("Supply document path GR: \n");
        docPaths.add(sc.nextLine().trim());

        System.out.println("Supply document path PR: \n");
        docPaths.add(sc.nextLine().trim());

        System.out.println("Supply document path SP: \n");
        docPaths.add(sc.nextLine().trim());

        //======= Receive character length for sampling ===========
        System.out.println("Supply character length for sampling: ");
        sampleLength = sc.nextInt();

        if (sampleLength <= 0) {
            System.out.println("Supply character length for sampling: ");
            sampleLength = sc.nextInt();
        }

        //METRICS
        List<String> metrics = new ArrayList<>();
        metrics.add("Language");
        metrics.add("Character count");
        metrics.add("No of space");
        metrics.add("word count");
        metrics.add("Vowel count");
        metrics.add("Consonant count");
//        metrics.add("Line count");

        statistics.add(metrics);

        String fileExt;
        String docUrl = "";
        for (String documentUrl : docPaths) {
            fileExt = documentUrl.substring(documentUrl.lastIndexOf(".") + 1);
            docUrl = documentUrl;

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
                    buffer = wordReader.extractWord(documentUrl, fileExt);
                    break;
                default:
                    System.out.println("File format not permitted.");
            }

            if (buffer.toString().isEmpty()) {
                System.out.println("Unable to process file, process returned an empty string!");
            }

            checkCount(sampleLength);

            List<String> textArray = RemoveUnwantedChar
                    .splitText(cleanedText)
                    .stream()
                    .filter(t -> !t.isEmpty())
                    .collect(Collectors.toList());

            List<String> results = new ArrayList<>();
            results.add(textArray.get(0));
            results = CalculateStatistics.getCharacterCount(cleanedText, results);
            results.add(String.valueOf(textArray.size()));
            results = GetVowelCount.vowelCount(cleanedText, results, textArray.get(0));
//            try {
//                results.add(String.valueOf(Files.lines(Paths.get(docUrl)).count()));
//            } catch (IOException ex) {
//                Logger.getLogger(LanguageFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
//            }

            statistics.add(results);

            String[] textArrayString = textArray.toArray(new String[0]);
            Map<String, Integer> freq = new HashMap<>();
//             Loop to iterate over the
//             elements of the map
            CalculateStatistics.getWordCount(textArrayString).entrySet().forEach(entry -> {
                freq.put(entry.getKey(), entry.getValue());
                System.out.format("%15s%10d \n", entry.getKey(), entry.getValue());
            });
        }

        System.out.println(statistics);
        //Send Final Data Array into a CSV File
        WriteCSV.writeToCsv(statistics, directory.getPath());
    }

    void checkCount(int charCount) {
        try {
            cleanedText = RemoveUnwantedChar.cleanText(buffer.toString());
            cleanedText = cleanedText.substring(0, sampleLength);
            System.out.println(cleanedText);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Total character shorter than specified length! Total count of characters in file is " + buffer.toString().length());
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
