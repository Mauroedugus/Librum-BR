package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
