package br.com.librumbr.web.controllers;

import br.com.librumbr.models.Book;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.services.BookService;
import br.com.librumbr.web.dto.BookCreateDTO;
import br.com.librumbr.web.dto.BookResponseDTO;
import br.com.librumbr.web.dto.BrasilApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/isbn/{isbn}")
    public Mono<BrasilApiResponseDTO>getBookFromBrasilApi(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
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
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookCreateDTO book) {
        var newBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(ModelMapperUtil.parseObject(newBook, BookResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable int id, @RequestBody BookCreateDTO updatedBook) {
        bookService.updateBook(id, updatedBook);
        return ResponseEntity.noContent().build();
    }
}
