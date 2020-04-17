package application.battle.history;

import java.awt.Point;
import java.util.Map;

import application.battle.piece.Piece;
import application.battle.piece.PieceType;

/**
 * 操作が完了してから次の操作が行われる間のキャプチャを表す
 */
public class History {
    
    private Map<Point, Piece> pieceOnBoard;
    
    private int tookDuckCount = 0;
    private int tookGiraffeCount = 0;
    private int tookElepahntCount = 0;
    private int takenDuckCount = 0;
    private int takenGiraffeCount = 0;
    private int takenElepahntCount = 0;
    
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
    
    public int getTookDuckCount() {
        return tookDuckCount;
    }
    
    public void setTookDuckCount(int tookDuckCount) {
        this.tookDuckCount = tookDuckCount;
    }
    
    public int getTookGiraffeCount() {
        return tookGiraffeCount;
    }
    
    public void setTookGiraffeCount(int tookGiraffeCount) {
        this.tookGiraffeCount = tookGiraffeCount;
    }
    
    public int getTookElepahntCount() {
        return tookElepahntCount;
    }
    
    public void setTookElepahntCount(int tookElepahntCount) {
        this.tookElepahntCount = tookElepahntCount;
    }
    
    public int getTakenDuckCount() {
        return takenDuckCount;
    }
    
    public void setTakenDuckCount(int takenDuckCount) {
        this.takenDuckCount = takenDuckCount;
    }
    
    public int getTakenGiraffeCount() {
        return takenGiraffeCount;
    }
    
    public void setTakenGiraffeCount(int takenGiraffeCount) {
        this.takenGiraffeCount = takenGiraffeCount;
    }
    
    public int getTakenElepahntCount() {
        return takenElepahntCount;
    }
    
    public void setTakenElepahntCount(int takenElepahntCount) {
        this.takenElepahntCount = takenElepahntCount;
    }
    
    /**
     * {@link History}クラスは操作完了後〜次操作までのキャプチャのため、完了した操作が1Pによる物ならばfalseが返る
     * @return true:次の操作を行えるプレイヤーが1P, false:否
     */
    public Boolean is1PlayerTurn() {
        return is1PlayerTurn;
    }
    
    public void setIs1PlayerTurn(Boolean is1PlayerTurn) {
        this.is1PlayerTurn = is1PlayerTurn;
    }
}
