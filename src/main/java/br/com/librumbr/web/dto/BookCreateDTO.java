package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookCreateDTO implements Serializable {

        @NotBlank
        @JsonProperty("isbn")
        @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "ISBN inválido")
        private String isbn;

        @NotBlank
        @JsonProperty("title")
        private String title;

        @NotEmpty
        @JsonProperty("authors")
        private List<@NotBlank String> authors;

        @NotBlank
        @JsonProperty("publisher")
        private String publisher;

        @NotBlank
        @JsonProperty("synopsis")
        private String synopsis;

        @NotNull
        @JsonProperty("year")
        private int year;

        @NotNull
        @JsonProperty("pageCount")
        private int pageCount;

        @NotBlank
        @JsonProperty("urlCover")
        private String coverUrl;

        @NotBlank
        @JsonProperty("category")
        private String category;
    }
