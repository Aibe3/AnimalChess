package application.battle.model;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import application.battle.piece.Piece;
import application.battle.piece.PieceType;

public class BattleModel implements IBattleModel {

    private Piece[][] board = new Piece[2][3];

    private int tookDuckCount;
    private int tookGiraffeCount;
    private int tookElepahntCount;
    private int takenDuckCount;
    private int takenGiraffeCount;
    private int takenElepahntCount;

    @Override
    public List<Map<PieceType, Point>> init() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Point> getCanMoveRange(Point piecePoint) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Point> getCanPopRange(PieceType storePiece, Boolean is1PlayersPiece) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Boolean> movePiece(Point movePoint) {
        // TODO Auto-generated method stub
        return null;
    }

}
