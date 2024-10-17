/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author joanp
 */
public class CustomerAlreadyRegisteredException extends RuntimeException {

    public CustomerAlreadyRegisteredException() {
        super("El cliente ya esta registrado");
    }
    
    
}
