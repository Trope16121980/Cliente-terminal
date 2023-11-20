package modelo;

import java.io.Serializable;

/**
 * @author Gustavo Senoráns Varela
 * @version 1.4, 15/11/2023
 * @since jdk 17
 */
public class Users implements Serializable {

    /**
     * @param serialVersionUID número de identificación de la clase Users
     */
    private static final long serialVersionUID = 6529685098267757690L;

    private String login;
    private String pass;
    private int numtipe;
    private String dni;
    private int codigo;

    /**
     * Clase Users
     *
     * @param login login del usuario
     * @param pass el password del usuario
     * @param numtipe el tipo de usuario admin / user
     * @param dni el dni del empleado al que pertenece el usuario
     * @param codigo el código que se genera para después del inicio de sesión
     */
    public Users(String login, String pass, int numtipe, String dni, int codigo) {
        this.login = login;
        this.pass = pass;
        this.numtipe = numtipe;
        this.dni = dni;
        this.codigo = codigo;
    }

    /**
     * Clase Users
     *
     * @param login login del usuario
     * @param pass el password del usuario
     * @param numtipe el tipo de usuario admin / user
     * @param dni el dni del empleado al que pertenece el usuario
     */
    public Users(String login, String pass, int numtipe, String dni) {
        this.login = login;
        this.pass = pass;
        this.numtipe = numtipe;
        this.dni = dni;
    }

    /**
     * Clase Users
     */
    public Users() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getNumtipe() {
        return numtipe;
    }

    public void setNumtipe(int numtipe) {
        this.numtipe = numtipe;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
