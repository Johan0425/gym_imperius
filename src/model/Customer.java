/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enums.Role;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author joanp
 */
public class Customer extends Person {

    private LocalDate birthDate;
    private int age;

    private EmergencyContact emergencyContact;
    private Membership membership;
    private String healthInformation;

    public Customer(String fullname, String phoneNumber, String cedula, Membership membership) {
        super(fullname, phoneNumber, cedula, Role.CLIENTE, null);
        this.membership = membership;
    }

    public Customer(LocalDate birthDate, EmergencyContact emergencyContact, Membership membership, String healthInformation, String fullname, String phoneNumber, String cedula) {
        super(fullname, phoneNumber, cedula, Role.CLIENTE, null);
        this.birthDate = birthDate;
        this.emergencyContact = emergencyContact;
        this.membership = membership;
        this.healthInformation = healthInformation;
        this.age = calculateAge();
    }

    public Customer(LocalDate birthDate, int age, EmergencyContact emergencyContact, Membership membership, String healthInformation, String fullname, String phoneNumber, String cedula) {
        super(fullname, phoneNumber, cedula, Role.CLIENTE, null);
        this.birthDate = birthDate;
        this.age = age;
        this.emergencyContact = emergencyContact;
        this.membership = membership;
        this.healthInformation = healthInformation;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public String getHealthInformation() {
        return healthInformation;
    }

    public void setHealthInformation(String healthInformation) {
        this.healthInformation = healthInformation;
    }

    /**
     * Método encargado de calcular la edad de acuerdo a la fecha de nacimiento
     * del cliente.
     *
     * @return Retorna la edad calculada.
     */
    private int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

}
