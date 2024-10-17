/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.CustomerDao;
import java.util.ArrayList;
import model.Customer;
import model.Membership;

/**
 *
 * @author joanp
 */
public class CustomerController {

    private final CustomerDao customerdao;

    public CustomerController() {
        this.customerdao = new CustomerDao();
    }

    public ArrayList<Object> listCustomers() {
        return customerdao.listEntity();
    }

    public Customer selectCustomer(String cedula) {
        return (Customer) customerdao.selectEntity(cedula);
    }

    public void insertCustomer(Customer customer) {
        customerdao.insertEntity(customer);
    }

    public void updateCustomer(Customer customer) {
        customerdao.updateEntity(customer);
    }

    public void deleteCustomer(String cedula) {
        customerdao.deleteEntity(cedula);
    }

    public void renewMembership(String customerId, Membership membership) {
        customerdao.renewMembership(customerId, membership);
    }

    public void editMembership(String customerId, Membership membership) {
        customerdao.editMembership(customerId, membership);
    }

}
