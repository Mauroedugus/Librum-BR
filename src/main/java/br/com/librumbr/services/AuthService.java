package br.com.librumbr.services;

import br.com.librumbr.models.User;
import br.com.librumbr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.reset-url-base}") // Vamos adicionar isso no application.properties
    private String resetUrlBase;

    public void forgotPassword(String email){
        UserDetails user =  userService.loadUserByUsername(email);
        String token = tokenService.generatePasswordResetToken(email);
        String resetLink = resetUrlBase + "?token=" + token;

        emailService.sendPasswordResetEmail(email,resetLink);
    }

    public void resetPassword(String token, String newPassword){
        String email = tokenService.validateToken(token);

        User user = userService.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
