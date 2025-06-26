package br.com.librumbr.controllers;

import br.com.librumbr.models.Exemplary;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.services.ExemplaryService;
import br.com.librumbr.web.dto.BookResponseDTO;
import br.com.librumbr.web.dto.ExemplaryCreateDTO;
import br.com.librumbr.web.dto.ExemplaryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exemplars")
class ExemplaryController {

    @Autowired
    private ExemplaryService exemplaryService;

    @PostMapping
    public ResponseEntity<ExemplaryResponseDTO> createExemplary(@RequestBody ExemplaryCreateDTO exemplary) {
        var newExemplary = exemplaryService.createExemplary(exemplary);
        return ResponseEntity.status(HttpStatus.CREATED).body(ModelMapperUtil.parseObject(newExemplary, ExemplaryResponseDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExemplary(@PathVariable int id, @RequestBody ExemplaryCreateDTO updateExemplary) {
        exemplaryService.updateExemplary(id, updateExemplary);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public String deleteExemplary(@PathVariable int id) {
        exemplaryService.deleteById(id);
        return "Successfully Operation";
    }

    @GetMapping("/{id}")
    public Exemplary getExemplary(@PathVariable int id) {
        return exemplaryService.findById(id);
    }

    @GetMapping
    public List<ExemplaryResponseDTO> getAllExemplars() {return exemplaryService.findAllExemplars();}
}
