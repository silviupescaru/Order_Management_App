package model;

public class Product {
    private int id;
    private double price;
    private String name;
    private int quantity;

    public Product(){}

    public Product(int id){
        super();
        this.id = id;
    }

    public Product(int id, double price, String name, int quantity){
        super();
        this.id = id;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString(){
        return "Product [id=" + id + ", pret=" + price + ", nume=" + name + ", cantitate=" + quantity;
    }

}
