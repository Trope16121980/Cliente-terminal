
package modelo;

import java.io.Serializable;

/**
 * @author Gustavo Senoráns Varela
 * @version 1.4, 15/11/2023
 * @since jdk 17
 */

public class Jornada implements Serializable{
    
    /**
     * @param serialVersionUID número de identificación de la clase Jornada
     */
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String dni;
    private String nom;
    private String apellido;
    private String codicard;
    private String horaentrada;
    private String horasalida;
    private String total;
    private String fecha;

    /**
     * Clase Jornada
     * @param dni dni del empleado que quiere
     * iniciar la jornada y para que esto se pueda
     * llevar a caba anteriomente hay que insertar el 
     * empleado en la tabla empleado
     * @param nom nombre del empleado
     * @param apellido apellido del empleado
     * @param codicard codigo de tarjeta del empleado
     * @param horaentrada hora de inicio de jornada del empleado
     * @param fecha fecha de inicio de jornada del empleado
     */
    public Jornada(String dni, String nom, String apellido, String codicard, String horaentrada, String fecha) {
        this.dni = dni;
        this.nom = nom;
        this.apellido = apellido;
        this.codicard = codicard;
        this.horaentrada = horaentrada;
        this.fecha = fecha;
    }
    
    /**
      * @param dni dni del empleado que quiere
     * iniciar la jornada y para que esto se pueda
     * llevar a caba anteriomente hay que insertar el 
     * empleado en la tabla empleado
     * @param nom nombre del empleado
     * @param apellido apellido del empleado
     * @param codicard codigo de tarjeta del empleado
     * @param horaentrada hora de inicio de jornada del empleado
     * @param horasalida hora de finalización de la jornada laboral
     * @param total tiempo total de la jornada laboral
     * @param fecha fecha de inicio de jornada del empleado
     */
    public Jornada(String dni, String nom, String apellido, String codicard, String horaentrada, String horasalida, String total, String fecha) {
        this.dni = dni;
        this.nom = nom;
        this.apellido = apellido;
        this.codicard = codicard;
        this.horaentrada = horaentrada;
        this.horasalida = horasalida;
        this.total = total;
        this.fecha = fecha;
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

    public String getCodicard() {
        return codicard;
    }

    public void setCodicard(String codicard) {
        this.codicard = codicard;
    }

    public String getHoraentrada() {
        return horaentrada;
    }

    public void setHoraentrada(String horaentrada) {
        this.horaentrada = horaentrada;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
