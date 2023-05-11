package com.roalvies.SpringBootCloud.msvclogin.msvclogin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-web" , url = "localhost:9081")
public interface WebClientRest {

    @DeleteMapping("/eliminar-rol-login/{id}")
    void eliminarRolLoginPorId(@PathVariable Long id);

}
