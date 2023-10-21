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
public class Jornada implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    
    private String dni;
    private String nom;
    private String apellido;
    private int codicard;
    private String horaentrada;
    private String horasalida;
    private String total;
    private String fecha;

    public Jornada(String dni, String nom, String apellido, int codicard, String horaentrada, String horasalida, String total, String fecha) {
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

    public int getCodicard() {
        return codicard;
    }

    public void setCodicard(int codicard) {
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
