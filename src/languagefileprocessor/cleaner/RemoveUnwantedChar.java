/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.cleaner;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author User
 */
public class RemoveUnwantedChar {

    public static String cleanText(String text) {
        
        return text.replaceAll("[^a-zA-Z_ ]", " ").toLowerCase();
    }
    
    public static List<String> splitText(String text) {
        
        String[] t = text.split("\\s+");
        return  Arrays.asList(t);
    }
}