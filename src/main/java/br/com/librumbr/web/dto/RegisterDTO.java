package br.com.librumbr.web.dto;

import br.com.librumbr.models.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotBlank String name,
                          @NotBlank String login,
                          @NotBlank String password,
                          @NotNull UserRole role) {

}
