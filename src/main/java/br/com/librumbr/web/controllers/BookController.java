package br.com.librumbr.web.controllers;

import br.com.librumbr.services.BookService;
import br.com.librumbr.web.dto.BrasilApiResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/isbn/{isbn}")
    public Mono<BrasilApiResponseDTO> getBookFromBrasilApi(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }
}
