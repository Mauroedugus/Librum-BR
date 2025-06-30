package br.com.librumbr.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "exemplars")
public class Exemplary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "inventory_number", nullable = false, length = 255)
    private String inventoryNumber;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "book_rental_id")
    private BookRental bookRental;

    @NonNull
    @Column(nullable = false, length = 100)
    private String status;

}
