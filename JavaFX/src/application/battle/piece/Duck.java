package application.battle.piece;

public class Duck extends Piece {
    
    public Duck(boolean is1PlayerPiece) {
        super(is1PlayerPiece);
        super.moveRange = new boolean[][] { { false, false, false }, 
                                            { is1PlayerPiece, false, !is1PlayerPiece },
                                            { false, false, false } };
    }
}
