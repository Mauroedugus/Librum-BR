package br.com.librumbr.web.dto;

import br.com.librumbr.models.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO{
    @JsonProperty("id")
    private int id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Formato de e-mail inválido")
    @JsonProperty("login")
    private String email;

    @NotNull
    @JsonProperty("role")
    private UserRole role;
}
