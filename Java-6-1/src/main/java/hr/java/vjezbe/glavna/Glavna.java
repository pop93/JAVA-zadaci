package hr.java.vjezbe.glavna;

import hr.java.vjezbe.Iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.Iznimke.PostojiViseNajmladjihStudenataException;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Glavna {
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_PREDMETA = 2;
    private static final int BROJ_STUDENATA = 3;
    private static final int BROJ_ISPITA = 2;

    public static void main(String[] args) {

        List<Predmet> listaPredmeta = new ArrayList<>(BROJ_PREDMETA);
        List<Profesor> listaProfesora = new ArrayList<>(BROJ_PROFESORA);
        List<Ispit> listaIspita = new ArrayList<>(BROJ_ISPITA);
        List<Student> listaStudenata = new ArrayList<>();
        Map<Profesor, List<Predmet>> mapaProfesoraiPredmeta = new HashMap<>();



        int brojUstanova = 0;


        Scanner scanner = new Scanner(System.in);
        boolean unosBroja;
        do {
            try {
                System.out.println("Unesite broj obrazovnih ustanova:");
                brojUstanova = scanner.nextInt();
                unosBroja = false;

            } catch (Exception e) {
                System.out.println("Unesite brojčanu vrijednost!");
                logger.info("Korisnik je unio slovo kod unosa broja obrazovnih ustanova", e.getMessage(), e);
                unosBroja = true;
            }
            scanner.nextLine();

        } while (unosBroja);

        Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>();

        //List<ObrazovnaUstanova> obrazovneUstanove = new ArrayList<>();
        for (int j = 0; j < brojUstanova; j++) {

            System.out.println("Unesite podatke za " + (j + 1) + ". obrazovnu ustanovu");

            for (int i = 0; i < BROJ_PROFESORA; i++) {
                System.out.println("Unesite " + (i + 1) + ".profesora:");
                listaProfesora.add(unesiProfesora(scanner, i));

            }

            for (int i = 0; i < BROJ_PREDMETA; i++) {
                System.out.println("Unesite " + (i + 1) + ".predmet");
                listaPredmeta.add(unesiPredmet(scanner, listaProfesora, i, mapaProfesoraiPredmeta));
            }
            ispisiProfesore(mapaProfesoraiPredmeta, listaProfesora);
            for (int i = 0; i < BROJ_STUDENATA; i++) {
                System.out.println("Unesite " + (i + 1) + ".studenta:");
                listaStudenata.add(unesiStudenta(i, scanner));
            }
            for (int i = 0; i < BROJ_ISPITA; i++) {
                System.out.println("Unesite " + (i + 1) + ".ispitni rok:");
                listaIspita.add(unesiIspit(i, scanner, listaPredmeta, listaStudenata));
                ispisiPodatke(listaIspita.get(i), listaPredmeta);


            }
            ispisStudenata(listaIspita);
            System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti, 1 - Veleučilište Jave, 2 - Fakultet računarstva");
            int odabirUstanove = scanner.nextInt();
            scanner.nextLine();
            System.out.println(odabirUstanove);

            sveuciliste.dodajObrazovnuUstanovu(unesiPodatkeZaObrazovnuUstanovu(listaStudenata, scanner, listaIspita, listaProfesora, listaPredmeta, odabirUstanove));


        }
        ispisObrazovnihUstanova(sveuciliste.listaObrazovnihUstanova());
    }


    /**
     * @param scanner sadrzi podatke o objektu kojeg korisnik unosi rucno
     * @param i       sadrzi podatak o broju profesora
     * @return vraca objekt profesora koji sadrzi sve podatke o profesoru
     */
    private static Profesor unesiProfesora(Scanner scanner, int i) {
        System.out.println("Unesite sifru " + (i + 1) + ".profesora:");
        String sifraProfesora = scanner.nextLine();

        String imeProfesora;
        boolean tocnostProfesora;
        do {
            System.out.println("Unesite ime " + (i + 1) + ".profesora");
            imeProfesora = scanner.nextLine();

            if (!imeProfesora.matches("[a-zA-Z_]+")) {
                tocnostProfesora = false;
                System.out.println("Ime profesor napisite slovima");
            } else {

                tocnostProfesora = true;
            }
        }
        while (tocnostProfesora == false);
        System.out.println("Unesite prezime " + (i + 1) + ".profesora");
        String prezimeProfesora = scanner.nextLine();
        System.out.println("Unesite titulu " + (i + 1) + ".profesora");
        String titulaProfesora = scanner.nextLine();

        return new Profesor(sifraProfesora, imeProfesora, prezimeProfesora, titulaProfesora);

    }

    /**
     * @param scanner sadrzi podatke o objektu kojeg korisnik unosi rucno
     * @param i       sadrzi podatak o broju predmeta
     * @return vraca objekt predmet koji sadrzi podatke o predmetu
     */
    private static Predmet unesiPredmet(Scanner scanner, List<Profesor> listaProfesora, int i, Map<Profesor, List<Predmet>> mapaProfesoraiPredmeta) {
        System.out.println("Unesite šifru " + (i + 1) + ". predmeta:");
        String sifraPredmeta = scanner.nextLine();
        System.out.println("Unesite naziv " + (i + 1) + ". predmeta:");
        String nazivPredmeta = scanner.nextLine();
        Integer brojEctsBodova = 0;
        boolean unosBroja3;
        do {
            try {
                System.out.println("Unesite broj ECTS bodova za predmet: " + nazivPredmeta);
                brojEctsBodova = scanner.nextInt();
                scanner.nextLine();
                unosBroja3 = false;
            } catch (Exception e) {
                System.out.println("Unesite brojčanu vrijednost!");
                logger.info("Korisnik je unio slovo kod unos ECTS bodova", e.getMessage(), e);
                scanner.nextLine();
                unosBroja3 = true;
            }
        } while (unosBroja3);


        Integer odabirProfesora = 0;

        do {
            System.out.println("Odaberite profesora:");
            for (int j = 0; j < BROJ_PROFESORA; j++) {
                Profesor profesor = listaProfesora.get(j);
                System.out.println((j + 1) + "." + profesor.getIme() + " " + profesor.getPrezime());

            }
            odabirProfesora = scanner.nextInt();

        }

        while (odabirProfesora < 1 || odabirProfesora > BROJ_PROFESORA);
        scanner.nextLine();

        System.out.println("Odabir:" + odabirProfesora);
        Profesor izabraniProfesor = listaProfesora.get(odabirProfesora - 1);

        Predmet predmet = new Predmet(sifraPredmeta, nazivPredmeta, brojEctsBodova, izabraniProfesor);

        if (mapaProfesoraiPredmeta.containsKey(izabraniProfesor)) {
            List<Predmet> listaPredmeta = mapaProfesoraiPredmeta.get(izabraniProfesor);
            listaPredmeta.add(predmet);
        } else {
            List<Predmet> listaPredmeta = new ArrayList<>();
            listaPredmeta.add(predmet);
            mapaProfesoraiPredmeta.put(izabraniProfesor, listaPredmeta);
        }

        return predmet;
    }


    /**
     * @param i       sadrzi podatak o broju studenata
     * @param scanner sadrzi podatke o objektu kojeg korisnik unosi rucno
     * @return vraca objekt student koji sadrzi podatke o studentu
     */

    private static Student unesiStudenta(int i, Scanner scanner) {
        System.out.println("Unesite ime " + (i + 1) + " studenta:");
        String imeStudenta = scanner.nextLine();
        System.out.println("Unesite prezime " + (i + 1) + " studenta:");
        String prezimeStudenta = scanner.nextLine();


        boolean ponoviUnos;
        LocalDate datumRodjenja = LocalDate.now();
        do {
            try {
                System.out.println("Unesite datum rođenja studenta: " + imeStudenta + " - " + prezimeStudenta + " u formatu:(dd.MM.yyyy.):");
                String datum = scanner.nextLine();
                DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                datumRodjenja = LocalDate.parse(datum, dTF);
                ponoviUnos = false;

            } catch (DateTimeParseException iznimka) {
                System.out.println("Unesite datum u točnom formatu!");
                logger.info("Korisnik je unio krivi format datuma rodenja", iznimka.getMessage(), iznimka);
                ponoviUnos = true;
            }
        }
        while (ponoviUnos);
        System.out.println("Unesite JMBAG " + (i + 1) + "." + " studenta:");
        String jmbgStudenta = scanner.nextLine();
        return new Student(imeStudenta, prezimeStudenta, jmbgStudenta, datumRodjenja);

    }

    /**
     * @param redniBroj     sadrzi podatak o broju ispita
     * @param scanner       sadrzi podatke o objektu kojeg korisnik unosi rucno
     * @param listaPredmeta sadrzi niz objekata predmeta
     * @return vraca objekt ispit koji sadrzi podatke o ispitu
     */
    private static Ispit unesiIspit(int redniBroj, Scanner scanner, List<Predmet> listaPredmeta, List<Student> listaStudenata) {
        System.out.println("Odaberite " + (redniBroj + 1) + ". predmet:");
        for (int i = 0; i < BROJ_PREDMETA; i++) {
            System.out.println((i + 1) + ". " + listaPredmeta.get(i).getNaziv());
        }

        Integer odabirPredmeta = scanner.nextInt();
        scanner.nextLine();
        Predmet odabraniPredmet = listaPredmeta.get(odabirPredmeta - 1);
        System.out.println("Odaberite studenta:");

        for (int i = 0; i < BROJ_STUDENATA; i++) {
            Student student = listaStudenata.get(i);
            System.out.println((i + 1) + "." + student.getIme() + " " + student.getPrezime());
        }

        int odabirStudenta = scanner.nextInt();
        scanner.nextLine();
        Student pronadeniStudent = listaStudenata.get(odabirStudenta - 1);

        odabraniPredmet.getListaStudenata().add(pronadeniStudent);
        boolean unosBroja1;
        Ocjena ocjenaStudenta = null;
        do {
            try {
                System.out.println("Unesite ocjenu studenta (1-5):");
                int unosOcjene = scanner.nextInt();
                for (Ocjena ocjena : Ocjena.values()
                ) {
                    if (unosOcjene == ocjena.getOcjena()) {
                        ocjenaStudenta = ocjena;
                    }
                }
                unosBroja1 = false;
            } catch (Exception e) {
                System.out.println("Unesite brojčanu vrijednost!");
                logger.info("Korisnik je unio slovo", e.getMessage(), e);
                scanner.nextLine();
                unosBroja1 = true;
            }
        }
        while (unosBroja1);
        scanner.nextLine();
        LocalDateTime localDateTime = LocalDateTime.now();
        boolean unosDatuma;
        do {
            try {
                System.out.println("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.HH:mm):");
                String datumIspita = scanner.nextLine();
                DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
                localDateTime = LocalDateTime.parse(datumIspita, dTF);
                unosDatuma = false;
            } catch (Exception e) {
                System.out.println("Unesite točan format datuma!");
                logger.info("Korisnik je krivi format datuma");
                unosDatuma = true;
            }
        } while (unosDatuma);


        return new Ispit(odabraniPredmet, pronadeniStudent, ocjenaStudenta, localDateTime);


    }

    /**
     * @param ispit sadrzi podatke o ispitu kao objektu
     *              metoda koja ne vraca nista, ispisuje podatke o studentu, ocjeni, predmetu.
     */
    public static void ispisiPodatke(Ispit ispit, List<Predmet> listaPredmeta) {


        listaPredmeta.forEach(predmet -> {
            Set<Student> listaStudenta = predmet.getListaStudenata();
            if (listaStudenta.isEmpty()) {
                System.out.println("Nema studenata upisanih na predmet " + predmet.getNaziv());
            } else {
                List<Student> studenti = new ArrayList<>(listaStudenta);
                Collections.sort(studenti, new StudentSorter());
                System.out.println("Studenti upisani na predmet " + predmet.getNaziv() + " su ");
                for (int i = 0; i < studenti.size(); i++) {
                    System.out.println((i + 1) + " " + studenti.get(i).getPrezime() + " " + studenti.get(i).getIme());
                }
            }
        });
    }

    public static void ispisiProfesore(Map<Profesor, List<Predmet>> mapaProfesoraiPredmeta, List<Profesor> listaProfesora) {

        for (Profesor profesor : mapaProfesoraiPredmeta.keySet()
        ) {
            System.out.println("Profesor " + profesor.getIme() + " " + profesor.getPrezime() + " predaje sljedeće predmete");
            List<Predmet> listaPredmeta = mapaProfesoraiPredmeta.get(profesor);
            for (int i = 0; i < listaPredmeta.size(); i++) {
                Predmet predmet = listaPredmeta.get(i);
                System.out.println(predmet.getNaziv());
            }
        }
    }

    public static void ispisStudenata(List<Ispit> listaIspita) {
        listaIspita.stream()
                .filter(ispit -> ispit.getOcjena() == Ocjena.ODLICAN)
                .forEach(ispit -> System.out.println("Studenti koji su ostvarili ocjenu IZVRSTAN su: " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime()));
    }


    /**
     * @return vraca obrazovnu ustanovu ovisno o odabiru korisnika
     */
    public static ObrazovnaUstanova unesiPodatkeZaObrazovnuUstanovu(List<Student> listaStudenata, Scanner
            scanner, List<Ispit> listaIspita, List<Profesor> listaProfesora, List<Predmet> listaPredmeta,
                                                                    int brojUcilista) {

        System.out.println("Unesite naziv obrazovne ustanove:");
        String nazivUstanove = scanner.nextLine();
        ObrazovnaUstanova obrazovnaUstanova;
        if (brojUcilista == 1) {
            obrazovnaUstanova = new VeleucilisteJave(nazivUstanove, listaPredmeta, listaProfesora, listaStudenata, listaIspita);
        } else {
            obrazovnaUstanova = new FakultetRacunarstva(nazivUstanove, listaPredmeta, listaProfesora, listaStudenata, listaIspita);
        }
        boolean unosOcjeneZavrsnog;
        int ocjenaZavrsnog = 0;
        for (Student student : listaStudenata) {
            boolean smijeUnositi;
            try {
                ((VisokoSkolska) obrazovnaUstanova).odrediProsjekOcjenaNaIspitima(listaIspita);

                smijeUnositi = true;
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                smijeUnositi = false;
            }
            if (smijeUnositi) {
                do {

                    try {
                        System.out.println("Unesite ocjenu završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ":");
                        ocjenaZavrsnog = scanner.nextInt();
                        unosOcjeneZavrsnog = false;
                    } catch (Exception e) {
                        System.out.println("Unesite brojčanu vrijednost!");
                        logger.info("Korisnik je unio slovo", e.getMessage(), e);
                        scanner.nextLine();
                        unosOcjeneZavrsnog = true;
                    }
                }
                while (unosOcjeneZavrsnog);

                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + student.getIme() + " " + student.getPrezime() + ":");
                int ocjenaObrane = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Konacna ocjena studenta " + student.getIme() + " " + student.getPrezime() + " " +
                        ((VisokoSkolska) obrazovnaUstanova).izracunajKonacnuOcjenuStudijaZaStudenta(listaIspita, ocjenaZavrsnog, ocjenaObrane));
            }

        }
        Student najboljiStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2018);
        System.out.println("Najbolji student 2018 godine je " + najboljiStudent.getIme() + " " + najboljiStudent.getPrezime() + " JMBAG:" + najboljiStudent.getJmbag());
        if (obrazovnaUstanova instanceof Diplomski) {
            Student najboljiStudentRektor = ((FakultetRacunarstva) obrazovnaUstanova).odrediStudentaZaRektorovuNagradu();
            try {
                System.out.println("Najbolji student 2018 godine je" + najboljiStudentRektor.getIme() + " " + najboljiStudentRektor.getPrezime() + " JMBAG:" + najboljiStudentRektor.getJmbag());
            } catch (PostojiViseNajmladjihStudenataException e) {
                System.out.println("Postoji vise studenata");
            }
        }
        return obrazovnaUstanova;
    }
    public static void ispisiNajboljegStudentaObrazovneUstanove(ObrazovnaUstanova obrazovnaUstanova) {
        Student najboljiStudent = obrazovnaUstanova.odrediNajuspjesnijegStudentaNaGodini(2018);
        System.out.println("Najbolji student 2018 godine je " + najboljiStudent.getIme() + " " + najboljiStudent.getPrezime() + " JMBAG:" + najboljiStudent.getJmbag());
        if (obrazovnaUstanova instanceof Diplomski) {
            Student najboljiStudentRektor = ((FakultetRacunarstva) obrazovnaUstanova).odrediStudentaZaRektorovuNagradu();
            try {
                System.out.println("Najbolji student 2018 godine je" + najboljiStudentRektor.getIme() + " " + najboljiStudentRektor.getPrezime() + " JMBAG:" + najboljiStudentRektor.getJmbag());
            } catch (Exception e) {
                System.out.println("Postoji vise studenata");
            }
        }
    }

    public static void ispisObrazovnihUstanova(List<ObrazovnaUstanova> listaObrazovnihUstanova) {
        listaObrazovnihUstanova.stream().sorted((o1, o2) -> {
            int brojStudenataPrve = o1.getListaStudenata().size();
            int brojStudenataDruge = o2.getListaStudenata().size();
            if (brojStudenataPrve > brojStudenataDruge) {
                return 1;
            } else if (brojStudenataPrve < brojStudenataDruge) {
                return -1;

            } else {
                return o1.getUstanova().compareToIgnoreCase(o2.getUstanova());
            }
        }).forEach(ustanova -> {
            System.out.println(ustanova.getUstanova() + " ima " + ustanova.getListaStudenata().size() + " studenata");
        });


    }

}


