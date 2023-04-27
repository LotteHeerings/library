package nl.belastingdienst.library.damage;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DamageDto {

    @NotBlank
    @Max(128)
    private String typeOfDamage;

    @NotBlank
    private Integer costOfDamage;

    @NotBlank
    private Boolean paid;

}
