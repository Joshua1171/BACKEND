package com.joshua.FullStackSimple.dao;

import com.joshua.FullStackSimple.entities.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonasDao extends CrudRepository<Persona,Long> {
}
