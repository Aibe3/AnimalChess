package com.animalchess.battle.piece;

public class Lion extends Piece {
    
    public Lion(boolean is1PlayerPiece) {
        super(is1PlayerPiece);
        super.moveRange = new boolean[][] { { true, true, true },
                                            { true, false, true },
                                            { true, true, true } };
    }
}
