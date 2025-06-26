package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExemplaryResponseDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("inventory_number")
    private String inventoryNumber;

    @JsonProperty("bookTitle")
    private String bookTitle;

    @JsonProperty("book_rental")
    private Integer bookRental;

    @JsonProperty("status")
    private String status;
}
