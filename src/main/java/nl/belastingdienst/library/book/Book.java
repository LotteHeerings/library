package nl.belastingdienst.library.book;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "ISBN-13", nullable = false, unique = true)
    private Integer ISBN13;

    @Column(name = "title")
    private String title;

    @Column(name = "authors")
    private String authors;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "cover_path")
    private String cover_path;

    @Column(name = "language")
    private String language;

    @Column(name = "publication_date")
    private String publication_date;
}
