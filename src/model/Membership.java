/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import enums.MembershipType;
import static enums.MembershipType.DIA;
import static enums.MembershipType.MES;
import static enums.MembershipType.QUINCEDIAS;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import util.dateGenerate;

/**
 *
 * @author joanp
 */
public class Membership {

    private final int remainingDays;
    private final User seller;

    private MembershipType type;

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String paymentMethod;

    public Membership(MembershipType type, User seller, String paymentMethod) {
        this.type = type;
        this.startDate = LocalDate.now();
        this.endDate = calculateEndDate(type);
        this.remainingDays = calculateDaysReamining();
        this.seller = seller;
        this.paymentMethod = paymentMethod;
    }

    public Membership(MembershipType type, LocalDate startDate, LocalDate endDate, User seller, String paymentMethod) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingDays = dateGenerate.calculateDaysReaminingWithDates(startDate, endDate);
        this.seller = seller;
        this.paymentMethod = paymentMethod;
    }

    public Membership(int id, MembershipType type, LocalDate startDate, LocalDate endDate, int remainingDays, float price, User seller, String paymentMethod) {
        this.id = id;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainingDays = calculateDaysReamining();
        this.seller = seller;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getSeller() {
        return seller;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Método encargado de calcular los días restantes de la membresía.
     *
     * @return Retorna la cantidad de días calculados.
     */
    public final int calculateDaysReamining() {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }

//    public static int calculateDaysReaminingWithDates(LocalDate startdate) {
//        return (int) ChronoUnit.DAYS.between(startdate, endDate);
//    }
    /**
     * Método encargado de calcular la fecha en la que terminará la membresía.
     *
     * @param type Tipo de membresía para determinar la cantidad de días.
     * @return Retorna la fecha calculada.
     */
    private LocalDate calculateEndDate(MembershipType type) {
        switch (type) {
            case DIA:
                return startDate.plusDays(1);
            case QUINCEDIAS:
                return startDate.plusDays(15);
            case MES:
                return startDate.plusMonths(1);
            case SEMANAL:
                return startDate.plusDays(7);
            case ESTUDIANTE:
                return startDate.plusMonths(1);
            default:
                throw new IllegalArgumentException();
        }
    }

}
