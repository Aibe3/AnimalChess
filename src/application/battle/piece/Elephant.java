package application.battle.piece;

public class Elephant extends Piece {

    // 自身の画像ファイル名
    private static final String imageFile = "elephant.jpeg";

    public Elephant(int firstX, int firstY, boolean isEnemy) {
        super(imageFolder + imageFile, firstX, firstY, isEnemy);
        super.range = new boolean[][] { { false, true, false }, 
                                        { true, false, true },
                                        { false, true, false } };
    }
}
