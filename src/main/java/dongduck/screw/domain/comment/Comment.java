package dongduck.screw.domain.comment;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;
import dongduck.screw.domain.user.User;

import javax.persistence.*;


@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String content;
}
