package dongduck.screw.domain.board;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.enumType.Sport;
import dongduck.screw.domain.likes.Likes;
import dongduck.screw.domain.member.Member;


import javax.persistence.*;
import java.util.*;

@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likeList = new ArrayList<>();

    private String title;
    private String content;
    private String area;

    @Enumerated(EnumType.STRING)
    private Sport sport;


}
