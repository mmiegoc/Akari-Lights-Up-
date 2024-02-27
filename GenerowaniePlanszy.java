package pl.edu.pw.elka.prm2t.akari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class GenerowaniePlanszy {

    private int rozmiar;
    private final Pole[][] plansza;
    private String[][] wybranaPlansza;

    private final static String[][] PLANSZA_DO_GENERACJI = new String[][]{
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
            {"b", "b", "b", "b", "b", "b", "b", "b", "b", "b"},
    };

    public GenerowaniePlanszy(int poziom) {
        if (poziom == 0) {
            this.rozmiar = 10;
            wybranaPlansza = PLANSZA_DO_GENERACJI;
        }
        plansza = new Pole[rozmiar][rozmiar];

    }


    public JPanel generujPlansze() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rozmiar, rozmiar, 1, 1));

        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                plansza[i][j] = new Pole(i, j, wybranaPlansza[i][j]);
                addActionListener(plansza[i][j]);
                panel.add(plansza[i][j]);
            }
        }
        return panel;

    }


    void addActionListener(Pole pole) {

        pole.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (Objects.equals(pole.getStan(), "b")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("c");
                    }
                } else if (Objects.equals(pole.getStan(), "c")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("0");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("b");
                    }
                } else if (Objects.equals(pole.getStan(), "0")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("1");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("c");
                    }
                } else if (Objects.equals(pole.getStan(), "1")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("2");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("0");
                    }
                } else if (Objects.equals(pole.getStan(), "2")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("3");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("1");
                    }
                } else if (Objects.equals(pole.getStan(), "3")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("4");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("2");
                    }
                } else if (Objects.equals(pole.getStan(), "4")) {
                    if (e.getButton() == MouseEvent.BUTTON1  /*lewy*/) {
                        pole.zmienStan("b");
                    } else if (e.getButton() == MouseEvent.BUTTON3  /*prawy*/) {
                        pole.zmienStan("3");
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


}
