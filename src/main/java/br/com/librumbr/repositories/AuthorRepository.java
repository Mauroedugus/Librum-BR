package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
