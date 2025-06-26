package br.com.librumbr.web.controllers;

import br.com.librumbr.models.Book;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.services.BookService;
import br.com.librumbr.web.dto.BookCreateDTO;
import br.com.librumbr.web.dto.BookResponseDTO;
import br.com.librumbr.web.dto.BrasilApiResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BrasilApiResponseDTO> getBookFromBrasilApi(@PathVariable String isbn) {
        BrasilApiResponseDTO book = bookService.getBookByIsbn(isbn).block(); // ← força sincronismo
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findBookById(@PathVariable int id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookCreateDTO book) {
        var newBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(ModelMapperUtil.parseObject(newBook, BookResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable int id, @RequestBody @Valid BookCreateDTO updatedBook) {
        bookService.updateBook(id, updatedBook);
        return ResponseEntity.noContent().build();
    }
}
