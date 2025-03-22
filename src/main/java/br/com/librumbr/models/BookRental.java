package br.com.librumbr.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "books_rental")
public class BookRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @OneToMany(mappedBy = "bookRental")
    private List<Exemplary> exemplars;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_reader",nullable = false)
    private Reader reader;

    @NonNull
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NonNull
    @Column(name = "predicted_date", nullable = false)
    private Date predictedDate;

    @Column(name = "final_date")
    private Date finalDate;

    @NonNull
    @Column(nullable = false, length = 100)
    private String status;
}
