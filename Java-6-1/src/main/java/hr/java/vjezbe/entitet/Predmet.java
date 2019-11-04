package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * klasa koja predstavlja entitet predmeta
 * definirana je sifrom,nazivom,brojem ects bodova,nositeljom i nizom objekata studenata.
 * @author Bruno
 */
public class Predmet extends Entitet implements Serializable  {
    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Set<Student> listaStudenata;

    /**
     * @param sifra sadrzi podatke o sifri.
     * @param naziv sadrzi podatke o nazivu.
     * @param brojEctsBodova sadrzi podatke o ects bodovima.
     * @param nositelj sadrzi podatke o nositelju.
     */
    public Predmet(String sifra, String naziv, Integer brojEctsBodova,Profesor nositelj) {
        super(0L);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.listaStudenata = new HashSet<>();
    }
    public Predmet (Long id, String sifra, String naziv, Integer brojEctsBodova,Profesor nositelj) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.listaStudenata = new HashSet<>();

    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public Set<Student> getListaStudenata() {
        return listaStudenata;
    }

    public void setListaStudenata(Set<Student> listaStudenata) {
        this.listaStudenata = listaStudenata;
    }
}
