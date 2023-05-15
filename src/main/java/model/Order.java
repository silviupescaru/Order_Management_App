package model;

public class Order {
    private int id;
    private int quantity;
    private int id_c;
    private int id_p;

    public Order(){
    }

    public Order(int id, int quantity, int id_c, int id_p){
        super();
        this.id = id;
        this.quantity = quantity;
        this.id_c = id_c;
        this.id_p = id_p;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getId_c(){
        return id_c;
    }

    public int getId_p(){
        return id_p;
    }

    @Override
    public String toString(){
        return "Order [id=" + id + ", quantity=" + quantity + ", id_c=" + id_c + ", id_p=" + id_p;
    }

}
