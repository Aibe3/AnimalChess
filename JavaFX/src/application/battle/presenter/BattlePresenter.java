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
    public void init(boolean isFirst1Player) {
        this.view.hideMoveArea();
        this.view.hidePiece();
        History nowSituation = this.model.init(isFirst1Player);
        showAllPiece(nowSituation.getPieceOnBoard());
        updatePieceCount(nowSituation.getPieceCountOf1P(), true);
        updatePieceCount(nowSituation.getPieceCountOf2P(), false);
    }
    
    @Override
    public void clickedPiece(Point piecePoint) {
        List<Point> points = this.model.getCanMovePoint(piecePoint);
        this.view.hideMoveArea();
        this.view.showMoveArea(points);
    }
    
    @Override
    public void clickedMoveRange(Point movePoint) {
        History nowSituation = this.model.movePiece(movePoint);
        
        this.view.hideMoveArea();
        this.view.hidePiece();
        showAllPiece(nowSituation.getPieceOnBoard());
        
        if (!nowSituation.is1PlayerTurn()) updatePieceCount(nowSituation.getPieceCountOf1P(), true);
        else updatePieceCount(nowSituation.getPieceCountOf2P(), false);
        
        if (nowSituation.hasFinishedGame()) this.view.gameSet(!nowSituation.is1PlayerTurn());
    }
    
    private void showAllPiece(Map<Point, Piece> pieceOnBoard) {
        pieceOnBoard.keySet().forEach(point -> {
            Piece piece = pieceOnBoard.get(point);
            this.view.showPiece(piece.toPieceType(), point, piece.is1PlayersPiece());
        });
    }
    
    private void updatePieceCount(Map<PieceType, Integer> countMap, boolean is1PlayersPiece) {
        countMap.entrySet().forEach(entry -> {
            this.view.updateTakePiece(entry.getKey(), is1PlayersPiece, entry.getValue());
        });
    }
    
    @Override
    public void clickedStorePiece(PieceType type, boolean is1PlayersPiece) {
        List<Point> points = this.model.getCanPopPoint(type, is1PlayersPiece);
        this.view.hideMoveArea();
        this.view.showMoveArea(points);
    }
}
