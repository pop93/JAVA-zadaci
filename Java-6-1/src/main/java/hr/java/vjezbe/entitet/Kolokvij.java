package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

public class Kolokvij extends Ispit {
    Integer bodovi;
    Predmet nazivKolokvija;
    Profesor nositeljKolegija;
    Profesor asistent;



    public Kolokvij(Long id, Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datum, Integer bodovi, Predmet nazivKolokvija, Profesor nositeljKolegija, Profesor asistent) {
        super(id, predmet, student, ocjena, datum);
        this.bodovi = bodovi;
        this.nazivKolokvija = nazivKolokvija;
        this.nositeljKolegija = nositeljKolegija;
        this.asistent = asistent;
    }

    public Integer getBodovi() {
        return bodovi;
    }

    public void setBodovi(Integer bodovi) {
        this.bodovi = bodovi;
    }

    public Predmet getNazivKolokvija() {
        return nazivKolokvija;
    }

    public void setNazivKolokvija(Predmet nazivKolokvija) {
        this.nazivKolokvija = nazivKolokvija;
    }

    public Profesor getNositeljKolegija() {
        return nositeljKolegija;
    }

    public void setNositeljKolegija(Profesor nositeljKolegija) {
        this.nositeljKolegija = nositeljKolegija;
    }

    public Profesor getAsistent() {
        return asistent;
    }

    public void setAsistent(Profesor asistent) {
        this.asistent = asistent;
    }
}
