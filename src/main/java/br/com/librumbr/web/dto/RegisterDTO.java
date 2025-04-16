package br.com.librumbr.web.dto;

import br.com.librumbr.models.UserRole;

public record RegisterDTO(String name, String login, String password, UserRole role) {

}
