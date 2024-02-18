package duikt.practice.otb.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotEmpty(message = "Fill in your name please!")
    @Column(nullable = false)
    private String username;

    @NotNull(message = "Must be a valid e-mail address")
    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Fill in your password please!")
    @Column(nullable = false)
    private String password;

}
