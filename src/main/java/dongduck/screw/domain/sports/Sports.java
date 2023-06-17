package dongduck.screw.domain.sports;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;
import dongduck.screw.domain.enumType.SportsType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sports extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy ="sports", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SportsType type;


}
