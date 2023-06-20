package dongduck.screw.dto.user;

import dongduck.screw.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String email;
    String password;

    public UserDto(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
