package duikt.practice.otb.dto;

import duikt.practice.otb.entity.addition.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
@Builder
@Getter
public class UserResponse {

    private Long id;

    @NotEmpty(message = "Fill in your name please!")
    private String username;

    @NotNull(message = "Must be a valid e-mail address")
    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    @NotEmpty(message = "Fill in your password please!")
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username)
                && Objects.equals(email, that.email) && Objects.equals(password, that.password)
                && userRole == that.userRole;
    }

    @Override
    public int hashCode() {
        return 57 * Objects.hash(id, username, email, password, userRole);
    }
}
