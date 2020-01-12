package application.battle.piece;

public class Giraffe extends Piece {

    // 自身の画像ファイル名
    private static final String imageFile = "giraffe.jpeg";

    public Giraffe(int firstX, int firstY, boolean isEnemy) {
        super(imageFolder + imageFile, firstX, firstY, isEnemy);
        super.range = new boolean[][] { { true, false, true },
                                        { false, false, false },
                                        { true, false, true } };
    }
}
