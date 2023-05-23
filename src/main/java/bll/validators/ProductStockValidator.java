package bll.validators;

import model.Product;

public class ProductStockValidator implements Validator<Product>{

    @Override
    public void validate(Product product) throws IllegalArgumentException{
        if(product.getQuantity() < 0) {
            throw new IllegalArgumentException("There isn't enough " + product.getName() + " in stock!");
        }
    }
}
