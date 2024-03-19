/**
 *
 *  @author Zadykowicz Piotr S24144
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class Service {
    String country;
    String countryShortcut;
    String currencyCode;
    String city;
    String currencyForValueInserted;
    String nbpValue;

    public Service(String country){
        this.country = country;
    }

    public String getCity(){
        return this.city;
    }

    public String getCountry(){
        return this.country;
    }

    public String getCurrencyCode(){
        return this.currencyCode;
    }

    public String getCurrencyForValueInserted(){
        return this.currencyForValueInserted;
    }

    public String getNbpValue(){
        return this.nbpValue;
    }

    //function returning the json of actual weather in the city
    public String getWeather(String miasto){
        this.city = miasto;
        String apiKey = "f6d28a2cbcc0ec6ed84819bc9c893d89";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + miasto + "," + country + "&appid=" + apiKey;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //returning JSON
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "o";
    }

    //function returning value from api
    public Double getRateFor(String currencyCode) {
        this.currencyCode = currencyCode;

        String apiKey = "43fd4e157d6419909cf1365b";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey +"/latest/" + currencyCode;
        String exchangeValue = "";


        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }




        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String jsonResponse = reader.lines().collect(Collectors.joining());

            this.countryShortcut = CurrencyCalucaltor.countryCurrency(country);
            if(jsonResponse.contains(countryShortcut)){
                //Finding our Country shortcut in Json
                int shortcutIndex = jsonResponse.indexOf(countryShortcut);
                 exchangeValue = jsonResponse.substring(shortcutIndex);


                //Getting only exchange value
                int commaIndex = exchangeValue.indexOf(',');
                exchangeValue = exchangeValue.substring(0,commaIndex);

                exchangeValue = exchangeValue.substring(5);

                currencyForValueInserted = exchangeValue;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Double.parseDouble(exchangeValue);
    }

    //value from NBP
    public Double getNBPRate() {
        String[] differentWebsites = {"A", "B", "C"};

        for(String s : differentWebsites) {
            try {
                String url = "http://api.nbp.pl/api/exchangerates/tables/" + s + "/?format=json";
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");


                try {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                } catch (IOException e) {
                    e.printStackTrace();
                }



                //returning JSON
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {

                        if(line.contains(currencyCode)){
                            System.out.println(line);

                            int shortcutIndex = line.indexOf(currencyCode);
                            line = line.substring(shortcutIndex);

                            //Getting only exchange value

                            int commaIndex = line.indexOf(':');
                            line = line.substring(commaIndex);
                            System.out.println(line);

                            int signIndex = line.indexOf('}');
                            line = line.substring(1,signIndex);

                            nbpValue = line;

                            return Double.valueOf(line);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
