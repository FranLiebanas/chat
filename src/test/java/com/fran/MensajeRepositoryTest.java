package com.fran;

import com.fran.entity.Contacto;
import com.fran.entity.Mensaje;
import com.fran.repository.ContactoRepository;
import com.fran.repository.MensajeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MensajeRepositoryTest {

    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private ContactoRepository contactoRepository;
    private Contacto emisor;
    private Contacto receptor;
    

    @BeforeEach
    public void setUp() {
        emisor = new Contacto("Juan", null);
        receptor = new Contacto("María", null);        
		// Guardar los contactos antes de crear y guardar los mensajes
        emisor = contactoRepository.save(emisor);
        receptor = contactoRepository.save(receptor);

        mensajeRepository.save(new Mensaje(emisor, receptor, LocalDateTime.now(), "Hola María"));
        mensajeRepository.save(new Mensaje(receptor, emisor, LocalDateTime.now(), "Hola Juan"));
    }

    @AfterEach
    public void tearDown() {
        mensajeRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        assertEquals(2, mensajes.size());
    }

    @Test
    public void testFindById() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        Long primerMensajeId = mensajes.get(0).getId();
        Optional<Mensaje> optionalMensaje = mensajeRepository.findById(primerMensajeId);
        assertTrue(optionalMensaje.isPresent());
    }

    @Test
    public void testSaveMensaje() {
        Mensaje nuevoMensaje = new Mensaje(emisor, receptor, LocalDateTime.now(), "Hola nuevamente María");
        Mensaje mensajeGuardado = mensajeRepository.save(nuevoMensaje);
        assertNotNull(mensajeGuardado.getId());
    }

    @Test
    public void testDeleteMensaje() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        Long primerMensajeId = mensajes.get(0).getId();

        mensajeRepository.deleteById(primerMensajeId);

        Optional<Mensaje> resultado = mensajeRepository.findById(primerMensajeId);
        assertTrue(resultado.isEmpty());
    }
}
