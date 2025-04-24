package br.com.librumbr.web.controllers;

import br.com.librumbr.models.User;
import br.com.librumbr.repositories.UserRepository;
import br.com.librumbr.services.TokenService;
import br.com.librumbr.web.dto.AuthDTO;
import br.com.librumbr.web.dto.LoginResponseDTO;
import br.com.librumbr.web.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid RegisterDTO data){
        if (repo.findByEmail(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());
        repo.save(newUser);

        return ResponseEntity.ok().build();
    }
}
