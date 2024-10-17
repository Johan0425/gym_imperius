/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.MembershipType;
import enums.Role;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Customer;
import model.Debt;
import model.EmergencyContact;
import model.Membership;
import model.Product;
import model.Sale;
import model.User;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class DebtDao {

    private final Connection con;
    private final ProductDao productdao;

    public DebtDao() {
        this.con = Singleton.getINSTANCE().getConnection();
        productdao = new ProductDao();
    }

    public ArrayList<Debt> listProductsDebts() {
        var debts = new ArrayList<Debt>();
        String sql = "SELECT * FROM loans AS l JOIN products AS p ON l.productLoan_id = p.id JOIN users AS u ON l.userLoan_id = u.id JOIN customers AS c ON "
                + "l.customerLoan_id = c.cedula";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                // Product data
                int productId = rs.getInt("p.id");
                Product product = (Product) productdao.selectEntity(productId);

                // User data
                int userId = rs.getInt("u.id");
                User user = selectUser(userId);

                // Customer data
                String cedula = rs.getString("c.cedula");
                Customer customer = (Customer) selectEntity(cedula);

                // loan data
                int debtId = rs.getInt("l.id");
                LocalDate debtDate = rs.getDate("l.date").toLocalDate();
                float total = rs.getFloat("l.total");
                String payment = rs.getString("l.payment_method");

                Debt debt = new Debt(debtId, product, user, customer, debtDate, total, payment);
                debts.add(debt);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return debts;
    }

    public void repayDebt(Sale sale, int id) {
        String sql = "INSERT INTO sales (date, total,  product_id, user_id, payment_method) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(sale.getDate()));
            ps.setFloat(2, sale.getProduct().getPrice());
            ps.setInt(3, sale.getProduct().getId());
            ps.setInt(4, sale.getUser().getId());
            ps.setString(5, sale.getPaymentMethod());

            ps.executeUpdate();
            deleteDebt(id);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public Object selectProduct(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

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

    private void deleteDebt(int id) {
        String sql = "DELETE FROM loans WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void insertDebt(Debt debt) {
        String sql = "INSERT INTO loans (date, productLoan_id, userLoan_id, customerLoan_id, total, payment_method) VALUES (?, ?, ?, ?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(debt.getDate()));
            ps.setInt(2, debt.getProduct().getId());
            ps.setInt(3, debt.getUser().getId());
            ps.setString(4, debt.getCustomer().getCedula());
            ps.setFloat(5, debt.getProduct().getPrice());
            ps.setString(6, debt.getPaymentMethod());

            ps.executeUpdate();
            //se actualiza el stock
            productdao.deleteProduct(debt.getProduct().getId(), 1);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public Debt selectDebt(int id) {
        String sql = "SELECT * FROM loans AS l JOIN products AS p ON l.productLoan_id = p.id JOIN users AS u ON l.userLoan_id = u.id "
                + " JOIN customers AS c ON l.customerLoan_id = c.cedula WHERE l.id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // Product data
                int productId = rs.getInt("p.id");
                Product product = (Product) productdao.selectEntity(productId);

                // User data
                int userId = rs.getInt("u.id");
                User user = selectUser(userId);

                //customer data
                String cedula = rs.getString("c.cedula");
                Customer customer = (Customer) selectEntity(cedula);

                // Sale data
                LocalDate debtDate = rs.getDate("l.date").toLocalDate();
                float total = rs.getFloat("l.total");
                String payment = rs.getString("l.payment_method");

                return new Debt(id, product, user, customer, debtDate, total, payment);
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

    public Object selectEntity(Object obj) {
        String sql = "SELECT * FROM customers AS c JOIN emergency_contacts AS ec ON ec.customer_id = c.cedula JOIN memberships AS m ON m.customer_id = c.cedula WHERE cedula = ?";

        String cedula = (String) obj;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Customer data
                String fullname = rs.getString("c.fullname");
                String phoneNumber = rs.getString("c.phone_number");
                LocalDate birthDate = rs.getDate("c.birth_date").toLocalDate();
                int age = rs.getInt("c.age");
                String healthInformation = rs.getString("c.health_information");

                // Emergency contact data
                String emcFullname = rs.getString("ec.fullname");
                String emcPhoneNumber = rs.getString("ec.phone_number");

                // Membership data
                int mId = rs.getInt("m.id");
                MembershipType mType = MembershipType.valueOf(rs.getString("m.type"));
                LocalDate mStartDate = rs.getDate("m.start_date").toLocalDate();
                LocalDate mEndDate = rs.getDate("m.end_date").toLocalDate();
                String paymentMethod = rs.getString("m.payment_method");
                float price = rs.getFloat("m.price");
                int remainingDays = rs.getInt("m.remaining_days");

                // Seller data
                User seller = selectUser(rs.getInt("m.user_id"));

                EmergencyContact emc = new EmergencyContact(emcFullname, emcPhoneNumber);
                Membership membership = new Membership(mId, mType, mStartDate, mEndDate, remainingDays, price, seller, paymentMethod);

                return new Customer(birthDate, age, emc, membership, healthInformation, fullname, phoneNumber, cedula);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

}
