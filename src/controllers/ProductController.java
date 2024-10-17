/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.ProductDao;
import java.util.ArrayList;
import model.Product;

/**
 *
 * @author joanp
 */
public class ProductController {

    private final ProductDao productdao;

    public ProductController() {
        this.productdao = new ProductDao();
    }

     public ArrayList<Object> listProducts() {
        return productdao.listEntity();
    }

    public Product selectProduct(int id) {
        return (Product) productdao.selectEntity(id);
    }

    public void insertProduct(Product product) {
        productdao.insertEntity(product);
    }

    public void updateProduct(Product product) {
        productdao.updateEntity(product);
    }

    public void deleteAllProduct(int id) {
        productdao.deleteEntity(id);
    }

    /**
     * Elimina una cantidad específica de un producto del sistema por su ID.
     *
     * @param id ID del producto a eliminar.
     * @param quantity Cantidad del producto a eliminar.
     */
    public void deleteProduct(int id, int quantity) {
        productdao.deleteProduct(id, quantity);
    }

}
