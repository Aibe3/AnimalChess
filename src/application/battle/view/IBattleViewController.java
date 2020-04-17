package application.battle.view;

import java.awt.Point;
import java.util.List;

import application.battle.piece.Piece;
import application.battle.piece.PieceType;

public interface IBattleViewController {

    /**
     * 自駒、持ち駒を選択した際に使用。
     * 指定された場所を移動可能な領域として表示する。
     * @param points 移動可能とする場所
     */
    public void showMoveArea(List<Point> points);

    /**
     * 選択された自駒や持ち駒が再度選択された場合、駒が移動した場合に使用。
     * 指定された移動可能な場所を非表示にする。
     * @param points 非表示とする場所
     */
    public void hideMoveArea();

    /**
     * 引数で指定した駒を作成し画面上に表示する。
     * @param type 駒の種類
     * @param showPoint 駒の位置
     * @param is1PlayersPiece 駒の持ち手
     */
    public void showPiece(PieceType type, Point showPoint, Boolean is1PlayersPiece);

    /**
     * 指定位置の駒を非表示にする。
     * @param point 駒の位置
     */
    public void hidePiece(Point point);

    /**
     * 持ち駒の数を引数で指定した数に更新する。
     * 呼び出しがわは数だけを与え、詳細な表示は実装がわに任せる。
     * 数以外の引数は更新対象の特定に使用する。
     * TODO:メソッドの名前と引数の順序が微妙。。。
     * @param type 更新対象の種類
     * @param is1PlayerPiece 更新対象の持ち手
     * @param count 数
     */
    public void updatePieceCountLabel(PieceType type, Boolean is1PlayerPiece, int count);
    /**
     * 勝敗が決した際に使用。
     * @param isWin1Player 勝ったプレイヤー
     */
    public void gameSet(Boolean isWin1Player);
//    
//    /**
//     * 選択中の持ち駒を移動させる場合に使用。
//     * @param currentPoint 現在の場所
//     * @param movePoint 移動先の場所
//     */
//    public void movePiece(Point currentPoint, Point movePoint);
//    
//    /**
//     * 持ち駒を盤上に出す際に使用。
//     * @param type 
//     * @param is1PlayersPiece 
//     * @param putPoint 
//     */
//    void popPiece(Class<? extends Piece> type, Boolean is1PlayersPiece, Point putPoint);
//    
//    /**
//     * 相手駒を取った場合に使用。
//     * @param type
//     * @param putPoint
//     */
//    void pushPiece(Class<? extends Piece> type, Point putPoint);
//    
}
