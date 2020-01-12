package application.battle.piece;

/**
 * 
 * @author ketulavaru
 *
 */
public class Lion extends Piece {

    // 自身の画像ファイル名
    private static final String imageFile = "lion.jpeg";

    public Lion(int firstX, int firstY, boolean isEnemy) {
        super(imageFolder + imageFile, firstX, firstY, isEnemy);
        super.range = new boolean[][] { { true, true, true },
                                        { true, false, true },
                                        { true, true, true } };
    }
}
