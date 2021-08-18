/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    String uncleanText = "";

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
        System.out.println("Supply document path: \n");
        String docspath = sc.nextLine().trim();
        if (docspath.isEmpty()) {
            System.out.println("Supply document path: \n");
            docspath = sc.nextLine().trim();
        }

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
        metrics.add("Short word count");
        metrics.add("Long word count");
        metrics.add("Sentence length");
        metrics.add("Bigram Distribution");
        metrics.add("Trigram Distribution");

        statistics.add(metrics);

        String fileExt = docspath.substring(docspath.lastIndexOf(".") + 1);
        switch (fileExt.toLowerCase()) {
            case "pdf":
                PDFReader pdfReader = new PDFReader();
                buffer = pdfReader.extractPdf(docspath);
                break;
            case "txt":
                TextReader txtReader = new TextReader();
                buffer = txtReader.ReadFileUrl(docspath);
                break;
            case "doc":
            case "docx":
                WordDocumentReader wordReader = new WordDocumentReader();
                buffer = wordReader.extractWord(docspath, fileExt);
                break;
            default:
                System.out.println("File format not permitted.");
        }

        int totalCount = 0;
        String bufferString = buffer.toString();
        if (bufferString.isEmpty()) {
            System.out.println("Unable to process file, process returned an empty string!");
        } else {
            totalCount = bufferString.length();
        }

        List<String> list = new ArrayList<>();
        int index = 0;
        while (index < totalCount) {
            list.add(bufferString.substring(index, Math.min(index + sampleLength, totalCount)));
            index = index + sampleLength;
        }

        for (int i = 0; i < list.size(); i++) {
            if (i <= 15) {

                String uncleanText = list.get(i);
                cleanedText = RemoveUnwantedChar.cleanText(uncleanText);
                List<String> textArray = RemoveUnwantedChar
                        .splitText(cleanedText)
                        .stream()
                        .filter(t -> !t.isEmpty())
                        .collect(Collectors.toList());

                List<String> results = new ArrayList<>();
                results.add("Sample" + (i + 1));
                results = CalculateStatistics.getCharacterCount(cleanedText, results);
                results.add(String.valueOf(textArray.size()));
                results = GetVowelCount.vowelCount(cleanedText, results, textArray.get(0));
                results = CalculateStatistics.calculateWordLength(textArray, results, textArray.get(0));
                results.add(String.valueOf(uncleanText.split("[\\.\\?!]").length));
                results.add(String.valueOf(CalculateStatistics.bigramCounter(2, textArray)));
                results.add(String.valueOf(CalculateStatistics.bigramCounter(3, textArray)));
                statistics.add(results);
            }
        }
        //Send Final Data Array into a CSV File
        WriteCSV.writeToCsv(statistics, directory.getPath());
    }

    public static void main(String[] args) {
        LanguageFileProcessor p = new LanguageFileProcessor();
        p.Processor();
    }
}
