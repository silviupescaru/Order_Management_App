package bll;

import java.util.NoSuchElementException;

import dao.*;
import model.*;

public class OrderBLL {

    //private List<Validator<Product>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    public Orders findOrderById(int id) {
        Orders order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

}
