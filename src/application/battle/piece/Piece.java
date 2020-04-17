package application.battle.piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * TODO:画像をクリックしないとクリックイベントが発生しないが、うまくグリッドのサイズに合わせれない。
 * 
 */
public abstract class Piece extends ImageView {

    // 駒のイメージファイルを格納したフォルダパス
    protected static final String imageFolder = "application/battle/piece/image/";

    // 盤面上でのx座標
    protected int columnIndex;

    // 盤面上でのy座標
    protected int rowIndex;

    //
    protected boolean is1playersPiece;

    /**
     * <p>移動可能な範囲、true:可、false:不可</p>
     * <p>具象クラスのコンストラクタで初期化する</p>
     * <br>
     * なお、具象クラスではrangeは以下の様に値が代入されている<br>
     * 　{ 0 0, 0 1, 0 2 }<br>
     * 　{ 1 0, 1 1, 1 2 }<br>
     * 　{ 2 0, 2 1, 2 2 }<br>
     * が、それを盤面上に対応させると下記の様になる為注意<br>
     * 　┌────┬────┬────┐<br>
     * 　│ 0 0│ 1 0│ 2 0│<br>
     * 　├────┼────┼────┤<br>
     * 　│ 0 1│ 1 1│ 2 1│<br>
     * 　├────┼────┼────┤<br>
     * 　│ 0 2│ 1 2│ 2 2│<br>
     * 　└────┴────┴────┘<br>
     */
    protected boolean[][] range;

    // １度目のクリックイベントが発生した際は選択状態となりtrue。以外はfalse
    protected boolean hasSelected = false;

    // 全ての駒に共通するImageViewとGridPaneとのマージン
    private final Insets margin = new Insets(5, 0, 0, 12);

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public boolean is1PlayersPiece() {
        return is1playersPiece;
    }

    public boolean isEnemy(Piece piece) {
        return is1playersPiece != piece.is1PlayersPiece();
    }

    public boolean[][] getRange() {
        return range;
    }

    public boolean hasSelected() {
        return hasSelected;
    }

    public void setHasSelected(boolean onBoard) {
        this.hasSelected = onBoard;
    }

    /**
     * 
     * @param xPosition
     * @param yPosition
     * @param is1playersPiece
     */
    public Piece(String imageFilePath, boolean is1playersPiece, int xPosition, int yPosition) {
        super(new Image(imageFilePath, 64, 64, true, true));
        if (!is1playersPiece) super.setRotate(180);
        GridPane.setConstraints(this, xPosition, yPosition);
        GridPane.setMargin(this, margin);
        this.columnIndex = xPosition;
        this.rowIndex = yPosition;
        this.is1playersPiece = is1playersPiece;
    }

    public PieceType toPieceType() {
        PieceType type = PieceType.valueOf(this.getClass().getSimpleName());
        return type;
    }

    public List<Point> getCanMovePoints() {
        List<Point> points = new ArrayList<>();
        for (int xRange = 0; xRange < range.length; xRange++) {
            for (int yRange = 0; yRange < range[xRange].length; yRange++) {
                if (!range[xRange][yRange]) continue;
                if (!canMove(xRange, yRange)) continue;
                
                int xPoint = calcNewIndex(this.columnIndex, xRange);
                int yPoint = calcNewIndex(this.rowIndex, yRange);
                points.add(new Point(xPoint, yPoint));
            }
        }
        return points;
    }

    /**
     * 
     * @param xPoint
     * @param yPoint
     * @return
     */
    private boolean canMove(int xPoint, int yPoint) {
        // 盤面左からはみ出すパターン
        if ((columnIndex + xPoint) == 0) return false;
        // 盤面右からはみ出すパターン
        if ((columnIndex + xPoint) == 4) return false;
        // 盤面上からはみ出すパターン
        if ((rowIndex + yPoint) == 0) return false;
        // 盤面下からはみ出すパターン
        if ((rowIndex + yPoint) == 5) return false;
        // 
        return true;
    }

    /**
     * 
     * @param nowIndex
     * @param rangeIndex
     * @return
     */
    private int calcNewIndex(int nowIndex, int rangeIndex) {
        int newIndex = -99;
        switch(rangeIndex){
        case 0:
            newIndex = nowIndex - 1;
            break;
        case 1:
            newIndex = nowIndex;
            break;
        case 2:
            newIndex = nowIndex + 1;
            break;
        }
        return newIndex;
    }
    
    /**
     * 
     * @param columnIndex
     * @param rowIndex
     */
    public void move(int columnIndex, int rowIndex) {
        int newColumnIndex;
        int newRowIndex;
        
        newColumnIndex = calcIndex(this.columnIndex, columnIndex);
        newRowIndex = calcIndex(this.rowIndex, rowIndex);
        
        this.columnIndex = newColumnIndex;
        this.rowIndex = newRowIndex;
        
        GridPane.setConstraints(this, newColumnIndex, newRowIndex);
        
    }
    
    /**
     * 
     * @param nowPoint
     * @param movePoint
     * @return
     */
    private int calcIndex(int nowPoint, int movePoint) {
        int newPoint;
        if (nowPoint < movePoint) {
            newPoint = nowPoint + 1;
        } else if(nowPoint > movePoint) {
            newPoint = nowPoint - 1;
        } else { // nowPoint == movePoint
            newPoint = nowPoint;
        }
        return newPoint;
    }
}
