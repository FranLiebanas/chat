package com.fran.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fran.entity.Contacto;
import com.fran.repository.ContactoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Api")
public class ApiContactos {

	private final ContactoRepository contactoRepository;

	public ApiContactos(ContactoRepository contactoRepository) {
		this.contactoRepository = contactoRepository;
	}

	@GetMapping("/contactos/propietario/{propietario}")
    public ResponseEntity<List<Contacto>> obtenerContactosPorPropietario(@PathVariable String propietario) {
        List<Contacto> contactos = contactoRepository.findByPropietario(propietario);
        return ResponseEntity.ok(contactos);
    }

	// Crear un nuevo contacto
	@PostMapping("/contactos")
	public Contacto crearContacto(@RequestBody Contacto contacto) {
		return contactoRepository.save(contacto);
	}

	

	// Eliminar un contacto por su ID
	@DeleteMapping("/contactos/{id}")
	public void eliminarContacto(@PathVariable Long id) {
		contactoRepository.deleteById(id);
	}
}
