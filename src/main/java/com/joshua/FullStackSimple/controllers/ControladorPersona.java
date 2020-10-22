package com.joshua.FullStackSimple.controllers;

import com.joshua.FullStackSimple.entities.Persona;
import com.joshua.FullStackSimple.services.IServicioPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ControladorPersona {

    @Autowired
    private IServicioPersona iServicioPersona;

    @GetMapping("/personas")
    public List<Persona> index(HttpServletRequest request){
        return iServicioPersona.findAll();
    }

    @GetMapping("/personas/{id}")
    public ResponseEntity<?> mostrar (@PathVariable Long id){
        Persona persona=null;
        Map<String,Object> response=new HashMap<>();
        try{
            persona = iServicioPersona.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(persona == null) {
            response.put("mensaje", "ID del cliente: ".concat(id.toString().concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Persona>(persona,HttpStatus.OK);
    }

    @PostMapping("/personas")
    public ResponseEntity<?> crear(@Valid @RequestBody Persona persona, BindingResult result) { //Valid aplica las validaciones

        Persona p = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {

            // Mismo resultado que con lamda
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()) {
                errors.add("El campo '" + err.getField() +"' "+ err.getDefaultMessage());
            }
           /* Lambda Java
           List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList())
                    */
            response.put("errors", errors);
            new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            p = iServicioPersona.save(persona);
        } catch(DataAccessException e) {
            response.put("mensaje", "No se pudo insertar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente fue creado satisfactoriamente!");
        response.put("persona", p);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/personas/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id){
        Map<String, Object> response=new HashMap<>();
        try{
            iServicioPersona.deleteById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al hacer la consulta en la base de datos");
            response.put("error",e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The client successfully removed!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/clients/{id}")
    ResponseEntity<?> editar(@Valid @RequestBody Persona persona, BindingResult result, @PathVariable Long id) {

        Persona personaActual =iServicioPersona.findById(id);

        Persona personaActualizada = null;

        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()) {
                errors.add("El campo '" + err.getField() +"' "+ err.getDefaultMessage());
            }

            /*List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "The field'" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList())*/

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if ( personaActual == null) {
            response.put("mensaje", "Error:error al editar , ID de cliente "
                    .concat(id.toString().concat("no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            personaActual.setNombre(persona.getNombre());
            personaActual.setApellido(persona.getApellido());
            personaActual.setCorreo(persona.getCorreo());

            personaActualizada = iServicioPersona.save(personaActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar al cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "The client has been created successfully!");
        response.put("client", personaActualizada);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}

