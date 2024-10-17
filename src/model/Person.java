/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enums.Role;
import java.time.LocalDate;

/**
 *
 * @author joanp
 */
public abstract class Person extends User {

    protected final String fullname;
    protected String phoneNumber;

    public Person(String fullname, String phoneNumber, String cedula, Role role, String password) {
        super(cedula, role, password);
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
    }

    public Person(int id, String fullname, String phoneNumber, String cedula, Role role, String password) {
        super(id, cedula, role, password);
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
