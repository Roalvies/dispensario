package com.roalvies.SpringBootCloud.msvcweb.services;

import com.roalvies.SpringBootCloud.msvcweb.models.Login;
import com.roalvies.SpringBootCloud.msvcweb.models.entity.Web;

import java.util.List;
import java.util.Optional;

public interface WebService {
    List<Web> listar();
    Optional<Web> porId(Long id);
    Web guardar(Web web);
    void eliminar(Long id);

    //Metodo que agrupa todo los id y los guarda en una lsita
    Optional<Web> porIdConLogins(Long id);

    //Metodo para q se elimine un login de un rol desde la eliminmacion de logins
    void eliminarRolLoginPorId(Long id);

    //Con estos metodos comenzaremos a invocar la tabla de Login del otro servicio creado(Mysql), para agregar login al rol
    Optional<Login> asignarLoginRol(Login login, Long rolId);
    Optional<Login> crearLoginRol(Login login, Long rolId);
    Optional<Login> eliminarLoginRol(Login login, Long rolID);

}
