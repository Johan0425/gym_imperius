/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import enums.Role;
import interfaces.LoginDaoInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Trainer;
import org.mariadb.jdbc.Connection;
import model.User;
import singleton.Singleton;

/**
 *
 * @author joanp
 */
public class LoginDao implements LoginDaoInterface {

    private final Connection con;

    public LoginDao() {
        con = Singleton.getINSTANCE().getConnection();
    }

    @Override
    public User selectUser(String cedula, String password) {
        String sql = "SELECT * FROM users WHERE cedula = ? AND password = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                Role role = Role.valueOf(rs.getString("role"));

                switch (role) {
                    case ADMIN:
                        return new User(id, cedula, role, password);
                    case ENTRENADOR:
                        return selectTrainer(id, cedula);
                    default:
                        break;
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    private Trainer selectTrainer(int trainerId, String cedula) {
        String sql = "SELECT * FROM  employees WHERE cedula = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cedula);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String fullname = rs.getString("fullname");
                String phoneNumber = rs.getString("phone_number");
                String password = rs.getString("password");

                return new Trainer(trainerId, fullname, phoneNumber, cedula, password);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

}
