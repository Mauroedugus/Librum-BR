package br.com.librumbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.librumbr.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
