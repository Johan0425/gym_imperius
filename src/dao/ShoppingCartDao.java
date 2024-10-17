/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Product;
import model.ShoppingCart;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import model.Customer;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class ShoppingCartDao {

    private final Connection con;
    private final ProductDao productDao;
    private final ShoppingCart cart;

    public ShoppingCartDao(ShoppingCart cart) {
        this.cart = cart;
        con = Singleton.getINSTANCE().getConnection();
        productDao = new ProductDao();
    }

    public Product selectProduct(int id) {
        return (Product) productDao.selectEntity(id);
    }

    public void addProduct(Product product) {
        cart.getProducts().add(product);
    }

//    public void removeProduct(int id) {
//        String sql = "UPDATE products SET quantity = quantity + 1 WHERE id = ?";
//
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            ps.executeUpdate();
//            removeFromCart(id);
//        } catch (SQLException ex) {
//            System.err.println(ex.toString());
//        }
//    }

    public void removeFromCart(int id) {
        for (int i = 0; i < cart.getProducts().size(); i++) {
            if (cart.getProducts().get(i).getId() == id) {
                cart.getProducts().remove(i);
            }
        }
    }
}
