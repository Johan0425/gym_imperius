/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.MembershipType;
import enums.Role;
import exceptions.UserDeleteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Customer;
import model.Membership;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;
import interfaces.DaoInterface;
import javax.swing.JOptionPane;
import model.EmergencyContact;
import model.User;
import util.dateGenerate;

/**
 *
 * @author joanp
 */
public class CustomerDao implements DaoInterface {

    private final Connection con;

    public CustomerDao() {
        this.con = Singleton.getINSTANCE().getConnection();
    }

    @Override
    public ArrayList<Object> listEntity() {
        var customers = new ArrayList<Object>();
        String sql = "SELECT * FROM customers AS c JOIN emergency_contacts AS ec ON ec.customer_id = c.cedula JOIN memberships AS m ON m.customer_id = c.cedula";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Customer data
                String cedula = rs.getString("c.cedula");
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
                User seller = selectSeller(rs.getInt("m.user_id"));

                EmergencyContact emc = new EmergencyContact(emcFullname, emcPhoneNumber);
                Membership membership = new Membership(mId, mType, mStartDate, mEndDate, remainingDays, price, seller, paymentMethod);

                Customer customer = new Customer(birthDate, age, emc, membership, healthInformation, fullname, phoneNumber, cedula);
                customers.add(customer);
                
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return customers;
    }

    @Override
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
                User seller = selectSeller(rs.getInt("m.user_id"));

                EmergencyContact emc = new EmergencyContact(emcFullname, emcPhoneNumber);
                Membership membership = new Membership(mId, mType, mStartDate, mEndDate, remainingDays, price, seller, paymentMethod);

                return new Customer(birthDate, age, emc, membership, healthInformation, fullname, phoneNumber, cedula);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    @Override
    public void insertEntity(Object obj) {
        String sql = "INSERT INTO customers (cedula, fullname, phone_number, birth_date, age, health_information) VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        Customer customer = (Customer) obj;
        EmergencyContact emergencyContact = customer.getEmergencyContact();
        Membership membership = customer.getMembership();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getCedula());
            ps.setString(2, customer.getFullname());
            ps.setString(3, customer.getPhoneNumber());
            ps.setDate(4, java.sql.Date.valueOf(customer.getBirthDate()));
            ps.setInt(5, customer.getAge());
            ps.setString(6, customer.getHealthInformation());

            ps.executeUpdate();
            insertContactEmergency(customer.getCedula(), emergencyContact);
            insertCustomerMembership(customer.getCedula(), membership);

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void updateEntity(Object obj) {
        String sql = "UPDATE customers SET cedula = ?, fullname = ?, phone_number = ?, health_information = ?, birth_date = ? WHERE customers.cedula = ?";
        Customer customer = (Customer) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getCedula()); // Nuevo valor para cedula
            ps.setString(2, customer.getFullname());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getHealthInformation());
            ps.setDate(5, java.sql.Date.valueOf(customer.getBirthDate()));
            ps.setString(6, customer.getCedula()); // Valor antiguo de cedula para la cláusula WHERE

            ps.executeUpdate();
            updateContactEmergency(customer.getCedula(), customer.getEmergencyContact());

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void deleteEntity(Object obj) {
        String sql = "DELETE FROM customers WHERE cedula = ? AND NOT EXISTS (SELECT 1 FROM loans WHERE customerLoan_id = ?)";
        String cedula = (String) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setString(2, cedula);

            int rowDeleted = ps.executeUpdate();

            if (rowDeleted == 0) {
                throw new UserDeleteException();
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void editMembership(String customerId, Membership membership) {

        String sql = "UPDATE memberships SET type = ?,  start_date = ?, end_date = ?, payment_method = ? , remaining_days = ? WHERE customer_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, membership.getType().toString());
            ps.setDate(2, java.sql.Date.valueOf(membership.getStartDate()));
            ps.setDate(3, java.sql.Date.valueOf(membership.getEndDate()));
            ps.setString(4, membership.getPaymentMethod());

            int remainingDays = dateGenerate.calculateDaysReaminingWithDates(membership.getStartDate(), membership.getEndDate());
            ps.setInt(5, remainingDays);

            ps.setString(6, customerId);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void renewMembership(String customerId, Membership membership) {
        LocalDate currentDate = LocalDate.now();

        // Membresía actual
        Customer customer = (Customer) selectEntity(customerId);
        Membership previousMembership = customer.getMembership();

        LocalDate newStartDate;
        LocalDate newEndDate;

        if (currentDate.isBefore(previousMembership.getEndDate())) {
            // El cliente paga antes del vencimiento
            newStartDate = currentDate;
            newEndDate = previousMembership.getEndDate().plusDays(membership.getRemainingDays());
        } else {
            // El cliente paga después del vencimiento
            newStartDate = currentDate;
            newEndDate = membership.getEndDate();
        }

        String sql = "UPDATE memberships SET type = ?,  start_date = ?, end_date = ?, payment_method = ?, remaining_days = ? WHERE customer_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, membership.getType().toString());
            ps.setDate(2, java.sql.Date.valueOf(newStartDate));
            ps.setDate(3, java.sql.Date.valueOf(newEndDate));
            ps.setString(4, membership.getPaymentMethod());
            ps.setInt(5, membership.getRemainingDays());
            ps.setString(6, customerId);

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }

    }

    /**
     * Registra en la base de datos el contacto de emergencia después de
     * registrar un cliente.
     *
     * @param customerId Cédula del cliente.
     * @param contact Objeto EmergencyContact.
     */
    private void insertContactEmergency(String customerId, EmergencyContact contact) {
        String sql = "INSERT INTO emergency_contacts (fullname, phone_number, customer_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, contact.getFullname());
            ps.setString(2, contact.getPhoneNumber());
            ps.setString(3, customerId);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     * Actualiza en la base de datos el contacto de emergencia de un cliente.
     *
     * @param customerId Cédula del cliente.
     * @param contact Objeto EmergencyContact.
     */
    private void updateContactEmergency(String customerId, EmergencyContact contact) {
        String sql = "UPDATE emergency_contacts SET fullname = ?, phone_number = ? WHERE customer_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, contact.getFullname());
            ps.setString(2, contact.getPhoneNumber());
            ps.setString(3, customerId);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     * Registra en la base de datos la membresía adquirida por un cliente.
     *
     * @param customerId Cédula del cliente.
     * @param membership Objeto Membership.
     */
    private void insertCustomerMembership(String customerId, Membership membership) {
        String sql = "INSERT INTO memberships (type, start_date, end_date, remaining_days, price, customer_id, user_id, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, membership.getType().toString());
            ps.setDate(2, java.sql.Date.valueOf(membership.getStartDate()));
            ps.setDate(3, java.sql.Date.valueOf(membership.getEndDate()));
            ps.setInt(4, membership.getRemainingDays());
            ps.setFloat(5, membership.getType().getPrice());
            ps.setString(6, customerId);
            ps.setInt(7, membership.getSeller().getId());
            ps.setString(8, membership.getPaymentMethod());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
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
    
    private void updatedRemainingDays(int remaining_days) {
        String sql = "UPDATE memberships SET remaining_days = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, remaining_days);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
}
