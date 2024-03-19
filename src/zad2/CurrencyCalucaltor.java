package zad2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CurrencyCalucaltor {

    //function for finding the coutrny currency shortcut from file
    public static String countryCurrency(String country){
        String shortcut="";


        String filePath = "src/zad2/Countries.txt";

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Checking if line with Country Name was found
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(country)) {
                    shortcut = line.substring(line.length() - 3);
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return shortcut;
    }


}
