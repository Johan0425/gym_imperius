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
import model.User;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class UserDao implements DaoInterface {

    private final Connection con;

    public UserDao() {
        con = Singleton.getINSTANCE().getConnection();
    }

    @Override
    public ArrayList<Object> listEntity() {
        var users = new ArrayList<Object>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String cedula = rs.getString("cedula");
                String password = rs.getString("password");
                Role role = Role.valueOf(rs.getString("role"));

                User user = new User(id, cedula, role, password);
                users.add(user);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return users;
    }

    @Override
    public Object selectEntity(Object obj) {
        String sql = "SELECT * FROM users WHERE cedula = ?";
        String cedula = (String) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                Role role = Role.valueOf(rs.getString("role"));

                return new User(id, cedula, role, password);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    @Override
    public void insertEntity(Object obj) {
        String sql = "INSERT INTO users (cedula, password, role) VALUES (?, ?, ?)";
        User user = (User) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getCedula());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void updateEntity(Object obj) {
        String sql = "UPDATE users SET password = ? WHERE cedula = ?";
        User user = (User) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getCedula());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    @Override
    public void deleteEntity(Object obj) {
        String sql = "DELETE FROM users WHERE cedula = ?";
        String cedula = (String) obj;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

}

