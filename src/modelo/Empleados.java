
package modelo;

import java.io.Serializable;

/**
 *
 * @author Gustavo_Senorans
 */
public class Empleados implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String dni;
    private String nom;
    private String apellido;
    private String nomempresa;
    private String departament;
    private int codicard;
    private String mail;
    private int telephon;

    public Empleados(String dni, String nom, String apellido, String nomempresa, String departament, int codicard, String mail, int telephon) {
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

    public int getCodicard() {
        return codicard;
    }

    public void setCodicard(int codicard) {
        this.codicard = codicard;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getTelephon() {
        return telephon;
    }

    public void setTelephon(int telephon) {
        this.telephon = telephon;
    }
    
    
}
