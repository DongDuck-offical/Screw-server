package dongduck.screw.domain.member;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;
import dongduck.screw.domain.enumType.Role;


import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.LAZY;


@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<Board> boardList = new ArrayList<>();

    private String nickname;
    private String name;
    private String password;
    private String email;
    private String profileImageUrl;
    private String area;

    @Enumerated(EnumType.STRING)
    private Role role;
}
