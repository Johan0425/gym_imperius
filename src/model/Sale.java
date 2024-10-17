/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author joanp
 */
public class Sale {

    private final Product product;
    private final User user;
    private final LocalDate date;
    private final float total;

    private int id;
    private String paymentMethod;

    public Sale(Product product, User user, String paymentMethod) {
        this.product = product;
        this.user = user;
        this.date = LocalDate.now();
        this.total = product.getPrice();
        this.paymentMethod = paymentMethod;
    }

    public Sale(int id, Product product, User user, LocalDate date, float total, String paymentMethod) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getTotal() {
        return total;
    }

    public int getId() {
        return id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
