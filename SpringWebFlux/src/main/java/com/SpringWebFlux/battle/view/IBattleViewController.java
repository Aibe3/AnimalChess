package com.SpringWebFlux.battle.view;

import java.awt.Point;
import java.util.List;

import com.SpringWebFlux.battle.piece.PieceType;

public interface IBattleViewController {

    /**
     * 自駒、持ち駒を選択した際に使用。 指定された場所を移動可能な領域として表示する。
     *
     * @param points 移動可能とする場所
     */
    public void showMoveArea(List<Point> points);

    /**
     * 盤上の移動可能な場所をすべて非表示にする。
     */
    public void hideMoveArea();

    /**
     * 引数で指定した駒を作成し画面上に表示する。
     *
     * @param type            駒の種類
     * @param showPoint       駒の位置
     * @param is1PlayersPiece 駒の持ち手
     */
    public void showPiece(PieceType type, Point showPoint, boolean is1PlayersPiece);

    /**
     * 盤上の駒をすべて非表示にする。
     */
    public void hidePiece();

    /**
     * 持ち駒の数を引数で指定した数に更新する。 呼び出しがわは数だけを与え、詳細な表示は実装がわに任せる。 数以外の引数は更新対象の特定に使用する。
     * TODO:メソッドの名前と引数の順序が微妙。。。
     *
     * @param type           更新対象の種類
     * @param is1PlayerPiece 更新対象の持ち手
     * @param count          数
     */
    public void updateTakePiece(PieceType type, boolean is1PlayerPiece, int count);

    /**
     * 勝敗が決した際に使用。
     *
     * @param isWin1Player 勝ったプレイヤー
     */
    public void gameSet(boolean isWin1Player);
}
