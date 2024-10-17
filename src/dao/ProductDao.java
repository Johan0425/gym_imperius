/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import interfaces.DaoInterface;
import java.awt.print.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Product;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class ProductDao implements DaoInterface {

    private final Connection con;

    public ProductDao() {
        this.con = Singleton.getINSTANCE().getConnection();
    }

    @Override
    public ArrayList<Object> listEntity() {
        var products = new ArrayList<Object>();
        String sql = "SELECT * FROM products";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                float price = rs.getFloat("price");

                Product product = new Product(id, name, category, quantity, price);
                products.add(product);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return products;
    }

    @Override
    public Object selectEntity(Object obj) {
        String sql = "SELECT * FROM products WHERE id = ?";

        int id = (int) obj;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                float price = rs.getFloat("price");

                return new Product(id, name, category, quantity, price);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    @Override
    public void insertEntity(Object obj) {
        String sql = "INSERT INTO products (name, category, quantity, price) VALUES (?, ?, ?, ?)";
        Product product = (Product) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getQuantity());
            ps.setFloat(4, product.getPrice());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void updateEntity(Object obj) {
        String sql = "UPDATE products SET name = ?, category = ?, quantity = ?, price = ? WHERE id = ?";
        Product product = (Product) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getQuantity());
            ps.setFloat(4, product.getPrice());
            ps.setInt(5, product.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     * Elimina la totalidad de un producto en especifico.
     *
     * @param obj ID del producto.
     */
    @Override
    public void deleteEntity(Object obj) {
        String sql = "DELETE FROM products WHERE id = ?";
        int id = (int) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     * Elimina cierta cantidad de un producto, actualizando su cantidad. En caso
     * de que la cantidad sea el stock disponible, el producto será eliminado de
     * la BD.
     *
     * @param id ID del producto.
     * @param quantity Cantidad que desea eliminar.
     */
    public void deleteProduct(int id, int quantity) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            if (quantity == getTotalQuantity(id)) { // Elimina el producto de la BD.
//                ps.setInt(1, id);
//                ps.executeUpdate();
//            } else {
            // Resta del stock disponible la cantidad que desea eliminar.
            sql = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
            try (PreparedStatement updatePs = con.prepareStatement(sql)) {
                updatePs.setInt(1, quantity);
                updatePs.setInt(2, id);
                updatePs.executeUpdate();
            }
//            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     * Obtiene el stock disponible de un producto.
     *
     * @param id ID producto.
     * @return Stock disponible del producto.
     */
    private int getTotalQuantity(int id) {
        String sql = "SELECT quantity FROM products WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return 0;
    }

}
