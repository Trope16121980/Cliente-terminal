package modelo;

import java.io.Serializable;

/**
 * @author Gustavo Senoráns Varela
 * @version 1.4, 15/11/2023
 * @since jdk 17
 */

public class Empleados implements Serializable {
    /**
     * @param serialVersionUID número de identificación de la clase Empleados
     */
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String dni;
    private String nom;
    private String apellido;
    private String nomempresa;
    private String departament;
    private String codicard;
    private String mail;
    private String telephon;

    /**
     * Clase empleados
     * @param dni dni del empleado
     * @param nom nombre del empleado
     * @param apellido apellido del empleado
     * @param nomempresa nombre de la empresa para la que trabaja el empleado
     * y esta empresa ya tiene que existir en la BBDD HREntrada
     * @param departament departamento en el que trabajará el empleado
     * @param codicard codigo de tarjeta que utilizará el empleado
     * para iniciar jornada
     * @param mail mail del empleado
     * @param telephon número de teléfono del empleado
     */
    public Empleados(String dni, String nom, String apellido, String nomempresa, String departament, String codicard, String mail, String telephon) {
        this.dni = dni;
        this.nom = nom;
        this.apellido = apellido;
        this.nomempresa = nomempresa;
        this.departament = departament;
        this.codicard = codicard;
        this.mail = mail;
        this.telephon = telephon;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNomempresa() {
        return nomempresa;
    }

    public void setNomempresa(String nomempresa) {
        this.nomempresa = nomempresa;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getCodicard() {
        return codicard;
    }

    public void setCodicard(String codicard) {
        this.codicard = codicard;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }
    
    
}
