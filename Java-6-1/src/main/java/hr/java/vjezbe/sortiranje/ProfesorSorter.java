package hr.java.vjezbe.sortiranje;


import hr.java.vjezbe.entitet.Profesor;

import java.util.Comparator;

public class ProfesorSorter implements Comparator<Profesor> {

    @Override
    public int compare(Profesor o1, Profesor o2) {
        int prezimeRezultat = o1.getPrezime().compareToIgnoreCase(o2.getPrezime());
        if (prezimeRezultat == 0) {
            return o1.getIme().compareToIgnoreCase(o2.getIme());
        } else {
            return prezimeRezultat;
        }
    }
}