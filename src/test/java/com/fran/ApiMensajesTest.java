package com.fran;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fran.controller.ApiMensajes;
import com.fran.entity.Mensaje;
import com.fran.repository.MensajeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiMensajes.class)
public class ApiMensajesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MensajeRepository mensajeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void obtenerMensajes_DeberiaRetornarListaDeMensajes() throws Exception {
        List<Mensaje> mensajes = List.of(
                new Mensaje(null, null, LocalDateTime.now(), "Mensaje 1"),
                new Mensaje(null, null, LocalDateTime.now(), "Mensaje 2")
        );
        when(mensajeRepository.findAll()).thenReturn(mensajes);

        mockMvc.perform(get("/api/mensajes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].texto", is("Mensaje 1")))
                .andExpect(jsonPath("$[1].texto", is("Mensaje 2")));
    }

    @Test
    public void crearMensaje_DeberiaCrearNuevoMensaje() throws Exception {
        Mensaje nuevoMensaje = new Mensaje(null, null, LocalDateTime.now(), "Nuevo Mensaje");
        Mensaje mensajeCreado = new Mensaje(null, null, LocalDateTime.now(), "Nuevo Mensaje");
        when(mensajeRepository.save(any())).thenReturn(mensajeCreado);

        mockMvc.perform(post("/api/mensajes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(nuevoMensaje)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.texto", is("Nuevo Mensaje")));
    }

    @Test
    public void eliminarMensaje_DeberiaEliminarMensajeExistente() throws Exception {
        Mensaje mensajeCreado = new Mensaje(null, null, LocalDateTime.now(), "Nuevo Mensaje");
        Optional<Mensaje> optionalMensaje = Optional.of(mensajeCreado);
        when(mensajeRepository.findById(mensajeCreado.getId())).thenReturn(optionalMensaje);
        doNothing().when(mensajeRepository).delete(mensajeCreado);

        mockMvc.perform(delete("/api/mensajes/{id}", mensajeCreado.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void actualizarMensaje_DeberiaActualizarMensajeExistente() throws Exception {
        Mensaje mensajeExistente = new Mensaje(null, null, LocalDateTime.now(), "Mensaje Existente");
        Optional<Mensaje> optionalMensaje = Optional.of(mensajeExistente);
        when(mensajeRepository.findById(mensajeExistente.getId())).thenReturn(optionalMensaje);

        Mensaje mensajeActualizado = new Mensaje(null, null, LocalDateTime.now(), "Mensaje Actualizado");
        when(mensajeRepository.save(any())).thenReturn(mensajeActualizado);

        mockMvc.perform(put("/api/mensajes/{id}", mensajeExistente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(mensajeActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.texto", is("Mensaje Actualizado")));
    }
}
