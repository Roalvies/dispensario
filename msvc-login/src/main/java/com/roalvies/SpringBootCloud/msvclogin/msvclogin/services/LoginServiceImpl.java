package com.roalvies.SpringBootCloud.msvclogin.msvclogin.services;

import com.roalvies.SpringBootCloud.msvclogin.msvclogin.client.WebClientRest;
import com.roalvies.SpringBootCloud.msvclogin.msvclogin.models.entity.Login;
import com.roalvies.SpringBootCloud.msvclogin.msvclogin.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository repository;

    @Autowired
    private WebClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Login> listar() {
        return (List<Login>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Login> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Login guardar(Login login) {
        return repository.save(login);
    }

    @Override
    @Transactional //Cuando finaliza el metodo hace el commit o guarda en bd
    public void eliminar(Long id) {
        repository.deleteById(id);
        client.eliminarRolLoginPorId(id); //Usamos el cliente feign para llamar al otro microservicio y el metodo eliminarRolLoginPorIr
    }

    @Override
    @Transactional(readOnly = true)
    public List<Login> listarPorIds(Iterable<Long> ids) {
        return (List<Login>) repository.findAllById(ids);
    }

    @Override
    public Optional<Login> porCorreo(String correo) {
        return repository.findByCorreo(correo);
    }

    //Metodo Perzonalizado
    @Override
    public Optional<Login> porCorreoHrno(String correo) {
        return repository.porCorreoHrnito(correo);
    }


    //Se debe agregar este metodo luego de agregarlo en Login Service
    @Override
    public boolean existePorCorreo(String correo) {
        return repository.existsByCorreo(correo);
    }


}
