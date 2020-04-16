package application.battle.piece;

/**
 * 
 */
public class Lion extends Piece {

    // 自身の画像ファイル名
    private static final String imageFile = "lion.jpeg";

    public Lion(int firstX, int firstY, boolean is1playerPiece) {
        super(imageFolder + imageFile, is1playerPiece, firstX, firstY);
        super.range = new boolean[][] { { true, true, true },
                                        { true, false, true },
                                        { true, true, true } };
    }
}
