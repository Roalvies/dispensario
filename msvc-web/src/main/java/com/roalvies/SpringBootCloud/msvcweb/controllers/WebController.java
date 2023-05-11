package com.roalvies.SpringBootCloud.msvcweb.controllers;

import com.roalvies.SpringBootCloud.msvcweb.models.Login;
import com.roalvies.SpringBootCloud.msvcweb.models.entity.Web;
import com.roalvies.SpringBootCloud.msvcweb.services.WebService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WebController {

    @Autowired
    private WebService service;

    @GetMapping("/")
    public List<Web> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
       Optional<Web> vaca = service.porIdConLogins(id);//service.porId(id);
       if (vaca.isPresent()){
          return ResponseEntity.ok(vaca.get());
       }
       return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Web web, BindingResult result){
        if(result.hasErrors()){
            return validacion(result);
        }

        Web webDb = service.guardar(web);
        return ResponseEntity.status(HttpStatus.CREATED).body(webDb);
    }

    @PutMapping("/{id}") //Metodo Put para actualizar atraves del id
    public ResponseEntity<?> guardar(@Valid @RequestBody Web web, BindingResult result, @PathVariable Long id){ //Publicamos un respon que tomara lo q llegue e atraves de la respuesta del body, con el id
        if (result.hasErrors()){
            return validacion(result);
        }

        Optional<Web> caballo = service.porId(id); //tomamos la respuesta del servicio select x id y lo  pasamos a la variable caballo
        if(caballo.isPresent()){ //Si el select x id esta presente en la variable caballo
            Web webDb = service.guardar(web); //toma el servicio guardar con la respuesta del id xq la variable es minuscula y pasalo a la variable de bd webDb
            webDb.setRol(web.getRol());//trae la respuesta de la bd.setea el cambiodel json en(la respuesta del select .get)

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(webDb));//retorna la respuesta del json status 200
        }//Creado.cuerpo guardar el campo sesion---- Si no esta presente
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Web> gato = service.porId(id);
        if (gato.isPresent()){
            service.eliminar(gato.get().getId());

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    //Metodo para editar, le creamos el contexto /asignar-rol/rolId
    @PutMapping("/asignar-rol/{rolId}")
    //creamos el metodo asignarLogin segun lo que recibamos del web con responseEntity
    public ResponseEntity<?> asignarLogin(@RequestBody Login login, @PathVariable Long rolId){
        Optional<Login> sapo ;
        //Entra al try y catch
        try {
           sapo = service.asignarLoginRol(login, rolId); //Llamamos la clase puente(la asignamos a sapo) entre un servicio y otro. Solicitando el RolId para el login y un rol
        }
        catch(FeignException e){//Si no esta controlamos el error
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                        body(Collections.singletonMap("mensaje","alo No existe el login para el id o error en la comunicacion:"
                                + e.getMessage()));
        }//Si no esta el login en el rol informamos atraves de https un status no encontrado y mandamos el mensaje
        //invocamos la collecion singleMap para mandar 2 mensajes

        if (sapo.isPresent()){

            return ResponseEntity.status(HttpStatus.CREATED).body(sapo.get());
        }
        return ResponseEntity.notFound().build();
    }

    //Metodo para editar, le creamos el contexto /crearLogin/rolId
    @PostMapping("/crear-rol/{rolId}")
    //creamos el metodo asignarLogin segun lo que recibamos del web con responseEntity
    public ResponseEntity<?> crearLogin(@RequestBody Login login, @PathVariable Long rolId){
        Optional<Login> sapo ;
        //Entra al try y catch
        try {
            sapo = service.crearLoginRol(login, rolId); //Llamamos la clase puente(la asignamos a sapo) entre un servicio y otro. Solicitando el RolId para el login y un rol
        }catch(FeignException e){//Si no esta controlamos el error
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No se pudo crear el login ppara el id o error en la comunicacion:" + e.getMessage()));
        }//Si no esta el login en el rol informamos atraves de https un status no encontrado y mandamos el mensaje
        //invocamos la collecion singleMap para mandar 2 mensajes

        if (sapo.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(sapo.get());
        }
        return ResponseEntity.notFound().build();
    }


    //Metodo para editar, le creamos el contexto /eliminar-login/rolId
    @DeleteMapping("/eliminar-login/{rolId}")
    //creamos el metodo eliminarLogin segun lo que recibamos del web con responseEntity
    public ResponseEntity<?> eliminarLogin(@RequestBody Login login, @PathVariable Long rolId){
        Optional<Login> sapo ;
        //Entra al try y catch
        try {
            sapo = service.eliminarLoginRol(login, rolId); //Llamamos la clase puente(la asignamos a sapo) entre un servicio y otro. Solicitando el RolId para el login y un rol
        }
        catch(FeignException e){//Si no esta controlamos el error
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("mensaje","No existe el login para el id o error en la comunicacion:"
                            + e.getMessage()));
        }//Si no esta el login en el rol informamos atraves de https un status no encontrado y mandamos el mensaje
        //invocamos la collecion singleMap para mandar 2 mensajes

        if (sapo.isPresent()){

            return ResponseEntity.status(HttpStatus.OK).body(sapo.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-rol-login/{id}")
    public ResponseEntity<?> eliminarRolLoginPorId(@PathVariable Long id){
        service.eliminarRolLoginPorId(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo validacion
    private static ResponseEntity<Map<String, String>> validacion(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(),"El campo " + err.getField() + "" + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
