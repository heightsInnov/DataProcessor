/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.statistics;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author User
 */
public class CalculateStatistics {
    
    public static int getCharacterCount(String buffer){
        
        // Initializing counters
        int characterCount = 0;
        
        //Count the characters in the string except space    
        for(int i = 0; i < buffer.length(); i++) 
        {    
            if(buffer.charAt(i) != ' ')    
                characterCount++;    
        }
        return characterCount;
    }
    
    public static Map<String,Integer> getWordCount(String[] textArray){
        
        Map<String,Integer> mp = new TreeMap<>();
 
        // Loop to iterate over the words
        for(int i=0;i<textArray.length;i++)
        {
            // Condition to check if the
            // array element is present
            // the hash-map
            if(mp.containsKey(textArray[i]))
                mp.put(textArray[i], mp.get(textArray[i])+1);
            else
                mp.put(textArray[i],1);
        }
        return mp;
    }
}
