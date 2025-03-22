package br.com.librumbr.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "ISBN inválido")
    @NonNull
    @Column(nullable = false, length = 50, unique = true)
    private String isbn;

    @Column(name = "url_cover", length = 255)
    private String urlCover;

    @NonNull
    @Column(nullable = false, length = 100)
    private String title;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @NonNull
    @ManyToMany
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_publisher", nullable = false)
    private Publisher publisher;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(length = 4)
    private int year;

    @Column(name = "page_count",columnDefinition = "INT")
    private int pagesCount;

    @NonNull
    @Column(nullable = false, columnDefinition = "INT")
    private int quantity;

}
