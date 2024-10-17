/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author joanp
 */
public class ShoppingCart {
    
    private final ArrayList<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public float getTotal() {
        float total = 0;
        
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }
    
}
