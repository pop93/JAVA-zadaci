package hr.java.vjezbe.entitet;


import hr.java.vjezbe.Iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Predstavlja sucelje visokoskolske
 *
 * @author Bruno
 */
public interface VisokoSkolska {
    /**
     * @param ocjenaZavrsnog sadrzi podatak o ocjeni zavrsnog ispita
     * @param ocjenaObrane   sadrzi podatak o ocjeni obrane
     * @return vraca konacnu ocjenu studenta za studij u obliku big decimala
     */
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> listaIspita, int ocjenaZavrsnog, int ocjenaObrane);


    /**
     * @return defaultna metoda koja vraca prosjek ocjena na ispitima u obliku Big decimala
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> listaIspita) throws NemoguceOdreditiProsjekStudentaException {
        if (listaIspita.size() == 0) {
            return BigDecimal.valueOf(0);
        }
        int zbrojOcjena = 0;
        for (Ispit ispit : listaIspita) {
            if (ispit.getOcjena() == Ocjena.NEDOVOLJAN) {
                throw new NemoguceOdreditiProsjekStudentaException("Unesena je ocjena 1!!!");
            } else {
                zbrojOcjena += ispit.getOcjena().getOcjena();
            }
        }
        float rezultat = zbrojOcjena / (float) listaIspita.size();
        return BigDecimal.valueOf(rezultat);

    }

    /**
     * @return vraca podatke o filtriranim polozenim ispitima
     */

    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> listaIspita) {
        List<Ispit> novaListaIspita = new ArrayList<>();
        for (Ispit ispit : listaIspita) {
            if (ispit.getOcjena() != Ocjena.NEDOVOLJAN) {
                novaListaIspita.add(ispit);
            }
        }

        return novaListaIspita;
    }


    /**
     * @param student sadrzi objekt studenta sa podacima
     * @return vraca filtirane ispite po studentu
     */
    default List<Ispit> filtrirajIspitePoStudentu2(List<Ispit> listaIspita, Student student) {

        List<Ispit> filtriraniIspitiPoStudentu = new ArrayList<>();

        for (Ispit ispit : listaIspita) {
            if (student.equals(ispit.getStudent())) {
                filtriraniIspitiPoStudentu.add(ispit);
            }
        }


        return filtriraniIspitiPoStudentu;
    }

    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> listaIspita, Student student) {
        List<Ispit> filitriraniIspitiPoStudentu2 = listaIspita.stream()
                .filter(ispit -> ispit.getStudent().equals(student))
                .collect(toList());
        return filitriraniIspitiPoStudentu2;
    }

}