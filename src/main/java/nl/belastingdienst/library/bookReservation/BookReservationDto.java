package nl.belastingdienst.library.bookReservation;

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
public class BookReservationDto {

    @NotBlank
    @Min(13)
    private String isbn;

    @NotBlank
    @Max(64)
    private String email;

    @NotBlank
    @Max(10)
    private String reservationDate;

    @NotBlank
    private String weeksOfLending;
}
