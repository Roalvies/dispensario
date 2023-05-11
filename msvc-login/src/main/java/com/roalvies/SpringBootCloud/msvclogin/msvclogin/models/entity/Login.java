package com.roalvies.SpringBootCloud.msvclogin.msvclogin.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="w_login") //Nombre de la tabla

public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotEmpty //Validamos que no sea null
    private String login;

    @Column(unique = true)
    @NotEmpty //Validamos que no sea null
    private String correo;

    @NotEmpty //Validamos que no sea null
    private String nombre;

    @NotEmpty //Validamos que no sea null
    private String apellido;

    @NotBlank //Validamos que no sea blanco y vacio
    private String clave;
    private int edad;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}




