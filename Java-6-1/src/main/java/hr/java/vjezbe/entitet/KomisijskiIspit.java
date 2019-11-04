package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;

public class KomisijskiIspit extends Ispit  {
    Profesor clanKomisijePrvi;
    Profesor clanKomisijeDrugi;
    Profesor clanKomisijeTreci;

    public KomisijskiIspit(Long id,Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datum, Profesor clanKomisijePrvi, Profesor clanKomisijeDrugi, Profesor clanKomisijeTreci) {
        super(id,predmet, student, ocjena, datum);
        this.clanKomisijePrvi = clanKomisijePrvi;
        this.clanKomisijeDrugi = clanKomisijeDrugi;
        this.clanKomisijeTreci = clanKomisijeTreci;
    }

    public Profesor getClanKomisijePrvi() {
        return clanKomisijePrvi;
    }

    public void setClanKomisijePrvi(Profesor clanKomisijePrvi) {
        this.clanKomisijePrvi = clanKomisijePrvi;
    }

    public Profesor getClanKomisijeDrugi() {
        return clanKomisijeDrugi;
    }

    public void setClanKomisijeDrugi(Profesor clanKomisijeDrugi) {
        this.clanKomisijeDrugi = clanKomisijeDrugi;
    }

    public Profesor getClanKomisijeTreci() {
        return clanKomisijeTreci;
    }

    public void setClanKomisijeTreci(Profesor clanKomisijeTreci) {
        this.clanKomisijeTreci = clanKomisijeTreci;
    }
}
