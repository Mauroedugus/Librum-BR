package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @EntityGraph(attributePaths = {"authors", "publisher"})
    Optional<Book> findByIsbn(String isbn);

    @Override
    Optional<Book> findById(Integer integer);
}
