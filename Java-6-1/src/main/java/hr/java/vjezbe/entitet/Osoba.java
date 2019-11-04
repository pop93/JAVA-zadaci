package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Apstraktna klasa koja predstavlja entiter osobe koju nasljeduje predmet,studenta,profesora i ispit.
 * Definirana je imenom i prezimenom
 *
 * @author Bruno
 */
public abstract class Osoba extends Entitet implements Serializable {
    private String ime;
    private String prezime;

    /**
     * @param ime     sadrzi podatke o imenu
     * @param prezime sadrzi podatke o prezimenu
     */


    public Osoba(Long id, String ime, String prezime) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}
