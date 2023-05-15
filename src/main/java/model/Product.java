package model;

public class Product {
    private int ID;
    private double PRET;
    private String NUME;
    private int CANTITATE;

    public Product(){}

    public Product(int ID, double PRET, String NUME, int CANTITATE){
        super();
        this.ID = ID;
        this.PRET = PRET;
        this.NUME = NUME;
        this.CANTITATE = CANTITATE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPRET() {
        return PRET;
    }

    public void setPRET(double PRET) {
        this.PRET = PRET;
    }

    public String getNUME() {
        return NUME;
    }

    public void setNUME(String NUME) {
        this.NUME = NUME;
    }

    public int getCANTITATE() {
        return CANTITATE;
    }

    public void setCANTITATE(int CANTITATE) {
        this.CANTITATE = CANTITATE;
    }


    @Override
    public String toString(){
        return "Product [ID=" + ID + ", pret=" + PRET + ", nume=" + NUME + ", cantitate=" + CANTITATE;
    }
    /*
    *   ID
    *   PRET
    *   NUME
    *   CANTITATE
    * */
}
