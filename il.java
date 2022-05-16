package hadseregprogram;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/*Rendező osztályok*/
class CmpSzin implements Comparator<Katona> {

    @Override
    public int compare(Katona o1, Katona o2) {
        Collator col = Collator.getInstance();
        return col.compare(o1.getSzin(), o2.getSzin());
    }
}

class CmpFokozat implements Comparator<Katona> {

    @Override
    public int compare(Katona o1, Katona o2) {
        Collator col = Collator.getInstance();
        return col.compare(o1.getFokozat(), o2.getFokozat());
    }
}

class CmpEletero implements Comparator<Katona> {

    @Override
    public int compare(Katona o1, Katona o2) {
        return o1.getEletero() - o2.getEletero();
    }
}

class CmpLovedek implements Comparator<Katona> {

    @Override
    public int compare(Katona o1, Katona o2) {
        return o1.getLovedek() - o2.getLovedek();
    }
}

package hadseregprogram;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Katona implements Cloneable, Serializable {

    private String szin, fokozat;
    private int eletero, lovedek;
    private transient UUID uuid;
    
    
      public Katona(String szin, String fokozat, int eletero, int lovedek) {
        this.szin = szin;
        this.fokozat = fokozat;
        this.eletero = eletero;
        this.lovedek = lovedek;
        uuid = UUID.randomUUID();
    }

    public String getSzin() {
        return szin;
    }

    public String getFokozat() {
        return fokozat;
    }

    public int getEletero() {
        return eletero;
    }

    public int getLovedek() {
        return lovedek;
    }

    @Override
    public String toString() {
        return "Katona{" + "szin=" + szin + ", fokozat=" + fokozat + ", eletero=" + eletero + ", lovedek=" + lovedek + ", uuid=" + uuid + '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUUID() {
        uuid = UUID.randomUUID();
    }

  

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Katona k = (Katona) super.clone();
        k.setUUID();
        return k;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.szin);
        hash = 59 * hash + Objects.hashCode(this.fokozat);
        hash = 59 * hash + this.eletero;
        hash = 59 * hash + this.lovedek;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Katona other = (Katona) obj;
        if (this.eletero != other.eletero) {
            return false;
        }
        if (this.lovedek != other.lovedek) {
            return false;
        }
        if (!Objects.equals(this.szin, other.szin)) {
            return false;
        }
        if (!Objects.equals(this.fokozat, other.fokozat)) {
            return false;
        }
        return true;
    }
}


public class Hadsereg implements Iterable<Katona>, Serializable {

    List<Katona> katonak = new ArrayList<>();

    public Hadsereg() {
        katonak = new ArrayList<>();
        katonak.add(new Katona("piros", "őrmester", 40, 20));
        katonak.add(new Katona("piros", "őrmester", 40, 20));
        katonak.add(new Katona("fekete", "hadnagy", 80, 20));
    }

    public void elment(String path) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
        out.writeObject(this);
        out.flush();
        out.close();
    }

    public static Hadsereg visszaallit(String path) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
        Hadsereg h = (Hadsereg) in.readObject();

        for (Katona k : h) {
            k.setUUID();
        }

        return h;
    }

    /*Rendezések*/
    public void szinRendez() {
        Collections.sort(katonak, new CmpSzin());
    }

    public void fokozatRendez() {
        Collections.sort(katonak, new CmpFokozat());
    }

    public void eleteroRendez() {
        Collections.sort(katonak, new CmpEletero());
    }

    public void lovedekrendez() {
        Collections.sort(katonak, new CmpLovedek());
    }

    public void felvesz(Katona k) {
        katonak.add(k);
        szinRendez();
    }

    public void kulonbozoek() {
        HashSet<Katona> kulonbKatonak = new HashSet<>(katonak);

        int db = kulonbKatonak.size();
        System.out.printf("Különböző katonák: (%d):\n%s\n", db, kulonbKatonak);
    }

    @Override
    public Iterator<Katona> iterator() {
        return katonak.iterator();
    }
}

package hadseregprogram;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TesztHadsereg {

    /*beolvasás*/
    public TesztHadsereg(String path) throws Exception {
        List<String> sorok = Files.readAllLines(Paths.get(path));
        Hadsereg h = new Hadsereg();

        for (String sor : sorok) {
            String[] szeletek = sor.split("\\s");
            h.felvesz(new Katona(szeletek[0], szeletek[1], Integer.parseInt(szeletek[2]), Integer.parseInt(szeletek[3])));

        }

        System.out.println("Hadseregben katonák jelenleg:");
        /*szín alapján rendez*/
        System.out.println("Szín alapján rendezve:");

        /*lövedék alapján rendez*/
// System.out.println("Lövedék alapján rendezve:");
// 
// h.lovedekrendez();
        /*életerő alapján rendez*/
//  System.out.println("Életerő alapján rendezve:");
//        h.eleteroRendez();

        /*fokozat alapján rendez*/
// System.out.println("Fokozat alapján rendezve:");
// 
// h.fokozatRendez();
        h.szinRendez();
        for (Katona k : h) {
            System.out.println(k);
        }

        /*különböző katonák*/
        h.kulonbozoek();

        /*állapot ment*/
        h.elment("allapot.bin");

        /*állapot visszaállít*/
        
          System.out.println("visszaállított katonák: ");
        Hadsereg masolat = Hadsereg.visszaallit("allapot.bin");
        
        for (Katona katona : masolat) {
            System.out.println(katona);
            
        }
    }
}

