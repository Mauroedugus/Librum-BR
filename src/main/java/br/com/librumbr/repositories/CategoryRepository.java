package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
