package nl.belastingdienst.library.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NonNull
    private String isbn;

    @NonNull
    @Max(64)
    private String title;

    @Max(64)
    private String authors;

    @Max(32)
    private String publisher;

    private String cover_path;

    @Max(2)
    private String language;

    @NonNull
    private String publication_date;

    @NonNull
    private String pages;

}
