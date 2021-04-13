package com.SpringWebFlux.battle;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.SpringWebFlux.battle.piece.PieceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattleFormResponse implements Serializable {

    private static final long serialVersionUID = 3683381919421020128L;

    private List<Piece> pieces = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    public class Piece {
        private Point point;
        private PieceType type;
        private boolean is1PlayersPiece;
    }

    private List<Point> canMovePoints = new ArrayList<>();

    private Map<PieceType, Integer> pieceCountOf1P = new HashMap<>();

    private Map<PieceType, Integer> pieceCountOf2P = new HashMap<>();

    private boolean is1PlayerTurn;

    private String winer;
}
