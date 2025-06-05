package br.com.librumbr.web.dto;

import br.com.librumbr.models.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(@NotBlank String name,
                          @NotBlank
                          @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Formato de e-mail inválido")
                          String login,
                          @NotBlank String password,
                          @NotNull UserRole role) {

}
