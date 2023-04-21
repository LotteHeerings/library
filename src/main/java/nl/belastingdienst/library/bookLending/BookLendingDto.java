package nl.belastingdienst.library.bookLending;

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
public class BookLendingDto {

    @NotBlank
    @Min(13)
    private String isbn;

    @NotBlank
    @Max(64)
    private String email;

    @NotBlank
    private String weeksOfLending;

}
