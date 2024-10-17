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
public class User {

    protected final String cedula;

    protected Role role;
    protected int id;
    protected String password;

    public User(String cedula, Role role, String password) {
        this.cedula = cedula;
        this.role = role;
        this.password = password;
    }

    public User(int id, String cedula, Role role, String password) {
        this.id = id;
        this.cedula = cedula;
        this.role = role;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
