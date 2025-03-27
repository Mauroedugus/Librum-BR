package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Exemplary;

@Repository
public interface ExemplaryRepository extends JpaRepository<Exemplary, Integer> {

}
