package com.animalchess.battle.view;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.animalchess.battle.BattleForm;
import com.animalchess.battle.piece.PieceType;
import com.animalchess.battle.presenter.BattlePresenter;
import com.animalchess.battle.presenter.IBattlePresenter;

@Component
public class BattleViewController implements IBattleViewController {
    
    private BattleForm form;
    
    private IBattlePresenter presenter;
    
    public BattleViewController() {
        this.form = new BattleForm();
        this.presenter = new BattlePresenter(this);
    }
    
    public BattleForm init(boolean isFirst1Player) {
        this.presenter.init(isFirst1Player);
        return this.form;
    }
    
    public BattleForm clickedPiece(Point piecePoint) {
        this.presenter.clickedPiece(piecePoint);
        return this.form;
    }
    
    public BattleForm clickedStorePiece(PieceType type, boolean is1PlayersPiece) {
        this.presenter.clickedStorePiece(type, is1PlayersPiece);
        return this.form;
    }
    
    public BattleForm clickedMoveRange(Point movePoint) {
        this.presenter.clickedMoveRange(movePoint);
        return this.form;
    }
    
    @Override
    public void showMoveArea(List<Point> points) {
        this.form.setCanMovePoints(points.stream().map(p -> p.x + "-" + p.y).collect(Collectors.toList()));
    }
    
    @Override
    public void hideMoveArea() {
        this.form.setCanMovePoints(new ArrayList<>());
    }
    
    @Override
    public void showPiece(PieceType type, Point showPoint, boolean is1PlayersPiece) {
        String point = showPoint.x + "-" + showPoint.y;
        String piece = is1PlayersPiece?"1P":"2P" + type.toString();
        this.form.getPieceOnBoard().put(point, piece);
    }
    
    @Override
    public void hidePiece() {
        this.form.setPieceOnBoard(new HashMap<>());
    }
    
    @Override
    public void updateTakePiece(PieceType type, boolean is1PlayerPiece, int count) {
        Consumer<Map<String, Integer>> update = m -> {
            if(m.containsKey(type.toString())) m.replace(type.toString(), count);
            else m.put(type.toString(), count);
        };
        if(is1PlayerPiece) update.accept(this.form.getPieceCountOf1P());
        else update.accept(this.form.getPieceCountOf2P());
        
    }
    
    @Override
    public void gameSet(boolean isWin1Player) {
        // TODO Auto-generated method stub
        
    }
    
}
