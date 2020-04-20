package application.battle.piece;

import java.lang.reflect.Constructor;

public enum PieceType {
    Duck, Elephant, Giraffe, Lion;
    
    public Piece toPiece(boolean is1PlayersPiece) {
        Piece piece = null;
        try {
            String classPath = this.getClass().getPackage().getName() + "." + this.toString();
            Class<?> pieceClass = Class.forName(classPath);
            Constructor<?> constructor = pieceClass.getConstructor(boolean.class);
            piece = (Piece) constructor.newInstance(is1PlayersPiece);
        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はコードがバグっているのでStackTrace出力後に握りつぶす
        }
        return piece;
    }
}
