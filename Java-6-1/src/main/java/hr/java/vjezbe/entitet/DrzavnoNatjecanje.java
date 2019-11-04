package hr.java.vjezbe.entitet;

import java.util.List;
import java.util.Set;

public class DrzavnoNatjecanje {
    private Long id;
    private String naziv;
    private List<Student> listaStudenata;

    public DrzavnoNatjecanje(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<Student> getListaStudenata() {
        return listaStudenata;
    }

    public void setListaStudenata(List<Student> listaStudenata) {
        this.listaStudenata = listaStudenata;
    }
}
