package com.animalchess.battle.piece;

/**
 * TODO:画像をクリックしないとクリックイベントが発生しないが、うまくグリッドのサイズに合わせれない。
 */
public abstract class Piece {
    
    protected boolean is1PlayersPiece;
    
    /**
     * <p>
     * 移動可能な範囲、true:可、false:不可
     * </p>
     * <p>
     * 具象クラスのコンストラクタで初期化する
     * </p>
     * <br>
     * なお、具象クラスではrangeは以下の様に値が代入されている<br>
     * { 0 0, 0 1, 0 2 }<br>
     * { 1 0, 1 1, 1 2 }<br>
     * { 2 0, 2 1, 2 2 }<br>
     * が、それを盤面上に対応させると下記の様になる為注意<br>
     * ┌────┬────┬────┐<br>
     * │ 0 0│ 1 0│ 2 0│<br>
     * ├────┼────┼────┤<br>
     * │ 0 1│ 1 1│ 2 1│<br>
     * ├────┼────┼────┤<br>
     * │ 0 2│ 1 2│ 2 2│<br>
     * └────┴────┴────┘<br>
     * TODO:今更だけこのrangeって現在位置を渡すと移動可能位置を返す計算式であるべきだったよね。。。
     */
    protected boolean[][] moveRange;
    
    public boolean is1PlayersPiece() {
        return is1PlayersPiece;
    }
    
    public boolean isEnemy(Piece piece) {
        return is1PlayersPiece != piece.is1PlayersPiece();
    }
    
    public boolean[][] getMoveRange() {
        return moveRange;
    }
    
    public Piece(boolean is1PlayerPiece) {
        this.is1PlayersPiece = is1PlayerPiece;
    }
    
    public PieceType toPieceType() {
        return PieceType.valueOf(this.getClass().getSimpleName());
    }
}
