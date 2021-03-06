package com.animalchess.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleForm implements Serializable {
    
    private static final long serialVersionUID = 3683381919421020128L;
    
    /**
     * html上での盤面の位置と駒の情報を表すマップ。<br>
     * key:位置, value:駒の情報。<br>
     * 位置は${x}-${y}の形式で表され、種類は${Player}${PieceType}の形式で表される。<br>
     * <br>
     * 例<br>
     * 　盤面最上段左に2Pのキリンがいる場合 => key:0-0, value:2PGiraffe<br>
     * 　盤面最下段中央に1Pのライオンがいる場合 => key:1-3, value:1PLion<br>
     */
    private Map<String, String> pieceOnBoard = new HashMap<>();
    
    /**
     * 移動可能位置のリスト。<br>
     * 位置は${x}-${y}の形式で表される。<br>
     */
    private List<String> canMovePoints = new ArrayList<>();
    
    private Map<String, Integer> pieceCountOf1P = new HashMap<>();
    private Map<String, Integer> pieceCountOf2P = new HashMap<>();
    
    private boolean is1PlayerTurn;
    
    private String winer;
    
    private String clickedPoint;
    
    private boolean is1PlayerStorePiece;
    
    private String storePieceType;
    
    public Map<String, String> getPieceOnBoard() {
        return pieceOnBoard;
    }
    public void setPieceOnBoard(Map<String, String> pieceOnBoard) {
        this.pieceOnBoard = pieceOnBoard;
    }
    
    public List<String> getCanMovePoints() {
        return canMovePoints;
    }
    public void setCanMovePoints(List<String> canMovePoints) {
        this.canMovePoints = canMovePoints;
    }
    
    public Map<String, Integer> getPieceCountOf1P() {
        return pieceCountOf1P;
    }
    public void setPieceCountOf1P(Map<String, Integer> pieceCountOf1P) {
        this.pieceCountOf1P = pieceCountOf1P;
    }
    public Map<String, Integer> getPieceCountOf2P() {
        return pieceCountOf2P;
    }
    public void setPieceCountOf2P(Map<String, Integer> pieceCountOf2P) {
        this.pieceCountOf2P = pieceCountOf2P;
    }
    
    public boolean getIs1PlayerTurn() {
        return is1PlayerTurn;
    }
    public void setIs1PlayerTurn(boolean is1PlayerTurn) {
        this.is1PlayerTurn = is1PlayerTurn;
    }
    
    public String getWiner() {
        return winer;
    }
    public void setWiner(String winer) {
        this.winer = winer;
    }
    
    public String getClickedPoint() {
        return clickedPoint;
    }
    public void setClickedPoint(String clickedPoint) {
        this.clickedPoint = clickedPoint;
    }
    
    public boolean getIs1PlayerStorePiece() {
        return is1PlayerStorePiece;
    }
    public void setIs1PlayerStorePiece(boolean is1PlayerStorePiece) {
        this.is1PlayerStorePiece = is1PlayerStorePiece;
    }
    
    public String getStorePieceType() {
        return storePieceType;
    }
    public void setStorePieceType(String tookPieceType) {
        this.storePieceType = tookPieceType;
    }
}
