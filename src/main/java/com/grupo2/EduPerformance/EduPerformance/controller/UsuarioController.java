package com.grupo2.EduPerformance.EduPerformance.controller;

import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.request.UsuarioRequestDTO;
import com.grupo2.EduPerformance.EduPerformance.model.entity.dto.response.UsuarioResponseDTO;
import com.grupo2.EduPerformance.EduPerformance.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getMe(org.springframework.security.core.Authentication authentication) {
        // Al deshabilitar la seguridad, authentication será null.
        // Retornamos el administrador por defecto (id = 3) o el primer usuario encontrado para que el frontend funcione.
        if (authentication == null || !authentication.isAuthenticated()) {
            return service.findById(3L) // Asumiendo que 3L es el admin que creamos
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        List<UsuarioResponseDTO> all = service.findAll();
                        return all.isEmpty() ? ResponseEntity.status(401).build() : ResponseEntity.ok(all.get(0));
                    });
        }
        return service.findByEmail(authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioResponseDTO create(@Valid @RequestBody UsuarioRequestDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}