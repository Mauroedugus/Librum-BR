package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExemplaryCreateDTO implements Serializable {

    @JsonProperty("book_id")
    private String bookId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("book_rental")
    private String bookRentalId;

}
