package br.com.librumbr.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

}
