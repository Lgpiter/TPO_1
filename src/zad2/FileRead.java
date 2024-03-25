package zad2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {
    public static String getCountryCode2Digits(String country){
        String countryCode = "";

        String filePath = "Countries.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = reader.readLine()) != null) {
                if (line.contains(country)) {
                    String[] parts = line.split("\\s+"); // Rozdzielenie linii po białych znakach
                        String abbreviation = parts[parts.length - 1];
                        countryCode = parts[1];
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return countryCode;
    }

    public static String getCountryCurrency(String country){
        String bigLetterCountry = country.toUpperCase();
        String countryCode = "1";

        String filePath = "Currencies.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = reader.readLine()) != null) {

                if (line.contains(bigLetterCountry)) {
                    String[] parts = line.split("\\s+"); // Rozdzielenie linii po białych znakach
                    countryCode = parts[parts.length - 1];

                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(countryCode);
        return countryCode;
    }
}
