package bejarhato;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bejarhato {
    public static void main(String[] args) {
        Garazs garazs = new Garazs();
        /* PROBLÉMÁK: */
        /* 
         * 1.: a referencia típusra adott getteren keresztül elérem 
         * a private adattagot
         * 2.: felfedtük az osztály belső reprezentációját
        */
//        Auto[] autok = garazs.getAutok();
//        tombKiir("autók a garázsban: ", autok);
//        autok[0] = new Auto("Lada", "XYZ-789");
//        tombKiir("autók a garázsban: ", garazs.getAutok());

        //az 1. még probléma, de a 2. az osztályban javítva!
        ArrayList<Auto> autok = garazs.getAutokList();
        for (Auto auto : autok) {
            System.out.println(auto);
        }
        autok.set(0, new Auto("Lada", "XYZ-789"));
        for (Auto auto : garazs.getAutokList()) {
            System.out.println(auto);
        }
        
        System.out.println("--- Nem módosítható: ---");
        List<Auto> nemModosithato = garazs.getAutokNemModosithatoLista();
        for (Auto auto : nemModosithato) {
            System.out.println(auto);
        }
        //nemModosithato.set(0, new Auto("Lada", "XYZ-789"));
        
        System.out.println("--- Bejárható: ---");
        for (Auto auto : garazs) {
            System.out.println(auto);
        }
        
        System.out.println("--- iterátor használata: ---");
        Iterator<Auto> it = garazs.iterator();
        Auto elsoAuto = it.next();//nem használom fel
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    private static void tombKiir(String str, Auto[] tomb) {
        System.out.println(str);
        for (Auto auto : tomb) {
            System.out.println(auto);
        }
        
        
//        /* primitív típusok bejárhatóak: */
//        int[] egeszek = {3,6,1,9,2};
//        for (int szam : egeszek) {
//            System.out.print(szam + " ");
//        }
//        System.out.println("");
//        
//        /* Saját osztály típusú tömb is bejárható: */
//        Auto[] autok = new Auto[3];
//        autok[0] = new Auto("BMW", "ABC-123");
//        autok[1] = new Auto("VW", "QWE-123");
//        autok[2] = new Auto("Suzuki", "ASD-123");
//        
//        for (Auto auto : autok) {
//            System.out.println(auto);
//        }
    }
    
}
