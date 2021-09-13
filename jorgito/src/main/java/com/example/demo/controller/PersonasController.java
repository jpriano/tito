package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.PersonasModel;
import com.example.demo.repository.PersonasRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PersonasController {

	@Autowired
	PersonasRepository personasRepository;

	@GetMapping("/personas_apellido")
	public ResponseEntity<List<PersonasModel>> getPersonasByApellido(@RequestParam(required = false) String apellido) {
		try {
			List<PersonasModel> personasList = new ArrayList<PersonasModel>();
			if (apellido == null)
				personasRepository.findAll().forEach(personasList::add);
			else
				personasRepository.findByApellido(apellido).forEach(personasList::add);

			if (personasList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(personasList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/persona/{id}")
	public ResponseEntity<PersonasModel> getpersonasById(@PathVariable("id") long id) {
		Optional<PersonasModel> personasData = personasRepository.findById(id);

		if (personasData.isPresent()) {
			return new ResponseEntity<>(personasData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/persona_create")
	public ResponseEntity<PersonasModel> createpersona(@RequestBody PersonasModel personas) {
		try {
			PersonasModel _personas = personasRepository
					.save(new PersonasModel(personas.getDni(), personas.getNombre(), personas.getApellido()));
			return new ResponseEntity<>(_personas, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/persona_update/{id}")
	public ResponseEntity<PersonasModel> updatepersonas(@PathVariable("id") long id,
			@RequestBody PersonasModel personas) {
		Optional<PersonasModel> personasData = personasRepository.findById(id);

		if (personasData.isPresent()) {
			PersonasModel _personas = personasData.get();
			_personas.setDni(personas.getDni());
			_personas.setNombre(personas.getNombre());
			_personas.setApellido(personas.getApellido());
			return new ResponseEntity<>(personasRepository.save(_personas), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/persona_delete/{id}")
	public ResponseEntity<HttpStatus> deletepersonas(@PathVariable("id") long id) {
		try {
			personasRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/personas_delete_all")
	public ResponseEntity<HttpStatus> deleteAllpersonass() {
		try {
			personasRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/personas/{dni}")
	public ResponseEntity<List<PersonasModel>> findByPublished() {
		try {
			List<PersonasModel> personas = personasRepository.findByDni("13211994");

			if (personas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}