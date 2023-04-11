package nl.belastingdienst.library.book;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_book")
public class Book {
    @Id
    @Column(name = "ISBN13", nullable = false, unique = true)
    private String ISBN13;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors")
    private String authors;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "cover_path")
    private String cover_path;

    @Column(name = "language")
    private String language;

    @Column(name = "publication_date", nullable = false)
    private String publication_date;

    @Column(name = "pages")
    private Integer pages;
}
