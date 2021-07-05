/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languagefileprocessor.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 *
 * @author User
 */
public class TextReader {

    FileReader ins = null;

    public StringBuffer ReadFileUrl(String documentUrl) {
        StringBuffer buffer = new StringBuffer();
        File inFile = new File(documentUrl);
        try {
            String string = new String();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
            while ((string = reader.readLine()) != null) {
                buffer.append(string);
                buffer.append("\n");
            }
            reader.close();

            System.out.println("Reader read >> \n " + buffer);
        } catch (Exception e) {
            return buffer;
        } finally {
            try {
                ins.close();
            } catch (Exception e) {
            }
        }

        return buffer;
    }
}
