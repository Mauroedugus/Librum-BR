package br.com.librumbr.repositories;

import br.com.librumbr.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findUserDetailsByEmail(String email);
    User findUserByEmail(String email);
}
