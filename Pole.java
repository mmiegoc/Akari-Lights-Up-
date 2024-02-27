package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Pole extends JButton {

    private final int wiersz;
    private final int kolumna;
    private String stan;
    private int oswietlenie;

    public Pole(int wiersz, int kolumna, String stanPoczatkowy) {
        super();
        this.wiersz = wiersz;
        this.kolumna = kolumna;
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setFont(new Font("Dialog", Font.PLAIN, 16));
        zmienStan(stanPoczatkowy);

    }

    public void setOswietlenie(int noweOswietlenie) {
        oswietlenie = noweOswietlenie;
    }

    public void zmienStan(String stan) {
        this.stan = stan;

        if (Objects.equals(this.stan, "z")) {
            this.setBackground(Color.YELLOW);
            this.setForeground(Color.blue);
            this.setText("\uD83D\uDCA1");
            this.setOswietlenie(0);
        }
        if (Objects.equals(this.stan, "b")) {
            this.setBackground(Color.WHITE);
            this.setText("");
            this.setOswietlenie(0);
        }
        if (Objects.equals(this.stan, "c")) {
            this.setBackground(Color.BLACK);
            this.setText("");
        }
        if (Objects.equals(this.stan, "0")
                || Objects.equals(this.stan, "1")
                || Objects.equals(this.stan, "2")
                || Objects.equals(this.stan, "3")
                || Objects.equals(this.stan, "4")) {
            this.setBackground(Color.BLACK);
            this.setForeground(Color.WHITE);
            this.setText(this.stan);
        }
        if (Objects.equals(this.stan, "x")) {
            this.setBackground(Color.WHITE);
            this.setForeground(Color.BLACK);
            this.setText("x");
            this.setOswietlenie(0);
        }
        if (Objects.equals(this.stan, "xp")) {
            this.setBackground(Color.YELLOW);
            this.setForeground(Color.BLACK);
            this.setText("x");
            this.setOswietlenie(1);
        }
        if (Objects.equals(this.stan, "p")) {
            this.setBackground(Color.YELLOW);
            this.setText("");
            this.setOswietlenie(1);
        }
    }

    public void zwiekszOswietlenie(int oswietlenie) {
        this.setOswietlenie(oswietlenie + 1);
    }

    public void zmniejszOswietlenie(int oswietlenie) {
        this.setOswietlenie(oswietlenie - 1);
    }


    public int getWiersz() {
        return wiersz;
    }

    public int getKolumna() {
        return kolumna;
    }

    public String getStan() {
        return stan;
    }

    public int getOswietlenie() {
        return oswietlenie;
    }

}
