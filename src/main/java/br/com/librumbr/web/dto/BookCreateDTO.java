package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookCreateDTO implements Serializable {
        @JsonProperty("isbn")
        @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "ISBN inválido")
        private String isbn;

        @JsonProperty("title")
        private String title;

        @JsonProperty("authors")
        private List<String> authors;

        @JsonProperty("publisher")
        private String publisher;

        @JsonProperty("synopsis")
        private String synopsis;

        @JsonProperty("year")
        private int year;

        @JsonProperty("pageCount")
        private int pageCount;

        @JsonProperty("urlCover")
        private String coverUrl;

        @JsonProperty("category")
        private String category;
    }
