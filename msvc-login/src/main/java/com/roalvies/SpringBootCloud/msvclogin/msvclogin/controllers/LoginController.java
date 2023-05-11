package com.roalvies.SpringBootCloud.msvclogin.msvclogin.controllers;

import com.roalvies.SpringBootCloud.msvclogin.msvclogin.models.entity.Login;
import com.roalvies.SpringBootCloud.msvclogin.msvclogin.services.LoginService;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService service;

    @GetMapping("/")
    public List<Login> listar() { //creamos una lista de Login y lo asignamos a listar
        return service.listar(); //retornamos la respuesta del service
    }

    @GetMapping("/{id}") //El navegador entregara un id metodo get entrega
    public ResponseEntity<?> detalle(@PathVariable Long id) { //publicamos una lista Response con lo que llegue de id
        Optional<Login> loginOptional = service.porId(id); //Al valor optional que le damos al id en service le asignamos la respues de la consulta por id
        if(loginOptional.isPresent()){ //Si esta presente
            return ResponseEntity.ok(loginOptional.get()); //retorna 200 q es ok en json
        }
        return ResponseEntity.notFound().build();  //Si no esta reporta 404
    }

    @PostMapping("/")  //Metodo post inserta datos
    public ResponseEntity<?> crear(@Valid @RequestBody Login login, BindingResult result){ //Con @valid tomamos la clase validacion y con Bindin esperamos el resultado de la validacion
        if(result.hasErrors()){ //Si result tiene errores
            return validacion(result); //Retornamos el resultado del metodo creado al final de esta clase
        }

        if(!login.getCorreo().isBlank() && service.porCorreo(login.getCorreo()).isPresent()){//! distinto (si correo es distinto a vacio y el correo esta presente)
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje","Ya existe un usuario con ese correo"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(login));
    }



    @PutMapping("/{id}")   //Metodo Put para editar o tomar datos del json
    public ResponseEntity<?> editar(@Valid @RequestBody Login login, BindingResult result ,@PathVariable Long id) {//publicamos el metodo editar

        //Validacion   Blancos y nulos          //Para validar correctanebte siempre el metodo BindinResult debe ir luego de q se validara
        if(result.hasErrors()){//si has-> tiene errores
            return validacion(result);
        }

        Optional<Login> loginop = service.porId(id);
        if(loginop.isPresent()) {
            Login logindb = loginop.get();

            //Validacion no se repita campo correo ----------------------------------------------------Aqui cambiamos por el nuevo metodo existe(boolean)
            if(!login.getCorreo().isBlank() && !login.getCorreo().equalsIgnoreCase(logindb.getCorreo()) && service.existePorCorreo(login.getCorreo())){ //!->distinto el correo.equals Ignora mayuscula y minusca (del dato correo de la bd) y debe estar presente
                return ResponseEntity.badRequest().body(Collections.singletonMap("Mensaje1","Unido Mensaje2(Ya existe correo)"));
            }

            logindb.setNombre(login.getNombre());
            logindb.setApellido(login.getApellido());
            logindb.setLogin(login.getLogin());
            logindb.setClave(login.getClave());
            logindb.setEdad(login.getEdad());
            logindb.setCorreo(login.getCorreo());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(logindb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Login> perro = service.porId(id);{
            if(perro.isPresent()){
                service.eliminar(id);

                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/login-por-rol")
    public ResponseEntity<?> obtenerLoginPorRol(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    //Metodo Creado atraves de la app con refactorizacion de metodos
    private static ResponseEntity<Map<String, String>> validacion(BindingResult result) {
        Map<String, String> errores = new HashMap<>(); //El error lo debemos pasar a un json, por lo que para unir el valor con la respúesta lo pasamos a un HasMao
        result.getFieldErrors().forEach(err -> { //getFieldError lista que guarda cada campo con error - For arreglo que recorre lista
            errores.put(err.getField(),"El campo " + err.getField() + "" + err.getDefaultMessage());
        });//Por cada campo con error lo guardamos en el mapa y lo vamos actualizando con put
        ////en el ciclo for tomca cada campo con error y agrega (error.campo con errror,"el campo XXX error XXXX")
        return ResponseEntity.badRequest().body(errores);
    }

}
