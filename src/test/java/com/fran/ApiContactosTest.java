package com.fran;

import com.fran.controller.ApiContactos;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fran.entity.Contacto;
import com.fran.repository.ContactoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiContactos.class)
public class ApiContactosTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactoRepository contactoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void obtenerContactos_DeberiaRetornarListaDeContactos() throws Exception {
        List<Contacto> contactos = List.of(
                new Contacto("Juan", null),
                new Contacto("María", null)
        );
        when(contactoRepository.findAll()).thenReturn(contactos);

        mockMvc.perform(get("/api/contactos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Juan")))
                .andExpect(jsonPath("$[1].nombre", is("María")));
    }

    @Test
    public void crearContacto_DeberiaCrearNuevoContacto() throws Exception {
        Contacto nuevoContacto = new Contacto(null, null);
        Contacto contactoCreado = new Contacto("Nuevo Contacto", null);
        when(contactoRepository.save(any())).thenReturn(contactoCreado);

        mockMvc.perform(post("/api/contactos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(nuevoContacto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Nuevo Contacto")));
    }

    @Test
    public void eliminarContacto_DeberiaEliminarContactoExistente() throws Exception {
        Contacto contactoCreado = new Contacto("Nuevo Contacto", null);
        Optional<Contacto> optionalContacto = Optional.of(contactoCreado);
        when(contactoRepository.findById(contactoCreado.getId())).thenReturn(optionalContacto);
        doNothing().when(contactoRepository).delete(contactoCreado);

        mockMvc.perform(delete("/api/contactos/{id}", contactoCreado.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void actualizarContacto_DeberiaActualizarContactoExistente() throws Exception {
        Contacto contactoExistente = new Contacto("Contacto Existente", null);
        Optional<Contacto> optionalContacto = Optional.of(contactoExistente);
        when(contactoRepository.findById(contactoExistente.getId())).thenReturn(optionalContacto);

        Contacto contactoActualizado = new Contacto("Contacto Actualizado", null);
        when(contactoRepository.save(any())).thenReturn(contactoActualizado);

        mockMvc.perform(put("/api/contactos/{id}", contactoExistente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(contactoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Contacto Actualizado")));
    }
}