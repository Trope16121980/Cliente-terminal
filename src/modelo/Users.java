/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Gustavo_Senorans
 */
public class Users implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String login;
    private String pass;
    private int numtipe;
    private String dni;
    private int codigo;

    public Users(String login, String pass, int numtipe, String dni, int codigo) {
        this.login = login;
        this.pass = pass;
        this.numtipe = numtipe;
        this.dni = dni;
        this.codigo = codigo;
    }

    public Users(String login, String pass, int numtipe, String dni) {
        this.login = login;
        this.pass = pass;
        this.numtipe = numtipe;
        this.dni = dni;
    }

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
