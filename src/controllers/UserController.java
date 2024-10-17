/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.UserDao;
import model.User;

/**
 *
 * @author joanp
 */
public class UserController {
    
     private final UserDao userdao;

    public UserController() {
        userdao = new UserDao();
    }

    public User selectUser(int id) {
        return (User) userdao.selectEntity(id);
    }

    public void insertUser(User user) {
        userdao.insertEntity(user);
    }

    public void updateUser(User user) {
        userdao.updateEntity(user);
    }

    public void deleteUser(int id) {
        userdao.deleteEntity(id);
    }
    
}
