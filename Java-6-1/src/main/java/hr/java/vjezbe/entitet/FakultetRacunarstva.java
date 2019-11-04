package hr.java.vjezbe.entitet;

import hr.java.vjezbe.Iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.Iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;


/**
 * Predstavlja entitet fakultet racunarstva,
 * nasljeduje nadklasu Obrazovna ustanova i implementira
 * sucelje Diplomski. Također prima sve parametere od nadklase obrazovna ustanova
 *
 * @author Bruno
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {
    private static final Logger logger = LoggerFactory.getLogger(FakultetRacunarstva.class);

    /**
     * @param ustanova naziv podatak ustanove o nazivu  Obrazovne ustanove
     *                 <p>
     *                 takoder preuzima sve parametre obrazovne ustanove putem izraza super
     */
    public FakultetRacunarstva(String ustanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenata,
                               List<Ispit> listaIspita) {
        super(ustanova, listaPredmeta, listaProfesora, listaStudenata, listaIspita);
    }

    public FakultetRacunarstva(Long id, String ustanova, List<Predmet> listaPredmeta, List<Profesor> listaProfesora, List<Student> listaStudenata,
                               List<Ispit> listaIspita) {
        super(id, ustanova, listaPredmeta, listaProfesora, listaStudenata, listaIspita);
    }


    /**
     * sluzi za izracun konacne ocjene studenta na studiju
     *
     * @param ocjenaDiplomskog       podatak o ocjeni diplomskog rada
     * @param ocjenaObraneDiplomskog podatak o ocjeni obrane diplomskog rada
     * @return vraca konacnu ocjenu studenta za studij u obliku big decimala
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> listaIspita, int ocjenaDiplomskog, int ocjenaObraneDiplomskog) {

        int ukupneOcjene = 0;

        for (Ispit ispit : listaIspita
        ) {
            ukupneOcjene += ispit.getOcjena().getOcjena();
        }
        double prosjekOcjena = ukupneOcjene / (double) listaIspita.size();
        double konačnaOcjena = ((3 * prosjekOcjena + ocjenaDiplomskog + ocjenaDiplomskog) / 5);

        return new BigDecimal(konačnaOcjena);

    }

    /**
     * sluzi za odredivanje najuspjesnijeg studenta na godini
     *
     * @param broj oznacava ocjenu na godini
     * @return vraca objekt naboljeg studenta na godini
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int broj) {

        Student najboljiStudent=null;
        int najvise = 0;
        for (int i = 0; i < listaIspita.size(); i++) {
            Student student= listaStudenata.get(i);
            List<Ispit> filtriraniIspiti = filtrirajIspitePoStudentu(listaIspita,student);
            int brojIzvrsnih = prebrojiIzvrsne(filtriraniIspiti);
            if (brojIzvrsnih > najvise) {
               najboljiStudent=student;
                najvise = brojIzvrsnih;
            }

        }
        return najboljiStudent;
    }

    /**
     * sluzi za brojanje izvrsnih ispita
     *
     * @return vraca koliko ispita ima sa izvrsnim ocjenama
     */
    public int prebrojiIzvrsne(List<Ispit> listaIspita) {
        int brojacIspita = 0;
        for (Ispit ispiti : listaIspita
        ) {
            if (ispiti.getOcjena() == Ocjena.ODLICAN) {
                brojacIspita++;
            }
        }
        return brojacIspita;
    }

    /**
     * sluzi za odredivanje studenta za rektorovu nagradu
     *
     * @return vraca objekt studenta koji je osvojio rektorovu nagradu
     */
    @Override
    public Student odrediStudentaZaRektorovuNagradu() {
        Student najboljiStudent = listaStudenata.get(0);
        BigDecimal najboljiProsjek = new BigDecimal(0.0d);

        for (Student student : listaStudenata) {
            List<Ispit> filtriraniIspiti = filtrirajIspitePoStudentu(listaIspita, student);
            try {
                BigDecimal prosjek = odrediProsjekOcjenaNaIspitima(filtriraniIspiti);
                if (prosjek.doubleValue() > najboljiProsjek.doubleValue()) {
                    najboljiProsjek = prosjek;
                    najboljiStudent = student;
                } else if (prosjek.doubleValue() == najboljiProsjek.doubleValue()) {
                    if (najboljiStudent.getDatumRodjenja().isEqual(student.getDatumRodjenja())) {
                        logger.info("Pronađeno je više najmlađih studenata s istim datumom rođenja");
                        throw new PostojiViseNajmladjihStudenataException("Pronađeno je više najmlađih studenata s istim datumom rođenja, a to su " + student.getIme() + " " + najboljiStudent.getIme());


                    } else if (najboljiStudent.getDatumRodjenja().isAfter(student.getDatumRodjenja())) {
                        najboljiProsjek = prosjek;
                        najboljiStudent = student;
                    }
                }
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                System.out.println("Student" + student.getIme() + " " + student.getPrezime() + "zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!)");
            }

        }

        return najboljiStudent;

    }
}
