package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 *predstavlja entitet profesora,
 * nasljeduje nadklasu osoba i predstavljen je sifrom i titulom
 * @author Bruno
 */
public class Profesor extends Osoba implements Serializable{
    private String sifra;
    private String titula;


    /**
     * @param sifra sadrzi podatke o sifri profesora
     * @param ime  sadrzi podatke o imenu profesora
     * @param prezime sadrzi podatke o prezimenu profesora
     * @param titula sadrzi podatke o tituli profesora
     */
    public Profesor(String sifra, String ime, String prezime, String titula) {
        super(0L,ime, prezime);
        this.sifra = sifra;
        this.titula = titula;

    }
    public Profesor(Long id, String sifra, String ime, String prezime, String titula) {
        super(id,ime, prezime);
        this.sifra = sifra;
        this.titula = titula;

    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }


}
