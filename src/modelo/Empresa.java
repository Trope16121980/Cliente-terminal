
package modelo;

import java.io.Serializable;

/**
 * @author Gustavo Senoráns Varela
 * @version 1.4, 15/11/2023
 * @since jdk 17
 */

public class Empresa implements Serializable{
     /**
     * @param serialVersionUID número de identificación de la clase Empresa
     */
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String nom;
    private String address;
    private String telephon;

    /**
     * Clase Empresa
     * @param nom nombre de la empresa
     * @param address dirección de la empresa
     * @param telephon número de teléfono de la empresa
     */
    
    public Empresa(String nom, String address, String telephon) {
        this.nom = nom;
        this.address = address;
        this.telephon = telephon;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }
    
}
