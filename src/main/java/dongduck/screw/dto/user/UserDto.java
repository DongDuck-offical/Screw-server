package dongduck.screw.dto.user;

import dongduck.screw.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String username;
    String password;

    public UserDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
