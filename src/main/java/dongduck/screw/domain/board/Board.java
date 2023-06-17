package dongduck.screw.domain.board;

import dongduck.screw.domain.area.Area;
import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.likes.Likes;
import dongduck.screw.domain.sports.Sports;
import dongduck.screw.domain.user.User;


import javax.persistence.*;
import java.util.*;

@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="sportsId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sports sports;

    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likeList = new ArrayList<>();

    @JoinColumn(name="areaId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;

    private String title;
    private String content;


}
