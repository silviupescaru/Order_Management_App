package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.*;
import bll.validators.*;
import dao.*;
import model.*;

public class OrderBLL {

    //private List<Validator<Product>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

}
