package com.SpringWebFlux.battle.presenter;

import java.awt.Point;

import com.SpringWebFlux.battle.piece.PieceType;

public interface IBattlePresenter {

    public void clickedPiece(Point piecePoint);

    public void clickedMoveRange(Point movePoint);

    public void clickedStorePiece(PieceType type, boolean is1PlayersPiece);

    public void init(boolean isFirst1Player);
}
