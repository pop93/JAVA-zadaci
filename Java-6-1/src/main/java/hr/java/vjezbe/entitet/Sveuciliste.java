package hr.java.vjezbe.entitet;


import java.util.ArrayList;
import java.util.List;

public class Sveuciliste<T extends ObrazovnaUstanova> {

    List<T> listaObjekataUstanova;
    public Sveuciliste() {
         listaObjekataUstanova= new ArrayList();
    }


    public void dodajObrazovnuUstanovu(T objektiUstanove) {
        listaObjekataUstanova.add(objektiUstanove);
    }

    public T dohvatiObrazovnuUstanovu(Integer i) {
        return this.listaObjekataUstanova.get(i);
    }

    public List<T> listaObrazovnihUstanova() {
        return listaObjekataUstanova;
    }
}
