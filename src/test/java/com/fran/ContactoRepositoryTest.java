package com.fran;

import com.fran.entity.Contacto;
import com.fran.repository.ContactoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ContactoRepositoryTest {

	@Autowired
	private ContactoRepository contactoRepository;

	@BeforeEach
	public void setUp() {
		// Insertar algunos datos de prueba antes de cada prueba
		contactoRepository.save(new Contacto("Juan", null));
		contactoRepository.save(new Contacto("María", null));
	}

	@AfterEach
	public void tearDown() {
		// Limpiar la base de datos después de cada prueba
		contactoRepository.deleteAll();
	}

	@Test
	public void testFindAll() {
		// Verificar que se puedan recuperar todos los contactos guardados en la base de
		// datos
		List<Contacto> contactos = contactoRepository.findAll();
		assertEquals(2, contactos.size());
	}

	@Test
	public void testFindById() {
		// Verificar que se pueda encontrar un contacto por su ID
		List<Contacto> contactos = contactoRepository.findAll();
		Long primerContactoId = contactos.get(0).getId(); // Tomamos el ID del primer contacto en la lista
		Optional<Contacto> optionalContacto = contactoRepository.findById(primerContactoId);
		assertTrue(optionalContacto.isPresent());
		assertEquals("Juan", optionalContacto.get().getNombre());
	}

	@Test
	public void testSaveContacto() {
		// Verificar que se pueda guardar un nuevo contacto en la base de datos
		Contacto nuevoContacto = new Contacto("Pedro", null);
		Contacto contactoGuardado = contactoRepository.save(nuevoContacto);
		assertEquals("Pedro", contactoGuardado.getNombre());
	}

	@Test
	public void testUpdateContacto() {
		// Verificar que se pueda actualizar un contacto existente en la base de datos
		List<Contacto> contactos = contactoRepository.findAll();
		Long primerContactoId = contactos.get(0).getId(); // Tomamos el ID del primer contacto en la lista
		Optional<Contacto> optionalContacto = contactoRepository.findById(primerContactoId);
		assertTrue(optionalContacto.isPresent());

		Contacto contactoExistente = optionalContacto.get();
		contactoExistente.setNombre("Juanita");
		contactoRepository.save(contactoExistente);

		Contacto contactoActualizado = contactoRepository.findById(primerContactoId).orElse(null);
		assertEquals("Juanita", contactoActualizado.getNombre());
	}

	@Test
	public void testDeleteContacto() {
		// Verificar que se pueda eliminar un contacto existente de la base de datos
		List<Contacto> contactos = contactoRepository.findAll();
		Long primerContactoId = contactos.get(0).getId(); // Tomamos el ID del primer contacto en la lista
		Optional<Contacto> optionalContacto = contactoRepository.findById(primerContactoId);
		assertTrue(optionalContacto.isPresent());

		Contacto contactoAEliminar = optionalContacto.get();
		contactoRepository.delete(contactoAEliminar);

		Optional<Contacto> resultado = contactoRepository.findById(primerContactoId);
		assertTrue(resultado.isEmpty());
	}

}
