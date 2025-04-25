package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookResponseDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("authors")
    private List<String> authors;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("category")
    private String category;

    @JsonProperty("synopsis")
    private String synopsis;

    @JsonProperty("year")
    private int year;

    @JsonProperty("pageCount")
    private int pageCount;

    @JsonProperty("urlCover")
    private String coverUrl;
}
