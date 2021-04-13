package com.SpringWebFlux.battle.model;

import java.awt.Point;
import java.util.List;

import com.SpringWebFlux.battle.history.History;
import com.SpringWebFlux.battle.piece.PieceType;

public interface IBattleModel {
    public History init(boolean is1playerTurn);
    
    public List<Point> getCanMovePoint(Point piecePoint);
    
    public List<Point> getCanPopPoint(PieceType storePiece, boolean is1PlayersPiece);
    
    public History movePiece(Point movePoint);
}
