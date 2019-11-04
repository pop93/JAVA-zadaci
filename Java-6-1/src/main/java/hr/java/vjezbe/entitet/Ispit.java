package hr.java.vjezbe.entitet;


import java.io.Serializable;
import java.time.LocalDateTime;

/**predstavlja entitet Ispita
 * predstavljen predmetom,studentom,ocjenom i datumom
 * @author Bruno
 */
public class Ispit extends Entitet implements Serializable{
    private Predmet predmet;
    private Student student;
    private Ocjena ocjena;
    private LocalDateTime datum;

    /**
     * @param predmet sadrzi podatke o objektu u kojem se nalazi predmet
     * @param student sadrzi podatke o objektu u kojem se nalazi student
     * @param ocjena sadrzi podatak o ocjeni ispita
     * @param datum sadrzi podatak o datumu ispita
     */
    public Ispit(Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datum) {
        super(0L);
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datum = datum;
    }
    public Ispit(Long id, Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datum) {
        super(id);
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datum = datum;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Ocjena ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }
}
