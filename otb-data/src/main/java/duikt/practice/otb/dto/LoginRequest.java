package duikt.practice.otb.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Fill in your name please!")
    private String username;

    @NotEmpty(message = "Fill in your password please!")
    private String password;

}
