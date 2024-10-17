/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.MembershipType;
import enums.Role;
import interfaces.SaleDaoInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Membership;
import model.Product;
import model.Sale;
import model.Trainer;
import model.User;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class SaleDao implements SaleDaoInterface {

    private final Connection con;
    private final ProductDao productDao;

    public SaleDao() {
        con = Singleton.getINSTANCE().getConnection();
        productDao = new ProductDao();
    }

    @Override
    public ArrayList<Sale> listProductsSales() {
        var sales = new ArrayList<Sale>();
        String sql = "SELECT * FROM sales AS s JOIN products AS p ON s.product_id = p.id JOIN users AS u ON s.user_id = u.id";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                // Product data
                int productId = rs.getInt("p.id");
                Product product = (Product) productDao.selectEntity(productId);

                // User data
                int userId = rs.getInt("u.id");
                User user = selectUser(userId);

                // Sale data
                int saleId = rs.getInt("s.id");
                LocalDate saleDate = rs.getDate("s.date").toLocalDate();
                float total = rs.getFloat("s.total");
                String payment = rs.getString("s.payment_method");

                Sale sale = new Sale(saleId, product, user, saleDate, total, payment);
                sales.add(sale);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return sales;
    }

    public ArrayList<Membership> listMembershipsSales() {
        var memberships = new ArrayList<Membership>();
        String sql = "SELECT * FROM memberships AS m JOIN users AS u ON m.user_id = u.id";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("m.id");
                MembershipType type = MembershipType.valueOf(rs.getString("m.type"));
                LocalDate startDate = rs.getDate("m.start_date").toLocalDate();
                LocalDate endDate = rs.getDate("m.end_date").toLocalDate();
                String paymentMethod = rs.getString("m.payment_method");
                int remainingDays = rs.getInt("m.remaining_days");

                User seller = selectUser(rs.getInt("m.user_id"));

                Membership membership = new Membership(id, type, startDate, endDate, remainingDays, type.getPrice(), seller, paymentMethod);
                memberships.add(membership);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return memberships;
    }

    @Override
    public ArrayList<Sale> listTrainerSalesByDate(String cedula) {
        var sales = new ArrayList<Sale>();
        String sql = "SELECT * FROM sales AS s JOIN products AS p ON s.product_id = p.id JOIN users AS u ON s.user_id = u.id WHERE u.cedula = ? AND date = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                // Product data
                int productId = rs.getInt("p.id");
                Product product = (Product) productDao.selectEntity(productId);

                // User data
                int userId = rs.getInt("u.id");
                User user = selectUser(userId);

                // Sale data
                int saleId = rs.getInt("s.id");
                LocalDate saleDate = rs.getDate("s.date").toLocalDate();
                float total = rs.getFloat("s.total");
                String payment = rs.getString("s.payment_method");

                Sale sale = new Sale(saleId, product, user, saleDate, total, payment);
                sales.add(sale);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return sales;
    }

    @Override
    public Sale selectSale(int id) {
        String sql = "SELECT * FROM sales AS s JOIN products AS p ON s.product_id = p.id JOIN users AS u ON s.user_id = u.id WHERE s.id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // Product data
                int productId = rs.getInt("p.id");
                Product product = (Product) productDao.selectEntity(productId);

                // User data
                int userId = rs.getInt("u.id");
                User user = selectUser(userId);

                // Sale data
                LocalDate saleDate = rs.getDate("s.date").toLocalDate();
                float total = rs.getFloat("s.total");
                String payment = rs.getString("s.payment_method");

                return new Sale(id, product, user, saleDate, total, payment);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    @Override
    public void insertSale(Sale sale) {
        String sql = "INSERT INTO sales (date, total,  product_id, user_id, payment_method) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(sale.getDate()));
            ps.setFloat(2, sale.getProduct().getPrice());
            ps.setInt(3, sale.getProduct().getId());
            ps.setInt(4, sale.getUser().getId());
            ps.setString(5, sale.getPaymentMethod());

            ps.executeUpdate();
            //se actualiza el stock
            productDao.deleteProduct(sale.getProduct().getId(), 1);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public Trainer selectTrainer(String cedula) {
        String sql = "SELECT * FROM employees WHERE cedula = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String fullname = rs.getString("fullname");
                String phoneNumber = rs.getString("phone_number");
                String password = rs.getString("password");

                return new Trainer(fullname, phoneNumber, cedula, password);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    private User selectUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String cedula = rs.getString("cedula");
                String password = rs.getString("password");
                Role role = Role.valueOf(rs.getString("role"));

                return new User(id, cedula, role, password);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

}
