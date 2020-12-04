package application.battle.history;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import application.battle.piece.Piece;

class HistoryTest {
    
    private History history = new History();
    
    private Map<Point, Piece> createInitializePieceOnBoard(){
        Map<Point, Piece> pieceOnBoard = new HashMap<>();
        pieceOnBoard.put(new Point(1, 2), new Duck(true));
        pieceOnBoard.put(new Point(0, 3), new Elephant(true));
        pieceOnBoard.put(new Point(2, 3), new Giraffe(true));
        pieceOnBoard.put(new Point(1, 3), new Lion(true));
        pieceOnBoard.put(new Point(1, 1), new Duck(false));
        pieceOnBoard.put(new Point(2, 0), new Elephant(false));
        pieceOnBoard.put(new Point(0, 0), new Giraffe(false));
        pieceOnBoard.put(new Point(1, 0), new Lion(false));
        return pieceOnBoard;
    }
    
    @Test
    void testHasFinishedGame() {
        this.history.setPieceOnBoard(createInitializePieceOnBoard());
        this.history.setIs1PlayerTurn(true);
        assertFalse(this.history.hasFinishedGame(), "ライオン 1P:あり, 2P:あり　プレイヤー:2P");
        this.history.setIs1PlayerTurn(false);
        assertFalse(this.history.hasFinishedGame(), "ライオン 1P:あり, 2P:あり　プレイヤー:1P");
        
        this.history.getPieceOnBoard().remove(new Point(1, 3));
        this.history.setIs1PlayerTurn(true);
        assertTrue(this.history.hasFinishedGame(), "ライオン 1P:なし, 2P:あり　プレイヤー:2P");
        this.history.setIs1PlayerTurn(false);
        assertFalse(this.history.hasFinishedGame(), "ライオン 1P:なし, 2P:あり　プレイヤー:1P");
        
        this.history.setPieceOnBoard(createInitializePieceOnBoard());
        this.history.getPieceOnBoard().remove(new Point(1, 0));
        this.history.setIs1PlayerTurn(true);
        assertFalse(this.history.hasFinishedGame(), "ライオン 1P:あり, 2P:なし　プレイヤー:2P");
        this.history.setIs1PlayerTurn(false);
        assertTrue(this.history.hasFinishedGame(), "ライオン 1P:あり, 2P:なし　プレイヤー:1P");
    }
    
}
