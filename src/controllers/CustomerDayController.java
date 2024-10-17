/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.CustomerDayDao;
import java.util.ArrayList;
import model.Customer;

/**
 *
 * @author joanp
 */
public class CustomerDayController {

    private final CustomerDayDao customerdao;

    public CustomerDayController() {
        this.customerdao = new CustomerDayDao();
    }

    public void insertCustomerDay(Customer customer) {
        customerdao.insertEntityDay(customer);
    }

    public ArrayList<Object> listCustomersDay() {
        return customerdao.listEntityDay();
    }

}
