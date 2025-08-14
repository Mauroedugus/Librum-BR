package br.com.librumbr.web.controllers;

import br.com.librumbr.services.BookRentalService;
import br.com.librumbr.web.dto.BookRentalCreateDTO;
import br.com.librumbr.web.dto.BookRentalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class BookRentalController {
    @Autowired
    private BookRentalService bookRentalService;

    @PostMapping
    public ResponseEntity<BookRentalResponseDTO> createBookRental(@RequestBody BookRentalCreateDTO bookRental) {
        var newBookRental = bookRentalService.createBookRental(bookRental);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBookRental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookRentalResponseDTO> updateBookRental(@PathVariable int id, @RequestBody BookRentalCreateDTO updateBookRental) {
        var updated = bookRentalService.updateBookRental(id, updateBookRental);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public String deleteBookRental(@PathVariable int id) {
        bookRentalService.deleteById(id);
        return "Successfully Operation";
    }

    @GetMapping("/{id}")
    public BookRentalResponseDTO getBookRental(@PathVariable int id) {
        return bookRentalService.findById(id);
    }

    @GetMapping
    public List<BookRentalResponseDTO> getAllBookRental() {return bookRentalService.findAllBookRental();}
}
