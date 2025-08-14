package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookRentalCreateDTO implements Serializable {

    @JsonProperty("exemplary_ids")
    private List<Integer> exemplaryIds;

    @JsonProperty("reader_id")
    private Integer readerId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("predicted_date")
    private LocalDate predictedDate;

    @JsonProperty("final_date")
    private LocalDate finalDate;

}
