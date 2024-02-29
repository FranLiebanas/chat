package com.fran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fran.entity.Contacto;
import com.fran.entity.Mensaje;
import com.fran.repository.MensajeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiMensajes {

    private final MensajeRepository mensajeRepository;

    public ApiMensajes(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

 // Endpoint para obtener todos los mensajes
    @GetMapping("/mensajes")
    public ResponseEntity<List<Mensaje>> obtenerTodosLosMensajes() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        return ResponseEntity.ok(mensajes);
    }

    // Crear un nuevo mensaje
    @PostMapping("/mensajes")
    public ResponseEntity<Mensaje> crearMensaje(@RequestBody Mensaje nuevoMensaje) {
        Mensaje mensajeCreado = mensajeRepository.save(nuevoMensaje);
        return ResponseEntity.ok(mensajeCreado);
    }

    // Actualizar un mensaje existente
    @PutMapping("/mensajes/{id}")
    public ResponseEntity<Mensaje> actualizarMensaje(@PathVariable Long id, @RequestBody Mensaje mensajeActualizar) {
        Optional<Mensaje> optionalMensaje = mensajeRepository.findById(id);
        if (optionalMensaje.isPresent()) {
            Mensaje mensajeExistente = optionalMensaje.get();
            mensajeExistente.setTexto(mensajeActualizar.getTexto());
            // Puedes actualizar otros campos según sea necesario
            Mensaje mensajeActualizado = mensajeRepository.save(mensajeExistente);
            return ResponseEntity.ok(mensajeActualizado);
        } else {
            // Si no se encuentra el mensaje con el ID proporcionado, devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un mensaje existente
    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        Optional<Mensaje> optionalMensaje = mensajeRepository.findById(id);
        if (optionalMensaje.isPresent()) {
            mensajeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            // Si no se encuentra el mensaje con el ID proporcionado, devuelve un error 404
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener mensajes entre un emisor y un receptor específicos
    @GetMapping("/mensajes/emisor/{emisorNombre}/receptor/{receptorNombre}")
    public ResponseEntity<List<Mensaje>> obtenerMensajesPorEmisorYReceptor(@PathVariable String emisorNombre, @PathVariable String receptorNombre) {
        List<Mensaje> mensajes = mensajeRepository.findByEmisorNombreAndReceptorNombre(emisorNombre, receptorNombre);
        return ResponseEntity.ok(mensajes);
    }

    // Obtener mensajes entre un receptor y un emisor específicos
    @GetMapping("/mensajes/receptor/{receptorNombre}/emisor/{emisorNombre}")
    public ResponseEntity<List<Mensaje>> obtenerMensajesPorReceptorYEmisor(@PathVariable String receptorNombre, @PathVariable String emisorNombre) {
        List<Mensaje> mensajes = mensajeRepository.findByEmisorNombreAndReceptorNombre(emisorNombre, receptorNombre);
        return ResponseEntity.ok(mensajes);
    }
    
    
}

