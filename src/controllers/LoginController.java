package controllers;

import dao.LoginDao;
import model.User;

/**
 * Controlador para buscar el usuario que desea ingresar al sistema.
 *
 * @author joanp
 */
public class LoginController {

    private final LoginDao loginDao;

    public LoginController() {
        loginDao = new LoginDao();
    }

    /**
     * Busca y devuelve un objeto User si las credenciales con válidas
     *
     * @param cedula Número de cédula del usuario.
     * @param password Contraseña del usuario.
     * @return Objeto User si las credenciales son válidas; de lo contrario,
     * null.
     */
    public User selectUser(String cedula, String password) {
        return loginDao.selectUser(cedula, password);
    }
}
