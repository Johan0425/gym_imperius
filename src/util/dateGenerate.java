/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author joanp
 */
public class dateGenerate {

//    private static LocalDate startdate;
//    private static LocalDate endDate;
//
//    public dateGenerate(LocalDate startdate, LocalDate endDate) {
//        this.startdate = startdate;
//        this.endDate = endDate;
//    }
    public static int calculateDaysReaminingWithDates(LocalDate startdate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startdate, endDate);
    }

}
