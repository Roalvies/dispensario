package com.roalvies.SpringBootCloud.msvcweb.models.entity;


import com.roalvies.SpringBootCloud.msvcweb.models.Login;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "w_rol" )

public class Web {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty //Validamos q no este vacio
    private String rol;
    private String descripcion;

    //Indicamos que la regla sera 1 a muchos, con esto generamos cascada, que si borramos un rol, se quitan todos los login,
    //ademas, no deja registros huerfanos
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //Creammos una lista en la clase web donde esta el rol,ya que1 rol puede tener muchos usuarios
    //Agregamos un foreign key rol_id a la tabla w_rol
    @JoinColumn(name="rol_id")
    private List<LoginWeb> loginWebs;

    //No es un campo que este en la tabla - lista de logins para poder poblar los datos completos
    @Transient
    private List<Login> logins;

    //Creamos un constructor vacio con arraylist para guardar la respuesta
    public Web() {
        loginWebs = new ArrayList<>();
        logins = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    //Generamos Getter and setters de el metodo creado
    public List<LoginWeb> getLoginWebs() {
        return loginWebs;
    }

    public void setLoginWebs(List<LoginWeb> loginWebs) {
        this.loginWebs = loginWebs;
    }

    //Creamos un metodo addLoginWeb para ir añadiendo logins a los roles, que guardara en el array loginWebs, el resultado de loginWeb sin la s
    public void addLoginWeb(LoginWeb loginWeb){
        loginWebs.add(loginWeb);
    }

    //Public void (void es que no necesita un return por lo general se usa en metodos)
    //Metodo remove
    public void removeLoginWeb(LoginWeb loginWeb){
        loginWebs.remove(loginWeb);   //Quita de la lista
    }
    //Con este metodo es necesario agregar en la clase LoginWeb, un metodo para buscar xq si esta el usuario o no
    public List<Login> getLogins() {
        return logins;
    }

    public void setLogins(List<Login> logins) {
        this.logins = logins;
    }
}
