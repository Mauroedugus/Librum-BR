package br.com.librumbr.services;

import br.com.librumbr.exceptions.BookNotFoundException;
import br.com.librumbr.models.Author;
import br.com.librumbr.models.Book;
import br.com.librumbr.models.Category;
import br.com.librumbr.models.Publisher;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.repositories.AuthorRepository;
import br.com.librumbr.repositories.BookRepository;
import br.com.librumbr.repositories.CategoryRepository;
import br.com.librumbr.repositories.PublisherRepository;
import br.com.librumbr.web.dto.BookCreateDTO;
import br.com.librumbr.web.dto.BookResponseDTO;
import br.com.librumbr.web.dto.BrasilApiResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    private WebClient webClient;

    public BookService(WebClient.Builder webClientBuilder, BookRepository bookRepository) {
        this.webClient = webClientBuilder
                .baseUrl("https://brasilapi.com.br/api/isbn/v1")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Transactional
    public Mono<BrasilApiResponseDTO> getBookByIsbn(String isbn) {
        return webClient.get()
                .uri("/{isbn}", isbn)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    // Se for erro 404, ignora e tenta buscar local
                    return Mono.error(new BookNotFoundException("Livro não encontrado na API externa."));
                })
                .bodyToMono(BrasilApiResponseDTO.class)
                .onErrorResume(ex -> {
                    // Busca no banco se falhar a chamada
                    Optional<Book> bookOptional = bookRepository.findByIsbn(isbn);
                    if (bookOptional.isEmpty()){
                        return Mono.error(new BookNotFoundException("Livro não encontrado no banco de dados"));
                    }
                    BrasilApiResponseDTO dto = ModelMapperUtil.parseObject(bookOptional.get(), BrasilApiResponseDTO.class);
                    return Mono.just(dto);
                });
    }

    @Transactional
    public List<BookResponseDTO> findAllBooks() {
        var books = bookRepository.findAll();
        var booksDTO = books.stream().map(p -> ModelMapperUtil.parseObject(p, BookResponseDTO.class));

        return booksDTO.toList();
    }

    @Transactional
    public BookResponseDTO findBookById(int id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Livro com ID " + id + " não encontrado"));
        return ModelMapperUtil.parseObject(book, BookResponseDTO.class);
    }

    @Transactional
    public BookResponseDTO createBook(BookCreateDTO dto) {

        List<Author> authors = dto.getAuthors().stream()
                .map(name -> authorRepository.findByName(name)
                        .orElseGet(() -> authorRepository.save(new Author(name))))
                .toList();

        Category category = categoryRepository.findByName(dto.getCategory())
                .orElseGet(() -> categoryRepository.save(new Category(dto.getCategory())));

        Publisher publisher = publisherRepository.findByName(dto.getPublisher())
                .orElseGet(() -> publisherRepository.save(new Publisher(dto.getPublisher())));

        Book book = ModelMapperUtil.parseObject(dto, Book.class);

        book.setAuthors(authors);
        book.setCategory(category);
        book.setPublisher(publisher);

        return ModelMapperUtil.parseObject(bookRepository.save(book), BookResponseDTO.class);
    }

    @Transactional
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Livro com ID " + id + " não encontrado para exclusão.");
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public void updateBook(int id, BookCreateDTO updatedBook) {

        Book oldBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado"));

        List<Author> authors = updatedBook.getAuthors().stream()
                .map(authorName -> authorRepository.findByName(authorName)
                        .orElseGet(() -> authorRepository.save(new Author(authorName))))
                .collect(Collectors.toList());

        Category category = categoryRepository.findByName(updatedBook.getCategory())
                .orElseGet(() -> categoryRepository.save(new Category(updatedBook.getCategory())));

        Publisher publisher = publisherRepository.findByName(updatedBook.getPublisher())
                .orElseGet(() -> publisherRepository.save(new Publisher(updatedBook.getPublisher())));

        oldBook.setTitle(updatedBook.getTitle());
        oldBook.setIsbn(updatedBook.getIsbn());
        oldBook.setPublisher(publisher);
        oldBook.setCategory(category);
        oldBook.setSynopsis(updatedBook.getSynopsis());
        oldBook.setYear(updatedBook.getYear());
        oldBook.setPagesCount(updatedBook.getPageCount());
        oldBook.setUrlCover(updatedBook.getCoverUrl());
        oldBook.setAuthors(authors);

        Book savedBook = bookRepository.save(oldBook);
    }
}