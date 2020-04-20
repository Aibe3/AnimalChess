package application.battle.piece;

public class Elephant extends Piece {
    
    public Elephant(boolean is1PlayerPiece) {
        super(is1PlayerPiece);
        super.moveRange = new boolean[][] { { false, true, false },
                                            { true, false, true },
                                            { false, true, false } };
    }
}
