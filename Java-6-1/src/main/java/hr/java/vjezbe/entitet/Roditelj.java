package hr.java.vjezbe.entitet;

import java.io.Serializable;

public class Roditelj  extends  Osoba implements Serializable {

    private String oib;
    private String adresa;


    public Roditelj(Long id,String oib, String adresa,String ime,String prezime) {
        super(id,ime,prezime);
        this.oib = oib;
        this.adresa = adresa;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}

