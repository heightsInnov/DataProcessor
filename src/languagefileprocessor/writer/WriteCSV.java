/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author User
 */
public class WriteCSV {

    public static void writeToCsv(String[] wordArray, String pathToFile) {

        List<String> wordList = new ArrayList<>();
        Collections.addAll(wordList, wordArray);

        Long dateTimeMills = new Date().getTime();
        
        try (FileWriter csvWriter = new FileWriter(pathToFile+"\\"+dateTimeMills+".csv")) {
            csvWriter.append(String.join(",", wordList));
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
