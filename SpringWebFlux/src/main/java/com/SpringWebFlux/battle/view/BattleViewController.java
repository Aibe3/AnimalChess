package com.SpringWebFlux.battle.view;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.SpringWebFlux.battle.BattleFormResponse;
import com.SpringWebFlux.battle.piece.PieceType;
import com.SpringWebFlux.battle.presenter.BattlePresenter;
import com.SpringWebFlux.battle.presenter.IBattlePresenter;

@Component
public class BattleViewController implements IBattleViewController {

    private BattleFormResponse form;

    private IBattlePresenter presenter;

    public BattleViewController() {
        this.presenter = new BattlePresenter(this);
    }

    public BattleFormResponse init(boolean isFirst1Player) {
        this.form = new BattleFormResponse();
        this.form.set1PlayerTurn(isFirst1Player);
        this.presenter.init(isFirst1Player);
        return this.form;
    }

    public BattleFormResponse clickedPiece(Point piecePoint) {
        this.presenter.clickedPiece(piecePoint);
        return this.form;
    }

    public BattleFormResponse clickedStorePiece(PieceType type, boolean is1PlayersPiece) {
        this.presenter.clickedStorePiece(type, is1PlayersPiece);
        return this.form;
    }

    public BattleFormResponse clickedMoveRange(Point movePoint) {
        this.form.set1PlayerTurn(!this.form.is1PlayerTurn());
        this.presenter.clickedMoveRange(movePoint);
        return this.form;
    }

    @Override
    public void showMoveArea(List<Point> points) {
        this.form.setCanMovePoints(points);
    }

    @Override
    public void hideMoveArea() {
        this.form.setCanMovePoints(new ArrayList<>());
    }

    @Override
    public void showPiece(PieceType type, Point showPoint, boolean is1PlayersPiece) {
        this.form.getPieces().add(this.form.new Piece(showPoint, type, is1PlayersPiece));
    }

    @Override
    public void hidePiece() {
        this.form.setPieces(new ArrayList<>());
    }

    @Override
    public void updateTakePiece(PieceType type, boolean is1PlayerPiece, int count) {
        Consumer<Map<PieceType, Integer>> update = m -> {
            if(m.containsKey(type)) m.replace(type, count);
            else m.put(type, count);
        };
        if(is1PlayerPiece) update.accept(this.form.getPieceCountOf1P());
        else update.accept(this.form.getPieceCountOf2P());

    }

    @Override
    public void gameSet(boolean isWin1Player) {
        this.form.setWiner(isWin1Player?"1P":"2P");
    }

}
