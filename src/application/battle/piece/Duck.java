package application.battle.piece;

public class Duck extends Piece {

    // 自身の画像ファイル名
    private static final String imageFile = "duck.jpeg";

    public Duck(int firstX, int firstY, boolean isEnemy) {
        super(imageFolder + imageFile, firstX, firstY, isEnemy);
        boolean isAlly = !isEnemy;
        //代入している値の順序については親クラスのコメントを参照
        super.range = new boolean[][] { { false, false, false },
                                        { isAlly, false, isEnemy },
                                        { false, false, false } };
    }
}
