/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.SaleDao;
import java.util.ArrayList;
import model.Membership;
import model.Sale;
import model.Trainer;

/**
 *
 * @author joanp
 */
public class SaleController {

    private final SaleDao saledao;

    public SaleController() {
        saledao = new SaleDao();
    }

    public ArrayList<Sale> listProductsSales() {
        return saledao.listProductsSales();
    }
    
    public ArrayList<Sale> listTrainerSales(String cedula) {
        return saledao.listTrainerSalesByDate(cedula);
    }
    
    public ArrayList<Membership> listMembershipsSales() {
        return saledao.listMembershipsSales();
    }
    
    public Sale selectSale(int id) {
        return saledao.selectSale(id);
    }
    
    public void insertSale(Sale sale) {
        saledao.insertSale(sale);
    }
    
    public Trainer selectTrainer(String cedula) {
        return saledao.selectTrainer(cedula);
    }
    

}
