package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.Validator;
import dao.*;
import model.*;

import javax.swing.table.DefaultTableModel;

public class OrderBLL {

    private List<Validator<Orders>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        validators = new ArrayList<>();
        orderDAO = new OrderDAO();
    }

    public Orders findOrderById(int id) {
        Orders order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    public int insertOrder(Orders o)
    {
        return orderDAO.insert(o).getId();
    }

    public void deleteOrder(Orders o)
    {
        orderDAO.delete(o);
    }

    public void validateOrder(Orders o)
    {
        for(Validator<Orders> validator : validators)
        {
            validator.validate(o);
        }
    }

    public DefaultTableModel initOrdersTable()
    {
        return orderDAO.makeTable();
    }
    }
