package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

}
