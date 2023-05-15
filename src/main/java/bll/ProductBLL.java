package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.*;
import bll.validators.*;
import dao.*;
import model.*;

public class ProductBLL {

    //private List<Validator<Product>> validators;
    private final ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public Product findProductById(int ID) {
        Product product = productDAO.findById(ID);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + ID + " was not found!");
        }
        return product;
    }
}
