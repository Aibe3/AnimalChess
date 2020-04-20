package application.battle.history;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import application.battle.piece.Piece;
import application.battle.piece.PieceType;

/**
 * 操作が完了してから次の操作が行われる間のキャプチャを表す
 */
public class History {
    
    private Map<Point, Piece> pieceOnBoard;
    
    private Map<PieceType, Integer> pieceCountOf1P;
    private Map<PieceType, Integer> pieceCountOf2P;
    
//    private int tookDuckCount = 0;
//    private int tookElephantCount = 0;
//    private int tookGiraffeCount = 0;
//    private int takenDuckCount = 0;
//    private int takenElephantCount = 0;
//    private int takenGiraffeCount = 0;
//    
    private Boolean is1PlayerTurn;
    
    public Boolean hasFinishedGame() {
        return this.pieceOnBoard.values().stream().noneMatch(p -> {
            return p.is1PlayersPiece() == this.is1PlayerTurn && p.toPieceType() == PieceType.Lion;
        });
    }
    
    public Map<Point, Piece> getPieceOnBoard() {
        return pieceOnBoard;
    }
    
    public void setPieceOnBoard(Map<Point, Piece> board) {
        this.pieceOnBoard = board;
    }
    
    public Map<PieceType, Integer> getPieceCountOf1P() {
        return pieceCountOf1P;
    }
    
    public void setPieceCountOf1P(Map<PieceType, Integer> pieceCountOf1P) {
        this.pieceCountOf1P = pieceCountOf1P;
    }
    
    public Map<PieceType, Integer> getPieceCountOf2P() {
        return pieceCountOf2P;
    }
    
    public void setPieceCountOf2P(Map<PieceType, Integer> pieceCountOf2P) {
        this.pieceCountOf2P = pieceCountOf2P;
    }
    
    public int getTookDuckCount() {
        return this.pieceCountOf1P.get(PieceType.Duck);
//        return tookDuckCount;
    }
    
//    public void setTookDuckCount(int tookDuckCount) {
//        this.tookDuckCount = tookDuckCount;
//    }
//    
    public int getTookElephantCount() {
        return this.pieceCountOf1P.get(PieceType.Elephant);
//        return tookElepahntCount;
    }
    
//    public void setTookElepahntCount(int tookElephantCount) {
//        this.tookElephantCount = tookElephantCount;
//    }
//    
    public int getTookGiraffeCount() {
        return this.pieceCountOf1P.get(PieceType.Giraffe);
//        return tookGiraffeCount;
    }
    
//    public void setTookGiraffeCount(int tookGiraffeCount) {
//        this.tookGiraffeCount = tookGiraffeCount;
//    }
//    
    public int getTakenDuckCount() {
        return this.pieceCountOf2P.get(PieceType.Duck);
//        return takenDuckCount;
    }
    
//    public void setTakenDuckCount(int takenDuckCount) {
//        this.takenDuckCount = takenDuckCount;
//    }
//    
    public int getTakenElephantCount() {
        return this.pieceCountOf2P.get(PieceType.Elephant);
//        return takenElephantCount;
    }
    
//    public void setTakenElephantCount(int takenElephantCount) {
//        this.takenElephantCount = takenElephantCount;
//    }
//    
    public int getTakenGiraffeCount() {
        return this.pieceCountOf2P.get(PieceType.Giraffe);
//        return takenGiraffeCount;
    }
    
//    public void setTakenGiraffeCount(int takenGiraffeCount) {
//        this.takenGiraffeCount = takenGiraffeCount;
//    }
//    
    /**
     * {@link History}クラスは操作完了後〜次操作までのキャプチャのため、完了した操作が1Pによる物ならばfalseが返る
     * 
     * @return true:次の操作を行えるプレイヤーが1P, false:否
     */
    public Boolean is1PlayerTurn() {
        return is1PlayerTurn;
    }
    
    public void setIs1PlayerTurn(Boolean is1PlayerTurn) {
        this.is1PlayerTurn = is1PlayerTurn;
    }
}
