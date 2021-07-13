/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.reader;

/**
 *
 * @author User
 */
public class RemoveUnwantedChar {

    public static String cleanText(String text) {
        
        return text.replaceAll("[^a-zA-Z ]", " ").toLowerCase();
    }
    
    public static String[] splitText(String text) {
        
        return text.split("\\s+");
    }
}