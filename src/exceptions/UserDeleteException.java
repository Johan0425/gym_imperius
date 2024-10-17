/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author joanp
 */
public class UserDeleteException extends RuntimeException {

    public UserDeleteException() {
        super("No se puede eliminar el usuario, tiene deudas pendientes.");
    }

}
