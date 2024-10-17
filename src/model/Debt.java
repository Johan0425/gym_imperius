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
public class Debt {

    private int id;
    private String paymentMethod;

    private final Product product;
    private final User user;
    private final Customer customer;
    private final LocalDate date;
    private final float total;

    public Debt(Product product, User user, Customer customer, String paymentMethod) {
        this.product = product;
        this.user = user;
        this.customer = customer;
        this.date = LocalDate.now();
        this.total = product.getPrice();
        this.paymentMethod = paymentMethod;
    }

    public Debt(int id, Product product, User user, Customer customer, LocalDate date, float total, String paymentMethod) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
