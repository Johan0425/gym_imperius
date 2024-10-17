/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enums.Role;

/**
 *
 * @author joanp
 */
public class Trainer extends Person {

    public Trainer(String fullname, String phoneNumber, String cedula, String password) {
        super(fullname, phoneNumber, cedula, Role.ENTRENADOR, password);
    }

    public Trainer(int id, String fullname, String phoneNumber, String cedula, String password) {
        super(id, fullname, phoneNumber, cedula, Role.ENTRENADOR, password);
    }
   
}
