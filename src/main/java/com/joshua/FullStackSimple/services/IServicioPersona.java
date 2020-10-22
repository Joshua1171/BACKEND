package com.joshua.FullStackSimple.services;

import com.joshua.FullStackSimple.entities.Persona;

import java.util.List;

public interface IServicioPersona {

    public List<Persona> findAll();
    public Persona findById(Long id);
    public Persona save(Persona p);
    public void deleteById(Long id);


}
