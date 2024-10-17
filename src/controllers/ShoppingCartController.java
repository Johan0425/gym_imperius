/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.ShoppingCartDao;
import model.Product;
import model.ShoppingCart;

/**
 *
 * @author joanp
 */
public class ShoppingCartController {

    private final ShoppingCart cart;
    private final ShoppingCartDao cartdao;

    public ShoppingCartController(ShoppingCart cart) {
        this.cart = cart;
        cartdao = new ShoppingCartDao(cart);
    }

    public Product selectProduct(int id) {
        return cartdao.selectProduct(id);
    }

    public Product selectProductFromCart(int id) {
        for (Product p : cart.getProducts()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void addProduct(Product product) {
        cartdao.addProduct(product);
    }

    public void removeFromCart(int id) {
        cartdao.removeFromCart(id);
    }

//    public void removeProduct(int id) {
//        cartdao.removeProduct(id);
//    }
}
