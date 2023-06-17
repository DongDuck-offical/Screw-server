package dongduck.screw.domain.area;

import dongduck.screw.domain.base.BaseTimeEntity;
import dongduck.screw.domain.board.Board;

import javax.persistence.*;
import java.util.*;

@Entity
public class Area extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "area",fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();
}
