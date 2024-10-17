/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enums;

/**
 *
 * @author joanp
 */
public enum MembershipType {

    DIA("Día", 5000),
    SEMANAL("Semanal", 20000),
    QUINCEDIAS("Quincenal", 35000),
    MES("Mensual", 55000),
    ESTUDIANTE("Estudiante", 50000);

    private final String name;
    private final float price;

    private MembershipType(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

}
