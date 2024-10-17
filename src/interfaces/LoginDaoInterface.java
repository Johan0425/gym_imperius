/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import model.User;

/**
 *
 * @author joanp
 */
public interface LoginDaoInterface {
    
    User selectUser(String username, String password);
    
}
