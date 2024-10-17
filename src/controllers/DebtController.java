/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.DebtDao;
import java.util.ArrayList;
import model.Customer;
import model.Debt;
import model.Product;
import model.Sale;

/**
 *
 * @author joanp
 */
public class DebtController {

    private final DebtDao debtdao;

    public DebtController() {
        this.debtdao = new DebtDao();
    }

    public ArrayList<Debt> listProductsDebts() {
        return debtdao.listProductsDebts();
    }

    public void repayDebt(Sale sale, int id) {
        debtdao.repayDebt(sale, id);
    }

    public void insertDebt(Debt debt) {
        debtdao.insertDebt(debt);
    }

    public Debt selectDebt(int id) {
        return debtdao.selectDebt(id);
    }

    public Object selectEntity(String cedula) {
        return (Customer) debtdao.selectEntity(cedula);
    }

    public Object selectProduct(int id) {
        return (Product) debtdao.selectProduct(id);
    }

}
