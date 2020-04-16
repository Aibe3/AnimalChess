package application.battle.presenter;

import java.awt.Point;

import application.battle.model.IBattleModel;
import application.battle.piece.PieceType;
import application.battle.view.IBattleViewController;

public class BattlePresenter implements IBattlePresenter {

    private IBattleViewController view;
    
    private IBattleModel model;
    
    public BattlePresenter(IBattleViewController view) {
        this.view = view;
        
    }
    
    @Override
    public void clickedPiece(Point piecePoint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickedMoveRange(Point movePoint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickedStorePiece(PieceType type, Boolean is1PlayersPiece) {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(Boolean isFirst1Player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub

    }

}
