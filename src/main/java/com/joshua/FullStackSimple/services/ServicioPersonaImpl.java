package com.joshua.FullStackSimple.services;

import com.joshua.FullStackSimple.dao.PersonasDao;
import com.joshua.FullStackSimple.entities.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPersonaImpl implements IServicioPersona {

    @Autowired
    private PersonasDao personasDao;

    @Override
    public List<Persona> findAll() {
        return ((List<Persona>) personasDao.findAll());
    }

    @Override
    public Persona findById(Long id) {
        return personasDao.findById(id).orElse(null);
    }

    @Override
    public Persona save(Persona p) {
        return personasDao.save(p);
    }

    @Override
    public void deleteById(Long id) {
        personasDao.deleteById(id);
    }
}
