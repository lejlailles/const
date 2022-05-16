package masolhato;
public class Ember implements Cloneable{
    String nev;
    int kor;
    Ember barat;

    public Ember(String nev, int kor) {
        this(nev, kor, null);//lehetséges NullPointerException
        
        //this.nev = nev;
        //this.kor = kor;
    }
    
    public Ember(String nev, int kor, Ember barat) {
        this.nev = nev;
        this.kor = kor;
        this.barat = barat;
    }

    @Override
    public String toString() {
        return "Ember{" + "nev=" + nev + ", kor=" + kor + ", barat=" + barat + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); 
    }
    
    
}

package masolhato;

public class Program {
    public static void main(String[] args) throws CloneNotSupportedException {
        Ember e1 = new Ember("Péter", 22, new Ember("Pál", 33));
        Ember e2 = (Ember)e1.clone();
        
        e2.nev = "Pisti";
        System.out.println("e2=e1 és e2.nev = \"Pisti\":");
        System.out.println(e1);
        System.out.println(e2);
        
        Ember barat = new Ember("Xénia", 22);
        Ember e3 = new Ember("Géza", 44, barat);
        Ember e4 = (Ember)e3.clone();
        e4.kor = 55;
        e4.barat = new Ember("Piroska", 22);
        System.out.println(e3);
        System.out.println(e4);
        
    }
}

