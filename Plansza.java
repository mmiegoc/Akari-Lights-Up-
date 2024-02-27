package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Plansza {
    private List<String[]> csv = new ArrayList<>();
    private boolean czyCSV = false;
    private int rozmiar;
    private final Pole[][] plansza;
    private String[][] wybranaPlansza;


    private final static String[][] PLANSZA_LATWA = new String[][]{
            {"b", "b", "0", "b", "1", "b", "b"},
            {"b", "b", "b", "b", "1", "b", "b"},
            {"c", "c", "b", "b", "b", "b", "c"},
            {"b", "b", "b", "b", "b", "b", "b"},
            {"0", "b", "b", "b", "b", "1", "2"},
            {"b", "b", "c", "b", "b", "b", "b"},
            {"b", "b", "0", "b", "c", "b", "b"},
    };
    /* b - biale; c - czarne; z - zarowka; x - brak zarowki; p - pole podswietlone przez zarowke;
    0,1,2,3,4 - pola czarne z liczbami zarowek przylegajacych
     */
    private final static String[][] PLANSZA_SREDNIA = new String[][]{
            {"b", "b", "b", "b", "c", "b", "b", "b", "2", "b"},
            {"c", "b", "1", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "c", "b", "c", "b"},
            {"b", "b", "c", "b", "2", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "3", "b", "b", "c"},
            {"c", "b", "b", "0", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "2", "b", "2", "b", "b"},
            {"b", "2", "b", "2", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "c", "b", "0"},
            {"b", "1", "b", "b", "b", "c", "b", "b", "b", "b"}
    };

    private final static String[][] PLANSZA_TRUDNA = new String[][]{
            {"b", "b", "b", "b", "c", "b", "b", "c", "b", "b"},
            {"b", "b", "1", "b", "b", "b", "b", "b", "b", "b"},
            {"0", "b", "b", "b", "b", "b", "1", "b", "1", "b"},
            {"b", "b", "1", "b", "b", "1", "b", "b", "b", "b"},
            {"b", "b", "b", "0", "b", "b", "b", "b", "b", "2"},
            {"1", "b", "b", "b", "b", "b", "1", "b", "b", "b"},
            {"b", "b", "b", "b", "c", "b", "b", "2", "b", "b"},
            {"b", "0", "b", "1", "b", "b", "b", "b", "b", "1"},
            {"b", "b", "b", "b", "b", "b", "b", "c", "b", "b"},
            {"b", "b", "0", "b", "b", "c", "b", "b", "b", "b"}
    };


    public Plansza(int poziom) {
        if (poziom == 1) {
            this.rozmiar = 7;
            wybranaPlansza = PLANSZA_LATWA;
        }
        if (poziom == 2) {
            this.rozmiar = 10;
            wybranaPlansza = PLANSZA_SREDNIA;
        }
        if (poziom == 3) {
            this.rozmiar = 10;
            wybranaPlansza = PLANSZA_TRUDNA;
        }
        plansza = new Pole[rozmiar][rozmiar];

    }


    public Plansza(List<String[]> csv) {
        this.czyCSV = true;
        this.csv = csv;
        this.rozmiar = Integer.parseInt(csv.get(0)[0]);
        plansza = new Pole[rozmiar][rozmiar];
    }

    public JPanel generujPlansze() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rozmiar, rozmiar, 1, 1));
        if (!czyCSV) {
            for (int i = 0; i < rozmiar; i++) {
                for (int j = 0; j < rozmiar; j++) {
                    plansza[i][j] = new Pole(i, j, wybranaPlansza[i][j]);
                    addActionListener(plansza[i][j]);
                    panel.add(plansza[i][j]);
                }
            }
        } else {
            for (int i = 0; i < rozmiar; i++) {
                for (int j = 0; j < rozmiar; j++) {
                    plansza[i][j] = new Pole(i, j, csv.get(i + 1)[j]);
                    addActionListener(plansza[i][j]);
                    panel.add(plansza[i][j]);
                }
            }
        }

        return panel;
    }

    void addActionListener(Pole pole) {

        pole.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                /*włączanie żarowki z białego pola lub x*/
                if (Objects.equals(pole.getStan(), "b") || Objects.equals(pole.getStan(), "x")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("z");
                        petleWlaczajace(pole);
                    } else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "x") /*prawy*/) {
                        pole.zmienStan("b");
                    } else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "b")  /*prawy*/) {
                        pole.zmienStan("x");
                    }
                }
                /*włączanie żarówki z oświetlonego pola lub oświetlonego xp*/
                else if (Objects.equals(pole.getStan(), "p") || Objects.equals(pole.getStan(), "xp")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        int x = pole.getOswietlenie();
                        pole.zmienStan("z");
                        pole.setForeground(Color.red);
                        pole.setOswietlenie(x);
                        petleWlaczajace(pole);
                    } else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "p")  /*prawy*/) {
                        int x = pole.getOswietlenie();
                        pole.zmienStan("xp");
                        pole.setOswietlenie(x);
                    } else if (e.getButton() == MouseEvent.BUTTON3 && Objects.equals(pole.getStan(), "xp")  /*prawy*/) {
                        int x = pole.getOswietlenie();
                        pole.zmienStan("p");
                        pole.setOswietlenie(x);
                    }
                }
                /*wyłączanie żarówki na białe pole*/
                else if (Objects.equals(pole.getStan(), "z") && pole.getOswietlenie() == 0) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("b");
                        petleWylaczajace(pole);
                    }
                    /*wyłączanie żarówki na pole x*/
                    else if (e.getButton() == MouseEvent.BUTTON3 && pole.getOswietlenie() == 0/*prawy*/) {
                        pole.zmienStan("x");
                        petleWylaczajace(pole);
                    }
                }
                /*wyłączanie żarówki na pole oświetlone*/
                else if (Objects.equals(pole.getStan(), "z") && pole.getOswietlenie() > 0) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        int x = pole.getOswietlenie();
                        pole.zmienStan("p");
                        pole.setOswietlenie(x);
                        petleWylaczajace(pole);
                    }
                    /*wyłączanie żarówki na pole oświetlone xp*/
                    else if (e.getButton() == MouseEvent.BUTTON3 && pole.getOswietlenie() > 0 /*prawy*/) {
                        int x = pole.getOswietlenie();
                        pole.zmienStan("xp");
                        pole.setOswietlenie(x);
                        petleWylaczajace(pole);
                    }
                }
            }
        });
    }


    public int getRozmiar() {
        return rozmiar;
    }

    public Pole[][] getPlansza() {
        return plansza;
    }

    public Pole getPole(int wiersz, int kolumna) {
        if (wiersz > rozmiar || kolumna > rozmiar) {
            return null;
        }
        return plansza[wiersz][kolumna];
    }

    public void wypisanieNaKonsole() {
        for (int i = 0; i < rozmiar; i++) {
            System.out.println();
            for (int j = 0; j < rozmiar; j++) {
                System.out.print(plansza[i][j].getStan() + " ");
            }
        }
    }

    public boolean petleSprawdzajace() {
        boolean blad = false;
        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                Pole poleTemp = getPole(i,j);
                if(Objects.equals(poleTemp.getStan(), "b")){
                    blad = true;
                }
                else if(Objects.equals(poleTemp.getStan(), "z")) {
                    if (poleTemp.getOswietlenie() > 0) {
                        blad = true;
                        break;
                    }
                }
                else if (Objects.equals(poleTemp.getStan(), "0")) {
                    int check0 = sprawdzenieZarowek(i, j);
                    if (check0 != 0) {
                        blad = true;
                        break;
                    }
                }
                else if (Objects.equals(poleTemp.getStan(), "1")) {
                    int check1 = sprawdzenieZarowek(i, j);
                    if (check1 != 1) {
                        blad = true;
                        break;
                    }
                }
                else if (Objects.equals(poleTemp.getStan(), "2")) {
                    int check2 = sprawdzenieZarowek(i, j);
                    if (check2 != 2) {
                        blad = true;
                        break;
                    }
                }
                else if (Objects.equals(poleTemp.getStan(), "3")) {
                    int check3 = sprawdzenieZarowek(i, j);
                    if (check3 != 3) {
                        blad = true;
                        break;
                    }
                }
                else if (Objects.equals(poleTemp.getStan(), "4")) {
                    int check4 = sprawdzenieZarowek(i, j);
                    if (check4 != 4) {
                        blad = true;
                        break;
                    }
                }
            }
        }
        return blad;
    }

    public int sprawdzenieZarowek(int i, int j) {
        int lightsAmount = 0;
        if (i>0 && Objects.equals(getPole(i-1,j).getStan(), "z")){
            lightsAmount++;
        }
        if (j>0 && Objects.equals(getPole(i,j-1).getStan(), "z")){
            lightsAmount++;
        }
        if (i<rozmiar-1 && Objects.equals(getPole(i+1,j).getStan(), "z")){
            lightsAmount++;
        }
        if (j<rozmiar-1 && Objects.equals(getPole(i,j+1).getStan(), "z")){
            lightsAmount++;
        }
        return lightsAmount;
    }

    public void petleWlaczajace(Pole pole) {
        for (int i = 1; i < (rozmiar - pole.getKolumna()); i++) {
            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna()) + i);
            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "0") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "1") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "2") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "3") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "4")) {
                break;
            }
            switch (poleZNastepnejKolumny.getStan()) {
                case "x" -> poleZNastepnejKolumny.zmienStan("xp");
                case "b" -> poleZNastepnejKolumny.zmienStan("p");
                case "p", "xp" -> poleZNastepnejKolumny.zwiekszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                case "z" -> {
                    if (poleZNastepnejKolumny.getOswietlenie() == 0) {
                        poleZNastepnejKolumny.setForeground(Color.red);
                        poleZNastepnejKolumny.zwiekszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                    } else poleZNastepnejKolumny.zwiekszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                }
            }
        }
        for (int i = (pole.getKolumna() - 1); i >= 0; i--) {
            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "4")) {
                break;
            }
            switch (poleZPoprzedniejKolumny.getStan()) {
                case "x" -> poleZPoprzedniejKolumny.zmienStan("xp");
                case "b" -> poleZPoprzedniejKolumny.zmienStan("p");
                case "p", "xp" -> poleZPoprzedniejKolumny.zwiekszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                case "z" -> {
                    if (poleZPoprzedniejKolumny.getOswietlenie() == 0) {
                        poleZPoprzedniejKolumny.setForeground(Color.red);
                        poleZPoprzedniejKolumny.zwiekszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                    } else poleZPoprzedniejKolumny.zwiekszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                }
            }
        }
        for (int i = (pole.getWiersz() - 1); i >= 0; i--) {
            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "4")){
                break;
            }
            switch (poleZPoprzedniegoWiersza.getStan()) {
                case "x" -> poleZPoprzedniegoWiersza.zmienStan("xp");
                case "b" -> poleZPoprzedniegoWiersza.zmienStan("p");
                case "p", "xp" -> poleZPoprzedniegoWiersza.zwiekszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                case "z" -> {
                    if (poleZPoprzedniegoWiersza.getOswietlenie() == 0) {
                        poleZPoprzedniegoWiersza.setForeground(Color.red);
                        poleZPoprzedniegoWiersza.zwiekszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                    } else poleZPoprzedniegoWiersza.zwiekszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                }
            }
        }
        for (int i = 1; i < (rozmiar - pole.getWiersz()); i++) {
            Pole poleZNastepengoWiersza = getPole((pole.getWiersz() + i), (pole.getKolumna()));
            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "0") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "1") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "2") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "3") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "4")) {
                break;
            }
            switch (poleZNastepengoWiersza.getStan()) {
                case "x" -> poleZNastepengoWiersza.zmienStan("xp");
                case "b" -> poleZNastepengoWiersza.zmienStan("p");
                case "p", "xp" -> poleZNastepengoWiersza.zwiekszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                case "z" -> {
                    if (poleZNastepengoWiersza.getOswietlenie() == 0) {
                        poleZNastepengoWiersza.setForeground(Color.red);
                        poleZNastepengoWiersza.zwiekszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                    } else poleZNastepengoWiersza.zwiekszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                }
            }
        }
    }

    public void petleWylaczajace(Pole pole) {
        for (int i = 1; i < (rozmiar - pole.getKolumna()); i++) {
            Pole poleZNastepnejKolumny = getPole(pole.getWiersz(), (pole.getKolumna()) + i);
            if (Objects.equals(poleZNastepnejKolumny.getStan(), "c") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "0") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "1") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "2") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "3") ||
                    Objects.equals(poleZNastepnejKolumny.getStan(), "4")){
                break;
            }
            switch (poleZNastepnejKolumny.getStan()) {
                case "xp":
                    if (poleZNastepnejKolumny.getOswietlenie() == 1) {
                        poleZNastepnejKolumny.zmienStan("x");
                    } else {
                        poleZNastepnejKolumny.zmniejszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                    }
                    break;
                case "p":
                    if (poleZNastepnejKolumny.getOswietlenie() > 1) {
                        poleZNastepnejKolumny.zmniejszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                    } else {
                        poleZNastepnejKolumny.zmienStan("b");
                    }
                    break;
                case "z":
                    if (poleZNastepnejKolumny.getOswietlenie() == 1) {
                        poleZNastepnejKolumny.setForeground(Color.blue);
                        poleZNastepnejKolumny.zmniejszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                    } else
                        poleZNastepnejKolumny.zmniejszOswietlenie(poleZNastepnejKolumny.getOswietlenie());
                    break;

            }
        }
        for (int i = (pole.getKolumna() - 1); i >= 0; i--) {
            Pole poleZPoprzedniejKolumny = getPole(pole.getWiersz(), (i));
            if (Objects.equals(poleZPoprzedniejKolumny.getStan(), "c") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "0") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "1") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "2") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "3") ||
                    Objects.equals(poleZPoprzedniejKolumny.getStan(), "4")) {
                break;
            }
            switch (poleZPoprzedniejKolumny.getStan()) {
                case "xp":
                    if (poleZPoprzedniejKolumny.getOswietlenie() == 1) {
                        poleZPoprzedniejKolumny.zmienStan("x");
                    } else {
                        poleZPoprzedniejKolumny.zmniejszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                    }
                    break;
                case "p":
                    if (poleZPoprzedniejKolumny.getOswietlenie() > 1) {
                        poleZPoprzedniejKolumny.zmniejszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                    } else {
                        poleZPoprzedniejKolumny.zmienStan("b");
                    }
                    break;
                case "z":
                    if (poleZPoprzedniejKolumny.getOswietlenie() == 1) {
                        poleZPoprzedniejKolumny.setForeground(Color.blue);
                        poleZPoprzedniejKolumny.zmniejszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                    } else
                        poleZPoprzedniejKolumny.zmniejszOswietlenie(poleZPoprzedniejKolumny.getOswietlenie());
                    break;

            }
        }
        for (int i = (pole.getWiersz() - 1); i >= 0; i--) {
            Pole poleZPoprzedniegoWiersza = getPole(i, (pole.getKolumna()));
            if (Objects.equals(poleZPoprzedniegoWiersza.getStan(), "c") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "0") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "1") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "2") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "3") ||
                    Objects.equals(poleZPoprzedniegoWiersza.getStan(), "4")) {
                break;
            }
            switch (poleZPoprzedniegoWiersza.getStan()) {
                case "xp":
                    if (poleZPoprzedniegoWiersza.getOswietlenie() == 1) {
                        poleZPoprzedniegoWiersza.zmienStan("x");
                    } else {
                        poleZPoprzedniegoWiersza.zmniejszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                    }
                    break;
                case "p":
                    if (poleZPoprzedniegoWiersza.getOswietlenie() > 1) {
                        poleZPoprzedniegoWiersza.zmniejszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                    } else {
                        poleZPoprzedniegoWiersza.zmienStan("b");
                    }
                    break;
                case "z":
                    if (poleZPoprzedniegoWiersza.getOswietlenie() == 1) {
                        poleZPoprzedniegoWiersza.setForeground(Color.blue);
                        poleZPoprzedniegoWiersza.zmniejszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                    } else
                        poleZPoprzedniegoWiersza.zmniejszOswietlenie(poleZPoprzedniegoWiersza.getOswietlenie());
                    break;

            }
        }
        for (int i = 1; i < (rozmiar - pole.getWiersz()); i++) {
            Pole poleZNastepengoWiersza = getPole((pole.getWiersz() + i), (pole.getKolumna()));
            if (Objects.equals(poleZNastepengoWiersza.getStan(), "c") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "0") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "1") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "2") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "3") ||
                    Objects.equals(poleZNastepengoWiersza.getStan(), "4")) {
                break;
            }
            switch (poleZNastepengoWiersza.getStan()) {
                case "xp":
                    if (poleZNastepengoWiersza.getOswietlenie() == 1) {
                        poleZNastepengoWiersza.zmienStan("x");
                    } else {
                        poleZNastepengoWiersza.zmniejszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                    }
                    break;
                case "p":
                    if (poleZNastepengoWiersza.getOswietlenie() > 1) {
                        poleZNastepengoWiersza.zmniejszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                    } else {
                        poleZNastepengoWiersza.zmienStan("b");
                    }
                    break;
                case "z":
                    if (poleZNastepengoWiersza.getOswietlenie() == 1) {
                        poleZNastepengoWiersza.setForeground(Color.blue);
                        poleZNastepengoWiersza.zmniejszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                    } else
                        poleZNastepengoWiersza.zmniejszOswietlenie(poleZNastepengoWiersza.getOswietlenie());
                    break;

            }
        }
    }
}
