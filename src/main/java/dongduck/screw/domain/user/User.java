package dongduck.screw.domain.user;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;
import dongduck.screw.domain.enumType.RoleType;
import dongduck.screw.dto.user.SignupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<Board> boardList = new ArrayList<>();

    private String username;
    private String name;
    private String password;
    private String email;
    private String profileImageUrl;
    private String area;

    @Enumerated(EnumType.STRING)
    private RoleType role;


    public static User createUser(SignupDto signupDto){
        User user = User.builder()
                .username(signupDto.getUsername())
                .name(signupDto.getName())
                .password(signupDto.getPassword()).build();
        return user;
    }


}
