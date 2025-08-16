package br.com.librumbr.web.controllers;

import br.com.librumbr.services.ReaderService;
import br.com.librumbr.web.dto.ReaderCreateDTO;
import br.com.librumbr.web.dto.ReaderResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/readers") // ajuste para "/api/readers" se seu projeto usa prefixo /api
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping
    public List<ReaderResponseDTO> getAll() {
        return readerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponseDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(readerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReaderResponseDTO> create(@Valid @RequestBody ReaderCreateDTO dto) {
        ReaderResponseDTO created = readerService.create(dto);
        return ResponseEntity.created(URI.create("/readers/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReaderResponseDTO> update(@PathVariable int id,
                                                    @Valid @RequestBody ReaderCreateDTO dto) {
        return ResponseEntity.ok(readerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        readerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
