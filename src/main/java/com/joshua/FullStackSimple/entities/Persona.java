package com.joshua.FullStackSimple.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty(message = "No puede ser vacio")
    @Size(min=4,max = 12,message = "El tamaño debe ser entre 4 y 12 caracteres")
    @Column(nullable = false)
    String nombre;

    @NotEmpty(message = "No puede ser vacio")
    @Size(min=4,max = 12,message = "El tamaño debe ser entre 4 y 12 caracteres")
    @Column(nullable = false)
    String apellido;

    @Email(message = "Correo incorrecto")
    String correo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
