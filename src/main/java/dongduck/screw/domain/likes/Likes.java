package dongduck.screw.domain.likes;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;
import dongduck.screw.domain.user.User;

import javax.persistence.*;


//두번 좋아요 불가능
@Table(uniqueConstraints = {@UniqueConstraint(name="likes_uk", columnNames= {"userId","boardId"})})
@Entity
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name="boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
