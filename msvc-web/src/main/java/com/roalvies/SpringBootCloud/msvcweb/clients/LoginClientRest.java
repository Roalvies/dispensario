package com.roalvies.SpringBootCloud.msvcweb.clients;

import com.roalvies.SpringBootCloud.msvcweb.models.Login;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Cliente Feign q expone los servicios para el otro microservicio
@FeignClient(name="msvc-login", url = "localhost:9080")
public interface LoginClientRest {

    @GetMapping("/{id}")
    Login detalle(@PathVariable Long id);

    @PostMapping("/")
    Login crear(@RequestBody Login login);

    @PutMapping("/{id}")
    Login editar(@RequestBody Login login);

    //Implementamos el obtener de la clase Login del otro micro servicio
    @GetMapping("/login-por-rol")
    List<Login> obtenerLoginPorRol(@RequestParam Iterable<Long> ids);
}
