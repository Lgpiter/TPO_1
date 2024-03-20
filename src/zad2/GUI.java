package zad2;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI {

    String city;
    String country;
    String currencyCode;
    String currencyForValueInserted;
    String nbpValue;

    public GUI(String city, String country, String currencyCode, String currencyForValueInserted, String nbpValue){
        this.city=city;
        this.country = country;
        this.currencyCode = currencyCode;
        this.currencyForValueInserted = currencyForValueInserted;
        this.nbpValue = nbpValue;
    }



    private void initializeWebView(JFXPanel jfxPanel, String city) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://en.wikipedia.org/wiki/" + city);
        jfxPanel.setScene(new Scene(webView));
    }

    public void showWikiPage() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(city + " - Wikipedia");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1600, 1000);

            // Panel na górnej części okna
            JPanel topPanel = new JPanel(new BorderLayout());
            Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
            topPanel.setBorder(border);
            topPanel.setBackground(new Color(173, 216, 255));
            JLabel headerLabel = new JLabel("Kraj: " + country + ", Miasto: " + city);
            Font topPanelFont = headerLabel.getFont().deriveFont(Font.BOLD, 80f);
            headerLabel.setFont(topPanelFont);
            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(headerLabel, BorderLayout.CENTER);
            frame.add(topPanel, BorderLayout.NORTH);

            // Podzielenie dolnej części okna na 4 podramki
            JPanel bottomPanel = new JPanel(new GridLayout(1,2));
            frame.add(bottomPanel, BorderLayout.CENTER);



            JPanel leftPanel = new JPanel(new GridLayout(2,1));
            //Currency Pannel
            JPanel currencyPanel = currencyPanel();

           //adding NBP Pannel
            JPanel nbpPanel = nbpPanel();

            JPanel valuePanel = valuePanel(currencyPanel, nbpPanel);

            leftPanel.add(valuePanel);
            bottomPanel.add(leftPanel);

            JFXPanel jfxPanel1 = new JFXPanel();
            bottomPanel.add(jfxPanel1);


            frame.setVisible(true);

            Platform.runLater(() -> {
                WebView webView1 = new WebView();
                WebEngine webEngine1 = webView1.getEngine();
                webEngine1.load("https://en.wikipedia.org/wiki/" + city);
                jfxPanel1.setScene(new Scene(webView1));

                // Tutaj możesz dodać kolejne panele z innymi komponentami, jeśli potrzebujesz
            });
        });
    }



    public JPanel nbpPanel(){
        JPanel nbpTextPannel = new JPanel(new GridLayout(2,1));
        nbpTextPannel.setBackground(new Color(173, 216, 255));
        JLabel textLabelNbp1= new JLabel("Kurs waluty " + currencyCode + " wzgledem PLN wedlug NBP");
        JLabel textLabelNbp2 = new JLabel(nbpValue);

        Font font = textLabelNbp1.getFont().deriveFont(Font.BOLD, 20f);
        Font font2 = textLabelNbp2.getFont().deriveFont(Font.BOLD, 80f);
        textLabelNbp1.setFont(font);
        textLabelNbp2.setFont(font2);

        textLabelNbp1.setHorizontalAlignment(SwingConstants.CENTER);
        textLabelNbp2.setHorizontalAlignment(SwingConstants.CENTER);

        nbpTextPannel.add(textLabelNbp1);
        nbpTextPannel.add(textLabelNbp2);

        return nbpTextPannel;
    }

    public JPanel currencyPanel(){
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(173, 216, 255));
        JLabel textLabel = new JLabel("Kurs wymiany waluty w państwie " + this.country + " na walutę " + currencyCode);
        JLabel textLabel2 = new JLabel(currencyForValueInserted);
        Font font = textLabel.getFont().deriveFont(Font.BOLD, 20f);
        Font font2 = textLabel2.getFont().deriveFont(Font.BOLD, 80f); // Zwiększenie rozmiaru czcionki

        textLabel.setFont(font);
        textLabel2.setFont(font2);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        textPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(textLabel2, BorderLayout.CENTER);

        return textPanel;
    }

    public JPanel valuePanel(JPanel label1, JPanel label2){
        JPanel result = new JPanel(new GridLayout(2,1));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        result.setBorder(border);
        result.setBackground(new Color(173, 216, 255));
        result.add(label1);
        result.add(label2);

        return result;
    }



}
