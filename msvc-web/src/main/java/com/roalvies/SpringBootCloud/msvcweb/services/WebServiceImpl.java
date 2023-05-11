package com.roalvies.SpringBootCloud.msvcweb.services;

import com.roalvies.SpringBootCloud.msvcweb.clients.LoginClientRest;
import com.roalvies.SpringBootCloud.msvcweb.models.Login;
import com.roalvies.SpringBootCloud.msvcweb.models.entity.LoginWeb;
import com.roalvies.SpringBootCloud.msvcweb.models.entity.Web;
import com.roalvies.SpringBootCloud.msvcweb.repositories.WebRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WebServiceImpl implements WebService {

    @Autowired
    private WebRepository repository;

    //Sirve para traer la dependencia  en spring
    @Autowired
    private LoginClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Web> listar() {
        return (List<Web>) repository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Web> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Web guardar(Web web) {
        return repository.save(web);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Web> porIdConLogins(Long id) {
        Optional<Web> o = repository.findById(id);
        if (o.isPresent()){
            Web rol = o.get();
            if(!rol.getLoginWebs().isEmpty()){
                List<Long> ids = rol.getLoginWebs().stream().map(lr -> lr.getRolUser()).collect(Collectors.toList());

                List<Login> logins = client.obtenerLoginPorRol(ids);
                rol.setLogins(logins);
            }
            return Optional.of(rol);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarRolLoginPorId(Long id) {
        repository.eliminarRolLoginPorId(id);
    }

    //Creamos el metodo asignarLoginRol, esperamos recibir clase login y campo rol_id
    @Override
    @Transactional
    public Optional<Login> asignarLoginRol(Login login, Long rolId) {
        Optional<Web> pato = repository.findById(rolId);//llamamos de repository el metodo buscar x id llamamos rol_id y lo pasamos a pato
        if (pato.isPresent()){ //si el rol_id esta presente  en la consulkta
            Login loginMsvc = client.detalle(login.getId());//trae el get id de clase login con su detalle

            Web web = pato.get();
            LoginWeb rolLogin = new LoginWeb();
            rolLogin.setRolUser(loginMsvc.getId());

            web.addLoginWeb(rolLogin);
            repository.save(web);

            return Optional.of(loginMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Login> crearLoginRol(Login login, Long rolId) {
        Optional<Web> pato = repository.findById(rolId);//llamamos de repository el metodo buscar x id llamamos rol_id y lo pasamos a pato
        if (pato.isPresent()){ //si el rol_id esta presente  en la consulkta
            Login loginNuevoMsvc = client.crear(login);//trae el get id de clase login con su detalle

            Web web = pato.get();
            LoginWeb rolLogin = new LoginWeb();
            rolLogin.setRolUser(loginNuevoMsvc.getId());

            web.addLoginWeb(rolLogin);
            repository.save(web);

            return Optional.of(loginNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Login> eliminarLoginRol(Login login, Long rolId) {
        Optional<Web> pato = repository.findById(rolId);//llamamos de repository el metodo buscar x id llamamos rol_id y lo pasamos a pato
        if (pato.isPresent()){ //si el rol_id esta presente  en la consulkta
            Login loginMsvc = client.detalle(login.getId());//trae el get id de clase login con su detalle

            Web web = pato.get();
            LoginWeb rolLogin = new LoginWeb();
            rolLogin.setRolUser(loginMsvc.getId());

            web.removeLoginWeb(rolLogin);
            repository.save(web);

            return Optional.of(loginMsvc);
        }
        return Optional.empty();
    }
}
