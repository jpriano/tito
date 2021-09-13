package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PersonasModel;

public interface PersonasRepository extends JpaRepository<PersonasModel, Long> {
	List<PersonasModel> findByDni(String dni);
	List<PersonasModel> findByApellido(String apellido);
}
