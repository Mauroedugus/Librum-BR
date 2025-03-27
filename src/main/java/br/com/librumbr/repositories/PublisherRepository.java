package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
