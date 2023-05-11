package com.roalvies.SpringBootCloud.msvclogin.msvclogin.services;

import com.roalvies.SpringBootCloud.msvclogin.msvclogin.models.entity.Login;

import java.util.List;
import java.util.Optional;

public interface LoginService {
    List<Login> listar();
    Optional<Login> porId(Long id);
    Login guardar(Login login);
    void eliminar(Long id);

    //Nuevo metodo que trae todos los ids
    List<Login> listarPorIds(Iterable<Long> ids);

    //Metodo desde repositories(done se crear las consultas manuales)
    Optional<Login> porCorreo(String correo);

    Optional<Login> porCorreoHrno(String correo);

    //Ultimo metodo boolean, se crea este metodo con un boolean antres aca luego en service impl
    boolean existePorCorreo(String correo);
}
