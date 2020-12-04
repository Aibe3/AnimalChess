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
    
    private Map<PieceType, Integer> pieceCountOf1P;
    private Map<PieceType, Integer> pieceCountOf2P;
    
    private boolean is1PlayerTurn;
    
    /**
     * 勝敗が決しているかを返す
     * 操作者していないプレイヤーのライオンの駒が盤面にない場合ゲームの決着と判定する
     * @return true:決着, false:ゲームを続行
     */
    public boolean hasFinishedGame() {
        return this.pieceOnBoard.values().stream().noneMatch(p -> {
            return p.toPieceType() == PieceType.Lion && p.is1PlayersPiece() == this.is1PlayerTurn;
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
    }
    
    public int getTookElephantCount() {
        return this.pieceCountOf1P.get(PieceType.Elephant);
    }
    
    public int getTookGiraffeCount() {
        return this.pieceCountOf1P.get(PieceType.Giraffe);
    }
    
    public int getTakenDuckCount() {
        return this.pieceCountOf2P.get(PieceType.Duck);
    }
    
    public int getTakenElephantCount() {
        return this.pieceCountOf2P.get(PieceType.Elephant);
    }
    
    public int getTakenGiraffeCount() {
        return this.pieceCountOf2P.get(PieceType.Giraffe);
    }
    
    /**
     * {@link History}クラスは操作完了後〜次操作までのキャプチャのため、完了した操作が1Pによる物ならばfalseが返る
     * 
     * @return true:次の操作を行えるプレイヤーが1P, false:否
     */
    public boolean is1PlayerTurn() {
        return is1PlayerTurn;
    }
    
    public void setIs1PlayerTurn(boolean is1PlayerTurn) {
        this.is1PlayerTurn = is1PlayerTurn;
    }
}
