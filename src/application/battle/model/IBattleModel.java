package application.battle.model;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import application.battle.history.History;
import application.battle.piece.PieceType;

public interface IBattleModel {
    public History init(Boolean is1playerTurn);
    public List<Point> getCanMoveRange(Point piecePoint);
    public List<Point> getCanPopRange(PieceType storePiece, Boolean is1PlayersPiece);
    public History movePiece(Point movePoint);
}
