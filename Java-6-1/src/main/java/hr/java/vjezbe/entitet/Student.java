package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * predstavlja entitet studenta,
 * nasljeduje nadklasu osoba i predstavljen je jmbag-om i datumom rodjenja
 *
 * @author Bruno
 */
public class Student extends Osoba implements Serializable{
    private String jmbag;
    private LocalDate datumRodjenja;
    private boolean prosao;

    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja, boolean prosao) {
        super(id, ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
        this.prosao = prosao;

    }

    public boolean isProsao() {
        return prosao;
    }

    public void setProsao(boolean prosao) {
        this.prosao = prosao;
    }



    /**
     * @param ime           sadrzi podatke o imenu studenta
     * @param prezime       sadrzi podatke o prezimenu studenta
     * @param jmbag         sadrzi podatke o jmbag-u studenta
     * @param datumRodjenja sadrzi podatke o datumu rodjenja studenta
     */
    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(0L,ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;

    }
    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(id,ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;

    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return jmbag.equals(student.jmbag) &&
                datumRodjenja.equals(student.datumRodjenja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag, datumRodjenja);
    }
}
