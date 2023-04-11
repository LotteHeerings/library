package nl.belastingdienst.library.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotBlank
    @Min(13)
    private String ISBN13;

    @NotBlank
    @Max(64)
    private String title;

    @Max(64)
    private String authors;

    @Max(32)
    private String publisher;

    private String cover_path;

    @Max(2)
    private String language;

    @NotBlank
    private String publication_date;

    @NotBlank
    private String pages;

}
