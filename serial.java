package szerializalas;
public class Szerializalas {

    public static void main(String[] args) {
        Raktar raktar = new Raktar();
        raktar.felvesz(new Tabla(12, 23, "fehér"));
        raktar.felvesz(new Tabla(10, 20, "piros"));
        raktar.felvesz(new Tabla(11, 22, "Kék"));
        
        mutatLista(raktar);
        
        raktar.kiir();
        
        System.out.println("raktar null");
        raktar = null;
        System.out.println("raktár beolvasva:");
        raktar = Raktar.beolvas("raktar.bin");
        mutatLista(raktar);
        
    }

    private static void mutatLista(Raktar raktar) {
        for (Tabla tabla : raktar.getTablak()) {
            System.out.println(tabla);
        }
    }
    
}

package szerializalas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Raktar implements Serializable{
    private ArrayList<Tabla> tablak;

    public Raktar() {
        tablak = new ArrayList<>();
    }
    
    public void felvesz(Tabla tabla){
        tabla.setUjId();
        tablak.add(tabla);
    }

    /* ez nem jó, legyen valamelyik:
     * -> nem módosítható lista
     * másolat
     * Iterable
    */
    public List<Tabla> getTablak() {
        //return tablak;
        return Collections.unmodifiableList(tablak);
    }
    
    public void kiir(){
        try (ObjectOutputStream objKi = new ObjectOutputStream(new FileOutputStream("raktar.bin"))) {
            //FileOutputStream fajlKi = new FileOutputStream("raktar.bin");
            //ObjectOutputStream objKi = new ObjectOutputStream(fajlKi);
            objKi.writeObject(this);
            //objKi.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Raktar beolvas(String fajlNev){
        Raktar raktar = new Raktar();
        try(ObjectInputStream objBe = new ObjectInputStream(new FileInputStream(fajlNev))){
            raktar = (Raktar)objBe.readObject();
            
            //id
            for (Tabla tabla : raktar.getTablak()) {
                tabla.setUjId();
            }
            
            return raktar;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return raktar;
    }
}


package szerializalas;

import java.io.Serializable;
import java.util.UUID;

public class Tabla implements Serializable{
    private int szel, mag;
    private String szin;
    
    //nem szerializáljuk
    private transient UUID id;

    public Tabla(int szel, int mag, String szin) {
        this.szel = szel;
        this.mag = mag;
        this.szin = szin;
    }

    public int getSzel() {
        return szel;
    }

    public int getMag() {
        return mag;
    }

    public String getSzin() {
        return szin;
    }

    public UUID getId() {
        return id;
    }

    public void setUjId() {
        this.id = UUID.randomUUID();
    }
    
    @Override
    public String toString() {
        return "Tabla{" + "szel=" + szel + ", mag=" + mag + ", szin=" + szin + ", id=" + id + '}';
    }
    
    
}

