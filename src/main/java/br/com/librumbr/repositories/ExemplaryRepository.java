package br.com.librumbr.repositories;

import br.com.librumbr.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Exemplary;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplaryRepository extends JpaRepository<Exemplary, Integer> {

    List<Exemplary> findByBookId(int bookId);

    @Override
    Optional<Exemplary> findById(Integer integer);

    List<Exemplary> findByBookRentalId(Integer bookRentalId);

}
