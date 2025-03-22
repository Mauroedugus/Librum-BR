package br.com.librumbr.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String name;

    @Pattern( regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Formato de e-mail inválido")
    @NonNull
    @Column(nullable = false, length = 50)
    private String email;

    @Pattern(regexp = "\\+?[0-9\\-\\s]+", message = "Formato de telefone inválido")
    @NonNull
    @Column(name = "phone_number", nullable = false, length = 50)
    private String phoneNumber;

}
