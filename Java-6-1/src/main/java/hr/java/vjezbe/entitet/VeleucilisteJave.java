package hr.java.vjezbe.entitet;


import hr.java.vjezbe.Iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;


/**
 * Predstavlja entitet Veleucilista Jave ,
 * nasljeduje nadklasu Obrazovna ustanova i implementira
 * sucelje Visokoskolska. Također prima sve parametere od nadklase
 *
 * @author Bruno
 */

public class VeleucilisteJave extends ObrazovnaUstanova  implements VisokoSkolska {
    private static final Logger logger = LoggerFactory.getLogger(VeleucilisteJave.class);



    public VeleucilisteJave(String ustanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenata,
                            List<Ispit> listaIspita) {
        super(ustanova, listaPredmeta, listaProfesora, listaStudenata, listaIspita);
    }

    public VeleucilisteJave(Long id, String ustanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenata,
                            List<Ispit> listaIspita) {
        super(id, ustanova, listaPredmeta, listaProfesora, listaStudenata, listaIspita);
    }

    /**
     * služi za izracun konacne ocjene studija za studenta
     *

     * @param ocjenaZavrsnog sadrzi ocjenu zavrsnog ispita
     * @param ocjenaObrane   sadrzi ocjenu obrane ispita
     * @return vraća konačnu ocjenu studenta na studiju
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> listaIspita, int ocjenaZavrsnog, int ocjenaObrane) {
        int ukupneOcjene = 0;
        double prosjekOcjena = 0;
        for (Ispit ispit : listaIspita
        ) {
            if (ispit.getOcjena() == Ocjena.NEDOVOLJAN) {
                return new BigDecimal(1.0d);
            } else {
                ukupneOcjene += ispit.getOcjena().getOcjena();
            }
        }
        prosjekOcjena = ukupneOcjene / (double) listaIspita.size();
        double konačnaOcjena = ((2 * prosjekOcjena + ocjenaZavrsnog + ocjenaObrane) / 4);

        return new BigDecimal(konačnaOcjena);
    }


    /**
     * služi za odredivanje najuspjesnijeg studenta na godini
     *
     * @param godina sadrzi godinu studija
     * @return vraca rezultat pomocu lambda izraza na nacin da prvog studenta u polju sortira kao najboljeg
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        Collections.sort(listaStudenata, (student1, student2) -> {
            filtrirajIspitePoStudentu2(listaIspita,student1);
            List<Ispit> listapoStudentu1 = filtrirajIspitePoStudentu(listaIspita, student1);

            List<Ispit> listapoStudentu2 = filtrirajIspitePoStudentu(listaIspita, student2);


            BigDecimal student1Prosjek;
            BigDecimal student2Prosjek;
            try {
                student1Prosjek = odrediProsjekOcjenaNaIspitima(listapoStudentu1);
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                System.out.println("Student" + student1.getIme() + " " + student1.getPrezime() + "zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!“. ");
                logger.info("Student je ostvario ocjenu 1", e.getMessage(), e);
                student1Prosjek = new BigDecimal(1.0d);
            }
            try {
                student2Prosjek = odrediProsjekOcjenaNaIspitima(listapoStudentu2);
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                System.out.println("Student" + student2.getIme() + " " + student2.getPrezime() + "zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!“. ");
                logger.info("Student je ostvario ocjenu 1", e.getMessage(), e);
                student2Prosjek = new BigDecimal(1.0d);
            }


            int rezultat;
            if (student1Prosjek.doubleValue() < student2Prosjek.doubleValue()) {
                rezultat = -1;
            } else if (student1Prosjek.doubleValue() == student2Prosjek.doubleValue()) {
                rezultat = 0;
            } else {
                rezultat = 1;
            }

            return rezultat;

        });

        return listaStudenata.get(listaStudenata.size() - 1);
    }

}
