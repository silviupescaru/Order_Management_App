package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.*;
import bll.validators.*;
import dao.*;
import model.*;

import javax.swing.table.DefaultTableModel;

public class ProductBLL {

    private List<Validator<Product>> validators;
    private final ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new ProductStockValidator());
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }

    public Product insertProduct(Product product){
        return productDAO.insert(product);
    }

    public String idCalc(Product product){
        return productDAO.idCalc(product);
    }

    public Product deleteProduct(Product product){
        return productDAO.delete(product);
    }

    public DefaultTableModel initProductsTable()
    {
        return productDAO.makeTable();
    }

    public Product modifyTable(Product product){
        return productDAO.modifyTable(product);
    }

    public void validateProduct(Product p)
    {
        for (Validator<Product> validator : validators)
        {
            validator.validate(p);
        }
    }
}
