package dongduck.screw.domain.user;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;
import dongduck.screw.domain.enumType.RoleType;
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

    private String email; //unique
    private String name;
    private String password;
    private String profileImageUrl;
    private String area;

    @Enumerated(EnumType.STRING)
    private RoleType role;

}
