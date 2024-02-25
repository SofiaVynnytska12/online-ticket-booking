package duikt.practice.otb.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "userRegistration")
public class UserRegisterRequest {

    @Schema(example = "Nazar Kotelnytskyi")
    @NotEmpty(message = "Fill in your name please!")
    private String username;

    @Schema(example = "bfaskak244@gmail.com")
    @NotNull(message = "Must be a valid e-mail address")
    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    @Schema(example = "4214jfsdl@gldDQ$)!")
    @NotEmpty(message = "Fill in your password please!")
    private String password;

}
