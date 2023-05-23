package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.*;
import bll.validators.*;
import dao.*;
import model.*;

import javax.swing.table.DefaultTableModel;

public class ClientBLL {

    private final List<Validator<Client>> validators;
    private final ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    public List<Client> findAllClient(){
        List<Client> clients = clientDAO.findAll();
        if(clients == null){
            throw new NoSuchElementException("The table client is empty");
        }
        return clients;
    }

    public Client insertClient(Client client){
        return clientDAO.insert(client);
    }

    public String idCalc(Client client){
        return clientDAO.idCalc(client);
    }

    public Client deleteClient(Client client){
        return clientDAO.delete(client);
    }

    public DefaultTableModel initClientsTable()
    {
        return clientDAO.makeTable();
    }

    public Client modifyClient(Client client){
        return clientDAO.modifyTable(client);
    }

}
