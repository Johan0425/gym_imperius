/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.Role;
import interfaces.DaoInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Trainer;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class TrainerDao implements DaoInterface {

    private final Connection con;
    private final UserDao userDao;

    public TrainerDao() {
        con = Singleton.getINSTANCE().getConnection();
        userDao = new UserDao();
    }

    @Override
    public ArrayList<Object> listEntity() {
        var trainers = new ArrayList<Object>();
        String sql = "SELECT * FROM employees WHERE role = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, Role.ENTRENADOR.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cedula = rs.getString("cedula");
                String fullname = rs.getString("fullname");
                String phoneNumber = rs.getString("phone_number");
                String password = rs.getString("password");

                Trainer trainer = new Trainer(fullname, phoneNumber, cedula, password);
                trainers.add(trainer);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return trainers;
    }

    @Override
    public Object selectEntity(Object obj) {
        String sql = "SELECT * FROM employees WHERE cedula = ?";
        String cedula = (String) obj;

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

    @Override
    public void insertEntity(Object obj) {
        String sql = "INSERT INTO employees (cedula, fullname, phone_number, role, password) VALUES (?, ?, ?, ?, ?)";
        Trainer trainer = (Trainer) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trainer.getCedula());
            ps.setString(2, trainer.getFullname());
            ps.setString(3, trainer.getPhoneNumber());
            ps.setString(4, trainer.getRole().toString());
            ps.setString(5, trainer.getPassword());

            ps.executeUpdate();
            // Se inserta también en la tabla Users, puesto que el entrenador será un usuario del sistema
            userDao.insertEntity(trainer);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void updateEntity(Object obj) {
        String sql = "UPDATE employees SET phone_number = ?, password = ? WHERE cedula = ?";
        Trainer trainer = (Trainer) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trainer.getPhoneNumber());
            ps.setString(2, trainer.getPassword());
            ps.setString(3, trainer.getCedula());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void deleteEntity(Object obj) {
        String sql = "DELETE FROM employees WHERE cedula = ?";
        String cedula = (String) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.executeUpdate();
            // Se elimina también en la tabla Users, puesto que el entrenador es un usuario del sistema.
            userDao.deleteEntity(cedula);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

}

