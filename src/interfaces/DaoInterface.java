/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.ArrayList;
import java.sql.SQLException;


/**
 *
 * @author joanp
 */
public interface DaoInterface {
    
    ArrayList<Object> listEntity();

    Object selectEntity(Object obj);

    void insertEntity(Object obj);

    void updateEntity(Object obj);

    void deleteEntity(Object obj);

}
