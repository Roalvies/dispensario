package com.roalvies.SpringBootCloud.msvclogin.msvclogin.repositories;

import com.roalvies.SpringBootCloud.msvclogin.msvclogin.models.entity.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoginRepository extends CrudRepository<Login, Long> {
    //Consulta manual por otro campo de la tabla
    Optional<Login> findByCorreo(String correo);

    //Usamos el metodo query asi personalizamos la consulta, la l es para darle una entidad a la tabla y traer 1 campo
    @Query("select l from Login l where l.correo=?1")
    Optional<Login> porCorreoHrnito(String correo);

    //Metodo boolean que solo valida si existe o no el resultado
    boolean existsByCorreo(String correo);
    //En caso de ser boolean es necesario modificar el login service

}
