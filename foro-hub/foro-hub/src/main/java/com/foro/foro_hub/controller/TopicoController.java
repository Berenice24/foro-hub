package com.foro.foro_hub.controller;

import com.foro.foro_hub.model.Topico;
import com.foro.foro_hub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;

    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearTopico(@Valid @RequestBody Topico topico) {
        Optional<Topico> existente = topicoRepository.findByTituloAndMensaje(topico.getTitulo(), topico.getMensaje());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }
        Topico guardado = topicoRepository.save(topico);
        return ResponseEntity.status(201).body(guardado);
    }
    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return ResponseEntity.ok(topicos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topicoOpt = topicoRepository.findById(id);

        if (topicoOpt.isPresent()) {
            return ResponseEntity.ok(topicoOpt.get());
        } else {
            return ResponseEntity.status(404).body("Tópico no encontrado");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico topicoActualizado) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (!optionalTopico.isPresent()) {
            return ResponseEntity.status(404).body("Tópico no encontrado");
        }

        // Verificar duplicados (mismo título y mensaje, pero diferente id)
        Optional<Topico> duplicado = topicoRepository.findByTituloAndMensaje(topicoActualizado.getTitulo(), topicoActualizado.getMensaje());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }

        // Actualizar datos
        Topico topico = optionalTopico.get();
        topico.setTitulo(topicoActualizado.getTitulo());
        topico.setMensaje(topicoActualizado.getMensaje());
        topico.setAutor(topicoActualizado.getAutor());
        topico.setCurso(topicoActualizado.getCurso());
        topico.setStatus(topicoActualizado.getStatus());
        topico.setFechaCreacion(topicoActualizado.getFechaCreacion());

        Topico guardado = topicoRepository.save(topico);
        return ResponseEntity.ok(guardado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id); // usa topicoRepository

        if (topicoOptional.isPresent()) {
            topicoRepository.deleteById(id); // usa topicoRepository
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.notFound().build(); // 404 Not Found
    }



}
