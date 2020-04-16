package application.battle.piece;

public class Giraffe extends Piece {

    // 自身の画像ファイル名
    private static final String imageFile = "giraffe.jpeg";

    public Giraffe(int firstX, int firstY, boolean is1playerPiece) {
        super(imageFolder + imageFile, is1playerPiece, firstX, firstY);
        super.range = new boolean[][] { { true, false, true },
                                        { false, false, false },
                                        { true, false, true } };
    }
}
