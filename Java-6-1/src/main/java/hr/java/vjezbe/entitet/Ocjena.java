package hr.java.vjezbe.entitet;

public enum Ocjena {
    NEDOVOLJAN("Nedovoljan", 1),
    DOVOLJAN("Dovoljan", 2),
    DOBAR("Dobar", 3),
    VRLO_DOBAR("Vrlo_Dobar", 4),
    ODLICAN("Odlican", 5);

    private String opis;
    private int ocjena;

    Ocjena(String opis, int ocjena) {
        this.opis = opis;
        this.ocjena = ocjena;
    }

    public String getOpis() {
        return opis;
    }

    public int getOcjena() {
        return ocjena;
    }

}
