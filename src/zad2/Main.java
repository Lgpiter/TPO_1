/**
 *
 *  @author Zadykowicz Piotr S24144
 *
 */

package zad2;


public class Main {
    public static void main(String[] args) {

        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();


        GUI myGui = new GUI(s.getCity(), s.getCountry(), s.getCurrencyCode(), s.getCurrencyForValueInserted(), s.nbpValue, s.getWeather());
        myGui.showWikiPage();




    }
}
