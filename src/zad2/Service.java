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
    String currencyForValueInserted;
    String currencyCode;
    String countryCurrency;
    String city;

    String nbpValue;
    String weather;

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

    public String getWeather(){return this.weather;}

    public String getCurrencyForValueInserted(){return  this.currencyForValueInserted;}


    //function returning the json of actual weather in the city
    public String getWeather(String city){
        this.city = city;
        String apiKey = "f6d28a2cbcc0ec6ed84819bc9c893d89";
        String countryCode = FileRead.getCountryCode2Digits(country);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + countryCode + "&appid=" + apiKey;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //returning JSON
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            weather = reader.lines().collect(Collectors.joining());
            System.out.println(weather);
            return weather;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "o";
    }

    //function returning value from api
    public Double getRateFor(String currencyCode) {
        this.currencyCode = currencyCode;
        countryCurrency = FileRead.getCountryCurrency(country);

        String apiKey = "43fd4e157d6419909cf1365b";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey +"/latest/" + countryCurrency;
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
            System.out.println(jsonResponse);

            if(jsonResponse.contains(currencyCode)){
                //Finding our Country shortcut in Json
                int shortcutIndex = jsonResponse.indexOf(currencyCode);
                 exchangeValue = jsonResponse.substring(shortcutIndex);


                //Getting only exchange value
                int commaIndex = exchangeValue.indexOf(',');
                exchangeValue = exchangeValue.substring(0,commaIndex);

                exchangeValue = exchangeValue.substring(5);

                currencyForValueInserted = exchangeValue;

                System.out.println("Value exchange for currency inserted " + exchangeValue);

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

                        if(countryCurrency.contains("PLN") ){
                            nbpValue = "1.0";
                            return 1.0;
                        }

                        if(line.contains(countryCurrency)){

                            int shortcutIndex = line.indexOf(countryCurrency);
                            line = line.substring(shortcutIndex);

                            //Getting only exchange value

                            int commaIndex = line.indexOf(':');
                            line = line.substring(commaIndex);

                            int signIndex = line.indexOf('}');
                            line = line.substring(1,signIndex);

                            nbpValue = line;
                            System.out.println(line);

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
