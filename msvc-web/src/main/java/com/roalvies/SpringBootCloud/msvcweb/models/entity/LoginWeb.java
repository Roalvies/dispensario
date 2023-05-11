package com.roalvies.SpringBootCloud.msvcweb.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "w_rol_login")
public class LoginWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Le asignamos un nombre personalizado al campo
    @Column(name = "rol_login", unique = true)
    private Long rolUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRolUser() {
        return rolUser;
    }

    public void setRolUser(Long rolUser) {
        this.rolUser = rolUser;
    }


    //Vamos a sobrescribir el resultado del metodo removeLoginWeb de la clase Web con Override
    @Override
    //boolean es true o false
    public boolean equals(Object obj) { //Sobrescriremos el metodo equals del objeto obj
        if (this == obj){ // si la instancia es igual a obj devolvemos true
            return true;
        }
        if(!(obj instanceof LoginWeb)){   //Si el objeto no es una instancia de (instanceof) LoginWeb
            return false;
        }
        LoginWeb logi = (LoginWeb) obj; //Una vez que hizo la validacion si trae un obj lo pasa a LoginWeb
        return this.rolUser != null && this.rolUser.equals(logi.rolUser); //retorna  comparacion el valor el campo rolUser si es distinto a null y usuario es igual al objeto
    }
}
