package hr.java.vjezbe.sortiranje;


import hr.java.vjezbe.entitet.Student;


import java.util.Comparator;
import java.util.function.Function;


public class StudentSorter implements Comparator<Student> {


    @Override
    public int compare(Student o1, Student o2) {
        int prezimeRezultat =o1.getPrezime().compareToIgnoreCase(o2.getPrezime());
            if(prezimeRezultat==0) {
                return  o1.getIme().compareToIgnoreCase(o2.getIme());
            }else {
                return prezimeRezultat;
            }
    }

    @Override
    public Comparator<Student> thenComparing(Comparator<? super Student> other) {
        Function<Student, String> poImenu = Student::getIme;
        Function<Student, String> poPrezimenu = Student::getPrezime;
        Comparator<Student> poPrezimenuImenu = Comparator.comparing(poPrezimenu).thenComparing(poImenu);
        return poPrezimenuImenu;
    }


}
