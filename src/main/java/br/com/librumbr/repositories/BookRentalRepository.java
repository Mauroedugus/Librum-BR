package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.BookRental;

@Repository
public interface BookRentalRepository extends JpaRepository<BookRental, Integer> {

}
