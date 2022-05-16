package bejarhato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Nevsor implements Iterable<Ember>{
    private ArrayList<Ember> lista;
    //private HashSet<Ember> lista;

    public Nevsor() {
        lista = new ArrayList<>();
        //lista = new HashSet<>();
        lista.add(new Ember("Péter", 33));
        lista.add(new Ember("Pál", 23));
        lista.add(new Ember("Petra", 19));
        lista.add(new Ember("Pisti", 42));
    }

    /* PROBLÉMÁK:
     * 1: getter, ami setter
     * 2: felfedtük az osztály belső adatszerkezetét
    */
//    public ArrayList<Ember> getLista() {
//    public HashSet<Ember> getLista() {
//        return lista;
//    }
    
    /* MEGOLDÁSOK */
    //1.a: nem módosítható
    public List<Ember> getUnModLista(){
        return Collections.unmodifiableList(lista);
    }
    
    //1.b: másolat, így tényleg csak getter
    public ArrayList<Ember> getMasolatLista(){
        return new ArrayList<>(lista);
    }
    
    
    //2 elvonatkoztatunk az osztály konkrét belső adatábrázolásától
    public Ember[] getEmberek(){
        Ember[] tomb = new Ember[lista.size()];
        int i = 0;
        for (Ember ember : lista) {
            tomb[i++] = ember;
        }
        return tomb;
    }
    
    //3 bejárhatóvá tesszük sz osztályt
    @Override
    public Iterator<Ember> iterator() {
        return lista.iterator();
    }
    
}
