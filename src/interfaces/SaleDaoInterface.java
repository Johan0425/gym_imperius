/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.ArrayList;
import model.Sale;

/**
 *
 * @author joanp
 */
public interface SaleDaoInterface {

    ArrayList<Sale> listProductsSales();

    ArrayList<Sale> listTrainerSalesByDate(String cedula);

    Sale selectSale(int id);

    void insertSale(Sale sale);

}
