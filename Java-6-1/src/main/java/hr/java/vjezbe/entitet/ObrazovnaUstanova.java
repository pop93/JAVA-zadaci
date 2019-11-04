package hr.java.vjezbe.entitet;


import java.io.Serializable;
import java.util.List;


/**
 * Apstraktna klasa koja predstavlja entiter Obrazovne ustanove koju nasljeduje razne vrste
 * ustanova.Definirana je ustanovom, nizom predmeta, nizom profesora,nizom studenta,nizom ispita
 *
 * @author Bruno
 */
public abstract class ObrazovnaUstanova extends Entitet implements Serializable {
    String ustanova;
    List<Predmet> listaPredmeta;
    List<Profesor> listaProfesora;
    List<Student> listaStudenata;
    List<Ispit> listaIspita;

    public ObrazovnaUstanova(String ustanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenata, List<Ispit> listaIspita) {
        super(0L);
        this.ustanova = ustanova;
        this.listaPredmeta = listaPredmeta;
        this.listaProfesora = listaProfesora;
        this.listaStudenata = listaStudenata;
        this.listaIspita = listaIspita;
    }
    public ObrazovnaUstanova(Long id, String ustanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenata, List<Ispit> listaIspita) {
        super(id);
        this.ustanova = ustanova;
        this.listaPredmeta = listaPredmeta;
        this.listaProfesora = listaProfesora;
        this.listaStudenata = listaStudenata;
        this.listaIspita = listaIspita;
    }

    public String getUstanova() {
        return ustanova;
    }

    public void setUstanova(String ustanova) {
        this.ustanova = ustanova;
    }

    public List<Predmet> getListaPredmeta() {
        return listaPredmeta;
    }

    public void setListaPredmeta(List<Predmet> listaPredmeta) {
        this.listaPredmeta = listaPredmeta;
    }

    public List<Profesor> getListaProfesora() {
        return listaProfesora;
    }

    public void setListaProfesora(List<Profesor> listaProfesora) {
        this.listaProfesora = listaProfesora;
    }

    public List<Student> getListaStudenata() {
        return listaStudenata;
    }

    public void setListaStudenata(List<Student> listaStudenata) {
        this.listaStudenata = listaStudenata;
    }

    public List<Ispit> getListaIspita() {
        return listaIspita;
    }

    public void setListaIspita(List<Ispit> listaIspita) {
        this.listaIspita = listaIspita;
    }

    /**
     * @param godina trenutna godina studenta na godini
     * @return Vraca studenta koji je najuspjesniji na godini
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);

}
