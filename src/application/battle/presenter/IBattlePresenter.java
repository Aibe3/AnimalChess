package application.battle.presenter;

import java.awt.Point;

import application.battle.piece.PieceType;

public interface IBattlePresenter {

    public void clickedPiece(Point piecePoint);
    public void clickedMoveRange(Point movePoint);
    public void clickedStorePiece(PieceType type, Boolean is1PlayersPiece);
    public void init(Boolean isFirst1Player);
    public void reset();
}
