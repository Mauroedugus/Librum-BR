package br.com.librumbr.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookRentalResponseDTO implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("exemplary_ids")
    private List<Integer> exemplaryIds;

    @JsonProperty("reader")
    private Integer readerId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("predicted_date")
    private LocalDate predictedDate;

    @JsonProperty("final_date")
    private LocalDate finalDate;

    @JsonProperty("days_remaining")
    public long getDaysRemaining() {
        if (finalDate != null) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), predictedDate);
    }

}
