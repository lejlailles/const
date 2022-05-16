
package allapotmentes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Harcos implements Serializable{
    
    /* osztály (static) dolgai nem szerializálódnak */
    private static Random rnd = new Random();
    private static final long serialVersionUID = 3L;
    
    /* láthatóságtól függetlenül minden szerializálódik */
    private String nev;
    private int arany;
    private ArrayList<String> felszereles;
    
    /* kényes vagy generált adatokat nem akarunk szerializálni */
    transient private UUID id;// = UUID.randomUUID();

    public Harcos(){
        this("");
        /* hogy ne legyen kódduplikálás: */
        arany = 0;
        
//        nev = "";//"." vagy "nincs neve"
//        arany = 0;
//        felszereles = new ArrayList<>();
//        //id = 1;
    }
    
    public Harcos(String nev) {
        this.nev = nev;
        arany = rnd.nextInt(10)+1; //3;
        felszereles = new ArrayList<>();
        //id = 1;
        ujId();
    }
    
    public void ujId() {
        id = UUID.randomUUID();
    }
    
    public void felvesz(String targy){
        felszereles.add(targy);
    }
    
    @Override
    public String toString() {
        String felsz = "", sep = System.lineSeparator();
        for (String f : felszereles) {
            felsz += sep + "\t" + f;
        }
        return "Harcos{" + "nev=" + nev + ", arany=" + arany 
                + sep + "felszereles:" + felsz 
                + sep + "ID: " + id
                + sep + '}';
    }
    
    /* biztonságos getterek, ha kell setterek... */

    
}






package allapotmentes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllapotMentes {
    public static void main(String[] args) {
        Harcos harcos = new Harcos("Kübli");
        harcos.felvesz("Fejsze");
        harcos.felvesz("Pajzs");
        
        System.out.println("A játék jelenlegi állása: ");
        System.out.println(harcos);
        
        mentes(harcos);
        System.out.println("\nKikapcsolom a gépet, de mentjük az állást!\n");
        harcos = null;
        
        System.out.println("A betöltött állás:");
        harcos = betolt();
        System.out.println(harcos);
        //harcos.felvesz("");
    }

    private static void mentes(Harcos harcos) {
        /* Lehetne Files osztály metódusaival a gettereket kiírni */

        try (ObjectOutputStream objKi = new ObjectOutputStream(new FileOutputStream("harcos3.ser"))) {
            //FileOutputStream fajlKi = new FileOutputStream("harcos3.ser");
            //ObjectOutputStream objKi = new ObjectOutputStream(fajlKi);
            objKi.writeObject(harcos);
            /* aki try() ban van, az aut. lezáródik, nem kell explicit meghívni */
            //objKi.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllapotMentes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println(ex);
            //Logger.getLogger(AllapotMentes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static Harcos betolt() {
        Harcos harcos = new Harcos();// null;
        try (ObjectInputStream objKi = new ObjectInputStream(new FileInputStream("harcos3.ser"))) {
            //FileInputStream fajlKi = new FileInputStream("harcos3.ser");
            //ObjectInputStream objKi = new ObjectInputStream(fajlKi);
            //objKi.writeObject(harcos);
            harcos = (Harcos)objKi.readObject();
            harcos.ujId();
            //objKi.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllapotMentes.class.getName()).log(Level.SEVERE, null, ex);
        }catch(InvalidClassException ex){
            System.err.println("Régi verzió nem kompatibilis az új verzióval!");
            System.err.println(ex.getMessage());
        } 
        catch (IOException ex) {
            System.err.println(ex);
            //Logger.getLogger(AllapotMentes.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(AllapotMentes.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return harcos;
        }
    }
    
}
