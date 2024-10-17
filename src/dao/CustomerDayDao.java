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
import model.EmergencyContact;
import model.Membership;
import model.User;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class CustomerDayDao {

    private final Connection con;

    public CustomerDayDao() {
        this.con = Singleton.getINSTANCE().getConnection();
    }

    public void insertEntityDay(Object obj) {
        String sql = "INSERT INTO customers (cedula, fullname, phone_number) VALUES "
                + "(?, ?, ?)";
        Customer customer = (Customer) obj;
        Membership membership = customer.getMembership();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getCedula());
            ps.setString(2, customer.getFullname());
            ps.setString(3, customer.getPhoneNumber());

            ps.executeUpdate();
            insertCustomerMembership(customer.getCedula(), membership);

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    private void insertCustomerMembership(String customerId, Membership membership) {
        String sql = "INSERT INTO memberships (type, start_date, end_date, price, customer_id, user_id, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, membership.getType().toString());
            ps.setDate(2, java.sql.Date.valueOf(membership.getStartDate()));
            ps.setDate(3, java.sql.Date.valueOf(membership.getEndDate()));
            ps.setFloat(4, membership.getType().getPrice());
            ps.setString(5, customerId);
            ps.setInt(6, membership.getSeller().getId());
            ps.setString(7, membership.getPaymentMethod());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public ArrayList<Object> listEntityDay() {
        var customers = new ArrayList<Object>();
        String sql = "SELECT * FROM customers AS c JOIN memberships AS m ON m.customer_id = c.cedula";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Customer data
                String cedula = rs.getString("c.cedula");
                String fullname = rs.getString("c.fullname");
                String phoneNumber = rs.getString("c.phone_number");

                // Membership data
                int mId = rs.getInt("m.id");
                MembershipType mType = MembershipType.valueOf(rs.getString("m.type"));
                LocalDate mStartDate = rs.getDate("m.start_date").toLocalDate();
                LocalDate mEndDate = rs.getDate("m.end_date").toLocalDate();
                String paymentMethod = rs.getString("m.payment_method");
                float price = rs.getFloat("m.price");
                int remainingDays = rs.getInt("m.remaining_days");

                // Seller data
                User seller = selectSeller(rs.getInt("m.user_id"));

                Membership membership = new Membership(mId, mType, mStartDate, mEndDate, remainingDays, price, seller, paymentMethod);

                Customer customer = new Customer(fullname, phoneNumber, cedula, membership);
                customers.add(customer);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return customers;
    }

    private User selectSeller(int id) {
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
