package hr.java.vjezbe.glavna;


import hr.java.vjezbe.entitet.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class GlavnaDatoteke {

    private static final String FILE_PROFESORI = "dat\\profesori.txt";
    private static final String FILE_STUDENTI = "dat\\studenti.txt";
    private static final String FILE_PREDMETI = "dat\\predmeti.txt";
    private static final String FILE_ISPITI = "dat\\ispiti.txt";
    private static final String FILE_RODITELJI = "dat\\roditelji.txt";
    private static final String FILE_USTANOVE = "dat\\obrazovne_ustanove.txt";
    private static final String FILE_KOMISIJSKI_ISPITI = "dat\\komisijskiIspit.txt";
    private static final String FILE_KOLOKVIJI = "dat\\kolokviji.txt";
    private static final String FILE_DRŽAVNO_NATJECANJE = "dat\\drzavnoNatjecanje.txt";


    private static List<Profesor> listaProfesora;
    private static List<Student> listaStudenata;
    private static List<Predmet> listaPredmeta;
    private static List<Ispit> listaIspita;
    private static List<Roditelj> listaRoditelja;
    private static List<ObrazovnaUstanova> listaUstanova;
    private static Map<Profesor, List<Predmet>> mapaProfesoraiPredmeta = new HashMap<>();
    private static List<KomisijskiIspit> listaKomisijskihIspita;
    private static List<Kolokvij> listaKolokvija;
    private static List<DrzavnoNatjecanje> listaDrzavnihNatjecanja;


    public static void main(String[] args) {
        listaProfesora = dohvatiProfesore();
        listaStudenata = dohvatiStudente();
        listaPredmeta = dohvatiPredmete();
        listaIspita = dohvatiIspite();
        listaKomisijskihIspita = dohvatiKomisijskeIspite();
        listaUstanova = dohvatiUstanove();
        listaKolokvija = dohvatiKolokvije();
        listaDrzavnihNatjecanja = dohvatiDrzavnaNatjecanja();


        sloziMapuProfesoraPredmeta();
        //Glavna.ispisiProfesore(mapaProfesoraiPredmeta, listaProfesora);
        // Glavna.ispisiPodatke(null, listaPredmeta);
        //Glavna.ispisStudenata(listaIspita);
        //Glavna.ispisObrazovnihUstanova(listaUstanova);
        ispisiNajboljeStudenteUstanova();
        ispisPodatakaZaKomisijskiIspit(listaKomisijskihIspita, listaIspita);
        serijalizacija(listaUstanova);
        ispisiStudenteKojiImajuPozitivnuOcjenu(listaKomisijskihIspita);
        ispisiProfesoreKojiImajuTituleJavaProfesora(listaProfesora);
        ispisiKolokvijeiBodove(listaKolokvija);
        ispisiStudenteSaDržavnihNatjecanja(listaDrzavnihNatjecanja);

    }


    public static void ispisPodatakaZaKomisijskiIspit(List<KomisijskiIspit> listaKomisijskihIspita, List<Ispit> listaIspita) {


        for (KomisijskiIspit komisijskiIspit : listaKomisijskihIspita
        ) {

            System.out.println("ovo je ispis komisijskih ispita:");
            System.out.println("Datum ispita: " + komisijskiIspit.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            System.out.println("Prvi član komisij:" + komisijskiIspit.getClanKomisijePrvi().getPrezime() + " " + komisijskiIspit.getClanKomisijePrvi().getIme());
            System.out.println("Drugi član komisij:" + komisijskiIspit.getClanKomisijeDrugi().getPrezime() + " " + komisijskiIspit.getClanKomisijeDrugi().getIme());
            System.out.println("Treći član komisije:" + komisijskiIspit.getClanKomisijeTreci().getPrezime() + " " + komisijskiIspit.getClanKomisijeTreci().getIme());
            System.out.println("Student:" + komisijskiIspit.getStudent().getIme() + " " + komisijskiIspit.getStudent().getPrezime());

            System.out.println("Na ispit je izašao :" + listaIspita.stream()
                    .filter(ispit -> ispit.getStudent().getId().equals(komisijskiIspit.getStudent().getId())).count() + "puta");

            Long count = listaIspita.stream()
                    .filter(ispit -> ispit.getStudent().getId().equals(komisijskiIspit.getStudent().getId())).count();
            int broj = 1;
            for (Ispit ispit : listaIspita
            ) {
                if (listaIspita.size() == count) {
                    System.out.println("Lista ispita sadrzi" + ispit.getPredmet().getNaziv());
                } else System.out.println(broj + "ispisi nesto" + ispit.getPredmet().getNaziv());
                broj++;
            }
        }
    }

    public static void ispisiStudenteSaDržavnihNatjecanja(List<DrzavnoNatjecanje> listaDrzavnihNatjecanja) {


        for (DrzavnoNatjecanje drzavnoNatjecanje : listaDrzavnihNatjecanja
        ) {

            List<Student> listaStudenata;
            listaStudenata=drzavnoNatjecanje.getListaStudenata();
            for (Student student: listaStudenata ){
            System.out.println("Ovo su podaci studenata drzavnih Natjecanja koji imaju prolaznu ocjenu: predmet:" + drzavnoNatjecanje.getNaziv() + " "
                    + ", ime studenta: " +student.getIme() + " "+ student.getPrezime()+".");
            }
            }
        }


    public static void ispisiStudenteKojiImajuPozitivnuOcjenu(List<KomisijskiIspit> listaKomisijskihIspita) {
        List<KomisijskiIspit> listaIspitaFiltrirana = new ArrayList<>();
        listaKomisijskihIspita.stream().filter(komisijskiIspit -> komisijskiIspit.getOcjena() == Ocjena.NEDOVOLJAN)
                .forEach(m -> listaIspitaFiltrirana.add(m));

        for (Ispit ispit : listaIspitaFiltrirana
        ) {
            System.out.println("Studenti koji imaju ocjenu nedovoljan su: " + ispit.getStudent().getIme() + " " + ispit.getStudent().getPrezime() + " iz predmeta " + ispit.getPredmet().getNaziv());
        }
    }

    public static void ispisiProfesoreKojiImajuTituleJavaProfesora(List<Profesor> listaProfesora) {
        List<Profesor> listaprofesoraNova = new ArrayList<>();

        listaProfesora.stream().filter(ispit -> ispit.getTitula().contains("Java")).forEach(m -> listaprofesoraNova.add(m));
        for (Profesor profesori : listaprofesoraNova
        ) {
            System.out.println("Profesori koji imaju status profesora Java jesu:" + profesori.getIme() + " " + profesori.getPrezime());
        }
    }

    public static void ispisiKolokvijeiBodove(List<Kolokvij> listaKolokvija) {
        for (Kolokvij kolokvij : listaKolokvija
        ) {
            System.out.println("Predmet " + kolokvij.getNazivKolokvija().getNaziv() + " ima " + kolokvij.getBodovi() + " bodova gdje predaje " +
                    kolokvij.getNositeljKolegija().getIme() + " " + kolokvij.getNositeljKolegija().getPrezime());
        }


    }


    public static LocalDate ucitajDatum(String datum) {
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return LocalDate.parse(datum, dTF);
    }

    public static LocalDateTime ucitajVrijeme(String datum) {
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
        return LocalDateTime.parse(datum, dTF);
    }

    public static List<Kolokvij> dohvatiKolokvije() {
        System.out.println("Učitavanje Kolokvija…");
        List<String> listaLinija = new ArrayList<>();
        List<Kolokvij> listaKolokvija = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_KOLOKVIJI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 9) {
                Kolokvij kolokvij = new Kolokvij(
                        Long.parseLong(listaLinija.get(i)),
                        getPredmet(Long.parseLong(listaLinija.get(i + 1))),
                        getStudent(Long.parseLong(listaLinija.get(i + 2))),
                        getOcjena(Integer.parseInt(listaLinija.get(i + 3))),
                        ucitajVrijeme(listaLinija.get(i + 4)),
                        Integer.parseInt(listaLinija.get(i + 5)),
                        getPredmet(Long.parseLong((listaLinija.get(i + 6)))),
                        getProfesor(Long.parseLong((listaLinija.get(i + 7)))),
                        getProfesor(Long.parseLong((listaLinija.get(i + 8))))

                );
                listaKolokvija.add(kolokvij);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaKolokvija;

    }


    public static List<Profesor> dohvatiProfesore() {
        System.out.println("Učitavanje profesora…");
        List<String> listaLinija = new ArrayList<>();
        List<Profesor> listaProfesora = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_PROFESORI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 5) {
                Profesor profesor = new Profesor(
                        Long.parseLong(listaLinija.get(i)),
                        listaLinija.get(i + 1),
                        listaLinija.get(i + 2),
                        listaLinija.get(i + 3),
                        listaLinija.get(i + 4)
                );
                listaProfesora.add(profesor);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaProfesora;
    }


    public static List<KomisijskiIspit> dohvatiKomisijskeIspite() {
        System.out.println("Učitavanje komisijskih ispita…");
        List<String> listaLinija = new ArrayList<>();
        List<KomisijskiIspit> listaKomisijskihIspita = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_KOMISIJSKI_ISPITI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 8) {
                KomisijskiIspit komisijskiIspit = new KomisijskiIspit(
                        Long.parseLong(listaLinija.get(i)),
                        getPredmet(Long.parseLong(listaLinija.get(i + 1))),
                        getStudent(Long.parseLong(listaLinija.get(i + 2))),
                        getOcjena(Integer.parseInt(listaLinija.get(i + 3))),
                        ucitajVrijeme(listaLinija.get(i + 4)),
                        getProfesor(Long.parseLong((listaLinija.get(i + 5)))),
                        getProfesor(Long.parseLong((listaLinija.get(i + 6)))),
                        getProfesor(Long.parseLong((listaLinija.get(i + 7))))


                );
                listaKomisijskihIspita.add(komisijskiIspit);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaKomisijskihIspita;
    }


    public static List<Roditelj> dohvatiRoditelje() {
        System.out.println("Učitavanje roditelja…");
        List<String> listaLinija = new ArrayList<>();
        List<Roditelj> listaRoditelja = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_RODITELJI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 5) {
                Roditelj roditelj = new Roditelj(
                        Long.parseLong(listaLinija.get(i)),
                        listaLinija.get(i + 1),
                        listaLinija.get(i + 2),
                        listaLinija.get(i + 3),
                        listaLinija.get(i + 4)


                );
                listaRoditelja.add(roditelj);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaRoditelja;
    }


    public static List<Student> dohvatiStudente() {
        System.out.println("Učitavanje studenata…");
        List<String> listaLinija = new ArrayList<>();
        List<Student> listaStudenata = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_STUDENTI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 6) {
                Student student = new Student(
                        Long.parseLong(listaLinija.get(i)),
                        listaLinija.get(i + 1),
                        listaLinija.get(i + 2),
                        listaLinija.get(i + 3),
                        ucitajDatum(listaLinija.get(i + 4))
                );

                        student.setProsao(Boolean.parseBoolean(listaLinija.get(i + 5)));
                listaStudenata.add(student);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaStudenata;

    }

    public static List<DrzavnoNatjecanje> dohvatiDrzavnaNatjecanja() {
        System.out.println("Učitavanje drzavnih natjecanja…");
        List<String> listaLinija = new ArrayList<>();
        List<DrzavnoNatjecanje> listaDrzavnihNatjecanja = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_DRŽAVNO_NATJECANJE))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 3) {
                DrzavnoNatjecanje drzavnoNatjecanje = new DrzavnoNatjecanje(
                        Long.parseLong(listaLinija.get(i)),
                        listaLinija.get(i + 1)
                );

                String linijaStudenata = listaLinija.get(i + 2);

                String[] idevi = linijaStudenata.split(" ");

                List<Student> filtriranaLista= new ArrayList<>();
                for (int j = 0; j < idevi.length; j++) {
                    Student student = getStudent(Long.parseLong(idevi[j]));
                    if (student.isProsao()) {
                        filtriranaLista.add(student);
                    }
                }


                drzavnoNatjecanje.setListaStudenata(filtriranaLista);

                listaDrzavnihNatjecanja.add(drzavnoNatjecanje);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaDrzavnihNatjecanja;

    }

    public static List<Predmet> dohvatiPredmete() {
        System.out.println("Učitavanje predmeta…");
        List<String> listaLinija = new ArrayList<>();
        List<Predmet> listaPredmeta = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_PREDMETI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 6) {
                Predmet predmet = new Predmet(
                        Long.parseLong(listaLinija.get(i)),
                        listaLinija.get(i + 1),
                        listaLinija.get(i + 2),
                        Integer.parseInt(listaLinija.get(i + 3)),
                        getProfesor(Long.parseLong(listaLinija.get(i + 4)))
                );

                String linijaStudenata = listaLinija.get(i + 5);
                String[] idevi = linijaStudenata.split(" ");

                Set<Student> studenti = new HashSet<>();
                for (int j = 0; j < idevi.length; j++) {
                    Student student = getStudent(Long.parseLong(idevi[j]));
                    studenti.add(student);
                }

                predmet.setListaStudenata(studenti);

                listaPredmeta.add(predmet);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaPredmeta;

    }

    public static List<Ispit> dohvatiIspite() {
        System.out.println("Učitavanje Ispita…");
        List<String> listaLinija = new ArrayList<>();
        List<Ispit> listaIspita = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_ISPITI))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 5) {
                Ispit ispit = new Ispit(
                        Long.parseLong(listaLinija.get(i)),
                        getPredmet(Long.parseLong(listaLinija.get(i + 1))),
                        getStudent(Long.parseLong(listaLinija.get(i + 2))),
                        getOcjena(Integer.parseInt(listaLinija.get(i + 3))),
                        ucitajVrijeme(listaLinija.get(i + 4))


                );
                listaIspita.add(ispit);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaIspita;

    }

    public static List<ObrazovnaUstanova> dohvatiUstanove() {
        System.out.println("Učitavanje ustanova…");
        List<String> listaLinija = new ArrayList<>();
        List<ObrazovnaUstanova> listaUstanova = new ArrayList<>();
        String linija;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_USTANOVE))) {
            while ((linija = in.readLine()) != null) {
                listaLinija.add(linija);
            }
            for (int i = 0; i < listaLinija.size(); i += 6) {
                String linijaListe = listaLinija.get(i + 2);
                String[] idevi = linijaListe.split(" ");

                List<Predmet> predmeti = new ArrayList<>();
                for (int j = 0; j < idevi.length; j++) {
                    Predmet predmet = getPredmet(Long.parseLong(idevi[j]));
                    predmeti.add(predmet);
                }
                linijaListe = listaLinija.get(i + 3);
                idevi = linijaListe.split(" ");

                List<Profesor> profesori = new ArrayList<>();
                for (int j = 0; j < idevi.length; j++) {
                    Profesor profesor = getProfesor(Long.parseLong(idevi[j]));
                    profesori.add(profesor);
                }
                linijaListe = listaLinija.get(i + 4);
                idevi = linijaListe.split(" ");

                List<Student> studenti = new ArrayList<>();
                for (int j = 0; j < idevi.length; j++) {
                    Student student = getStudent(Long.parseLong(idevi[j]));
                    studenti.add(student);
                }
                linijaListe = listaLinija.get(i + 5);
                idevi = linijaListe.split(" ");

                List<Ispit> ispiti = new ArrayList<>();
                for (int j = 0; j < idevi.length; j++) {
                    Ispit ispit = getIspit(Long.parseLong(idevi[j]));
                    ispiti.add(ispit);
                }


                ObrazovnaUstanova ustanova;

                Long id = Long.parseLong(listaLinija.get(i));
                if (id.equals(1L)) {
                    ustanova = new VeleucilisteJave(
                            id,
                            listaLinija.get(i + 1),
                            predmeti,
                            profesori,
                            studenti,
                            ispiti
                    );
                } else {
                    ustanova = new FakultetRacunarstva(
                            id,
                            listaLinija.get(i + 1),
                            predmeti,
                            profesori,
                            studenti,
                            ispiti
                    );
                }


                listaUstanova.add(ustanova);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listaUstanova;

    }

    public static Ocjena getOcjena(int ocjena) {
        Ocjena pronadenaOcjena = null;
        for (Ocjena value : Ocjena.values()) {
            if (value.getOcjena() == ocjena)
                pronadenaOcjena = value;
        }
        return pronadenaOcjena;
    }

    public static Profesor getProfesor(Long id) {
        Profesor pronadeniProfesor = null;
        for (Profesor profesor : listaProfesora
        ) {
            if (profesor.getId().equals(id)) {
                pronadeniProfesor = profesor;
            }
        }

        return pronadeniProfesor;
    }

    public static Predmet getPredmet(Long id) {
        Predmet pronadeniPredmet = null;
        for (Predmet predmet : listaPredmeta) {
            if (predmet.getId().equals(id)) {
                pronadeniPredmet = predmet;
            }
        }

        return pronadeniPredmet;
    }

    public static Student getStudent(Long id) {
        Student pronadeniStudent = null;
        for (Student student : listaStudenata
        ) {
            if (student.getId().equals(id)) {
                pronadeniStudent = student;
            }
        }

        return pronadeniStudent;
    }

    public static Ispit getIspit(Long id) {
        Ispit pronadeniIspit = null;
        for (Ispit ispit : listaIspita
        ) {
            if (ispit.getId().equals(id)) {
                pronadeniIspit = ispit;
            }
        }

        return pronadeniIspit;
    }


    public static void sloziMapuProfesoraPredmeta() {
        for (Predmet predmet : listaPredmeta) {
            Profesor izabraniProfesor = predmet.getNositelj();
            if (mapaProfesoraiPredmeta.containsKey(izabraniProfesor)) {
                List<Predmet> listaPredmeta = mapaProfesoraiPredmeta.get(izabraniProfesor);
                listaPredmeta.add(predmet);
            } else {
                List<Predmet> listaPredmeta = new ArrayList<>();
                listaPredmeta.add(predmet);
                mapaProfesoraiPredmeta.put(izabraniProfesor, listaPredmeta);
            }
        }
    }

    public static void ispisiNajboljeStudenteUstanova() {
        listaUstanova.forEach(ustanova -> {
            Glavna.ispisiNajboljegStudentaObrazovneUstanove(ustanova);
        });
    }


    public static void serijalizacija(List<ObrazovnaUstanova> listaUstanova) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("dat/obrazovne-ustanove.dat"))) {
            out.writeObject(listaUstanova);
        } catch (Exception e) {
            System.out.println("Greška prilikom serijalizacije");
            System.out.println(e.getMessage());
        }
    }

    public static void serijalizacijaIspita(List<Ispit> listaIspita) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("ispiti.dat"))) {
            out.writeObject(listaIspita);
        } catch (Exception e) {
            System.out.println("Greška prilikom serijalizacije");
            System.out.println(e.getMessage());
        }
    }

    public static List<Ispit> deserijalizacijaIspita() {
        List<Ispit> listaIspita = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("ispiti.dat"))) {

            listaIspita = (List<Ispit>) in.readObject();
        } catch (Exception e) {
            System.out.println("Greška prilikom deserijalizacije");
            System.out.println(e.getMessage());
        }
        return listaIspita;
    }


    public static void ispisStudenata(List<Ispit> listaIspita) {
        List<Ispit> filtriranaListaIspita = new ArrayList<>();
        listaIspita.stream()
                .filter(ispit -> ispit.getOcjena() == Ocjena.VRLO_DOBAR || ispit.getOcjena() == Ocjena.ODLICAN)
                .forEach(m -> filtriranaListaIspita.add(m));


        serijalizacijaIspita(filtriranaListaIspita);

        List<Ispit> deserijaliziraniIspiti = deserijalizacijaIspita();

        deserijaliziraniIspiti.stream().forEach(m -> System.out.println(m.getStudent().getIme()));

    }


    public static List<ObrazovnaUstanova> deserijalizacija() {
        List<ObrazovnaUstanova> listaUstanova = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("obrazovne-ustanove.dat"))) {

            listaUstanova = (List<ObrazovnaUstanova>) in.readObject();
        } catch (Exception e) {
            System.out.println("Greška prilikom deserijalizacije");
            System.out.println(e.getMessage());
        }
        return listaUstanova;
    }

}
