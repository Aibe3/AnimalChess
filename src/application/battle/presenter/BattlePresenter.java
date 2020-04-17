package application.battle.presenter;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import application.battle.history.History;
import application.battle.model.BattleModel;
import application.battle.model.IBattleModel;
import application.battle.piece.Piece;
import application.battle.piece.PieceType;
import application.battle.view.IBattleViewController;

public class BattlePresenter implements IBattlePresenter {
    
    private IBattleViewController view;
    
    private IBattleModel model;
    
    public BattlePresenter(IBattleViewController view) {
        this.view = view;
        this.model = new BattleModel();
    }
    
    @Override
    public void init(Boolean isFirst1Player) {
        this.view.hideMoveArea();
        History nowSituation = this.model.init(isFirst1Player);
        Map<Point, Piece> pieceOnBoard = nowSituation.getPieceOnBoard();
        pieceOnBoard.keySet().forEach(po -> {
            Piece pi = pieceOnBoard.get(po);
            this.view.showPiece(pi.toPieceType(), po, pi.is1PlayersPiece());
        });
    }
    
    @Override
    public void clickedPiece(Point piecePoint) {
        List<Point> points = this.model.getCanMoveRange(piecePoint);
        this.view.hideMoveArea();
        this.view.showMoveArea(points);
    }
    
    @Override
    public void clickedMoveRange(Point movePoint) {
        History nowSituation = this.model.movePiece(movePoint);
        this.view.hideMoveArea();
        this.view.hidePiece(movePoint);
        PieceType movePieceType = nowSituation.getPieceOnBoard().get(movePoint).toPieceType();
        Boolean is1PlayersOperate = !nowSituation.is1PlayerTurn();
        this.view.showPiece(movePieceType, movePoint, is1PlayersOperate);
        if (nowSituation.hasFinishedGame()) this.view.gameSet(nowSituation.is1PlayerTurn());
    }
    
    @Override
    public void clickedStorePiece(PieceType type, Boolean is1PlayersPiece) {
        List<Point> points = this.model.getCanPopRange(type, is1PlayersPiece);
        this.view.hideMoveArea();
        this.view.showMoveArea(points);
    }
    
//    @Override
//    public void reset() {
//        // TODO:これはいるのか。。。？
//    }
}
