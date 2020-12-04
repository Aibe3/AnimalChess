package com.animalchess.battle.piece;

public class Giraffe extends Piece {
    
    public Giraffe(boolean is1PlayerPiece) {
        super(is1PlayerPiece);
        super.moveRange = new boolean[][] { { true, false, true },
                                            { false, false, false },
                                            { true, false, true } };
    }
}
