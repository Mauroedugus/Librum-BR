package br.com.librumbr.services;

import br.com.librumbr.exceptions.UserNotFoundException;
import br.com.librumbr.models.mapper.ModelMapperUtil;
import br.com.librumbr.repositories.UserRepository;
import br.com.librumbr.web.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username);
    }

    @Transactional
    public List<UserDTO> findAllUsers(){
        return repo.findAll().stream().map(u -> ModelMapperUtil.parseObject(u, UserDTO.class)).toList();
    }

    @Transactional
    public UserDTO findUserById(int id){
        var user = repo.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário com id "+id+" não encontrado."));
        return ModelMapperUtil.parseObject(user, UserDTO.class);
    }

    @Transactional
    public void updateUser(int id, UserDTO updatedUser){
        var user = repo.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário com id "+id+" não encontrado."));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        repo.save(user);
    }

    @Transactional
    public void deleteUser(int id){
        if (!repo.existsById(id)){
            throw new UserNotFoundException("Usuário com id "+id+" não encontrado para exclusão.");
        }
        repo.deleteById(id);
    }
}
