package application.battle.view;

import java.awt.Point;
import java.util.List;

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
    public void hideMoveArea(List<Point> points);

    /**
     * 選択中の持ち駒を移動させる場合に使用。
     * @param currentPoint 現在の場所
     * @param movePoint 移動先の場所
     */
    public void movePiece(Point currentPoint, Point movePoint);

    /**
     * 持ち駒を盤上に出す際に使用。
     * TODO:現在の手ばんがどちらかのプレイヤーかを意識すれば第二引数は不要？いや、View層からはその考えは省くべき？
     * @param type 駒の種別
     * @param is1PlayersPiece 出す駒がどちらのプレイヤーのものか
     * @param putPoint 出す箇所
     */
    public void popPiece(PieceType type, Boolean is1PlayersPiece, Point putPoint);

    /**
     * 相手駒を取った場合に使用。
     * @param type 駒の種別
     * @param is1PlayersPiece 取った駒がどちらのプレイヤーのものか
     */
    public void pushPiece(PieceType type, Boolean is1PlayersPiece);

    /**
     * 勝敗が決した際に使用。
     * @param isWin1Player 勝ったプレイヤー
     */
    public void gameSet(Boolean isWin1Player);
}
