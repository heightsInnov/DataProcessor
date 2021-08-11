/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.statistics;

import java.util.List;

/**
 *
 * @author User
 */
public class GetVowelCount {

    public static List<String> vowelCount(String str, List<String> list, String lang) {
        int vCount = 0, cCount = 0;

        //Converting entire string to lower case to reduce the comparisons    
        str = str.toLowerCase();

        if (lang.equals("french")) {
            for (int i = 0; i < str.length(); i++) {
                //Checks whether a character is a vowel
                if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u') {
                    //Increments the vowel counter    
                    vCount++;
                } //Checks whether a character is a consonant    
                else if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                    //Increments the consonant counter    
                    cCount++;
                }
            }
        } else {
            for (int i = 0; i < str.length(); i++) {
                //Checks whether a character is a vowel
                if (str.charAt(i) == 'a' || str.charAt(i) == 'e' || str.charAt(i) == 'i' || str.charAt(i) == 'o' || str.charAt(i) == 'u' || str.charAt(i) == 'y') {
                    //Increments the vowel counter    
                    vCount++;
                } //Checks whether a character is a consonant    
                else if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                    //Increments the consonant counter    
                    cCount++;
                }
            }
        }
        list.add(String.valueOf(vCount));
        list.add(String.valueOf(cCount));

        return list;
    }
}
