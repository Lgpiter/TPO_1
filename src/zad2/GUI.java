package zad2;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
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

    public void showWikiPage() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(city + " - Wikipedia");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1600, 1000);

            // Panel na górnej części okna
            JPanel topPanel = new JPanel(new BorderLayout());
            JLabel headerLabel = new JLabel("Kraj: " + "POLSKA" + ", Miasto: " + city);
            Font topPanelFont = headerLabel.getFont().deriveFont(Font.BOLD, 80f);
            headerLabel.setFont(topPanelFont);
            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(headerLabel, BorderLayout.CENTER);
            frame.add(topPanel, BorderLayout.NORTH);

            // Podzielenie dolnej części okna na 4 podramki
            JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
            frame.add(bottomPanel, BorderLayout.CENTER);

            JFXPanel jfxPanel1 = new JFXPanel();
            bottomPanel.add(jfxPanel1);


            JPanel textPanel = new JPanel(new BorderLayout());
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

            bottomPanel.add(textPanel);



            JPanel nbpTextPannel = new JPanel(new BorderLayout());
            JLabel textLabelNbp1= new JLabel("Kurs waluty " + currencyCode + " wzgledem PLN wedlug NBP");
            JLabel textLabelNbp2 = new JLabel(nbpValue);

            textLabelNbp1.setFont(font);
            textLabelNbp2.setFont(font2);

            textLabelNbp1.setHorizontalAlignment(SwingConstants.CENTER);
            textLabelNbp2.setHorizontalAlignment(SwingConstants.CENTER);
            nbpTextPannel.add(textLabelNbp1, BorderLayout.NORTH);
            nbpTextPannel.add(textLabelNbp2, BorderLayout.CENTER);

            bottomPanel.add(nbpTextPannel);

            JFXPanel jfxPanel4 = new JFXPanel();
            bottomPanel.add(jfxPanel4);

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

}
