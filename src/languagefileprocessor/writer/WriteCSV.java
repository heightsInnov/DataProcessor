/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author User
 */
public class WriteCSV {

    public static void writeToCsv(List<List<String>> wordArray, String pathToFile) {

        try (FileWriter csvWriter = new FileWriter(pathToFile + "\\" + new Date().getTime() + ".csv")) {
            for(List<String> s : wordArray){
                csvWriter.append(String.join(",", s)+"\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException ex) {
        }
    }
    
    public static void writeFrequency(List<Map<String, Integer>> wordArray, String pathToFile) {

        try (FileWriter csvWriter = new FileWriter(pathToFile + "\\" + new Date().getTime() + ".csv")) {
            System.out.println(wordArray.toString());
            for(Map<String, Integer> s : wordArray){
                csvWriter.append(String.join(",", s.keySet())+"\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException ex) {
        }
    }
}
