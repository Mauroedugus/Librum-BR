package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExemplaryResponseDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("inventory_number")
    private String inventoryNumber;

    @JsonProperty("book_Title")
    private String bookTitle;

    @JsonProperty("book_rental_id")
    private Integer bookRentalId;

    @JsonProperty("status")
    private String status;
}
