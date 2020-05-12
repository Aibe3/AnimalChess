package application.battle.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import application.battle.history.History;
import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import application.battle.piece.Piece;
import application.battle.piece.PieceType;

class BattleModelTest {
    
    private BattleModel model = new BattleModel();
    
    @Test
    void testInit() {
        History h = this.model.init(true);
        Map<Point, Piece> answer = new HashMap();
        answer.put(new Point(0, 0), new Giraffe(false));
        answer.put(new Point(1, 0), new Lion(false));
        answer.put(new Point(2, 0), new Elephant(false));
        answer.put(new Point(1, 1), new Duck(false));
        answer.put(new Point(1, 2), new Duck(true));
        answer.put(new Point(0, 3), new Elephant(true));
        answer.put(new Point(1, 3), new Lion(true));
        answer.put(new Point(2, 3), new Giraffe(true));
        assertTrue(h.is1PlayerTurn());
        
    }
    
    private boolean testPieceOnBoard(Map<Point, Piece> target, Map<Point, Piece> answer) {
        BiFunction<Piece, Piece, Boolean> isSamePiece = (p1, p2) -> {
            if(p1.toPieceType() != p2.toPieceType()) return false;
            if(p1.is1PlayersPiece() ^ p2.is1PlayersPiece()) return false;
            return true;
        };
        BiFunction<Point, Point, Boolean> isSamePoint = (p1, p2) -> {
            if(p1.x != p2.x) return false;
            if(p1.y != p2.y) return false;
            return true;
        };
        BiFunction<Entry<Point, Piece>, Entry<Point, Piece>, Boolean> isSameSituation = (e1, e2) -> {
            if(!isSamePoint.apply(e1.getKey(), e2.getKey())) return false;
            if(!isSamePiece.apply(e1.getValue(), e2.getValue())) return false;
            return true;
        };
        BiFunction<Set<Entry<Point, Piece>>, Set<Entry<Point, Piece>>, Boolean> isSameBoard = (t, a) -> {
            return t.stream().allMatch(e1 -> a.stream().anyMatch(e2 -> isSameSituation.apply(e1, e2)));
        };
        return isSameBoard.apply(target.entrySet(), answer.entrySet());
    }
    
    @Test
    void testGetCanMovePoint() {
        fail("Not yet implemented");
    }
    
    @Test
    void testGetCanPopPoint() {
        fail("Not yet implemented");
    }
    
    @Test
    void testMovePiece() {
        fail("Not yet implemented");
    }
    
}
