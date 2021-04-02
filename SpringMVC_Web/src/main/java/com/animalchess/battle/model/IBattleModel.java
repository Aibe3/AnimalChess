package com.animalchess.battle.model;

import java.awt.Point;
import java.util.List;

import com.animalchess.battle.history.History;
import com.animalchess.battle.piece.PieceType;

public interface IBattleModel {
    public History init(boolean is1playerTurn);
    
    public List<Point> getCanMovePoint(Point piecePoint);
    
    public List<Point> getCanPopPoint(PieceType storePiece, boolean is1PlayersPiece);
    
    public History movePiece(Point movePoint);
}
