package nl.belastingdienst.library.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Email
    @NotBlank
    @Max(64)
    private String email;

    @Max(64)
    private String name;

    @NotBlank
    @Min(5)
    private String password;

    @NotBlank
    private String user_role;
}
