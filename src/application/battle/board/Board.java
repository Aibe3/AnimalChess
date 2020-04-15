package application.battle.board;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import application.battle.piece.Piece;
import application.dialog.BattleFinishedDialog;
import application.dialog.DecideFirstPlayerDialog;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Board {

    //
    private GridPane board;

    private int tookDuckCount;

    private int tookGiraffeCount;

    private int tookElephantCount;

    private int takenDuckCount;

    private int takenGiraffeCount;

    private int takenElephantCount;

    private Class<?> selectedPiece = null;

    private boolean is1playerTurn;

    public Board(GridPane boardControl) {
        this.board = boardControl;
        init();
    }

    /**
     * 
     */
    public void init() {

        // 盤上の駒を一旦全て削除
        this.board.getChildren().removeIf(child -> !child.getClass().equals(Group.class));

        // 相手の駒を生成し盤上に追加
        addInitializedPiece(Duck.class, 1, 1, false);
        addInitializedPiece(Giraffe.class, 0, 0, false);
        addInitializedPiece(Elephant.class, 2, 0, false);
        addInitializedPiece(Lion.class, 1, 0, false);

        // 自分の駒を生成し盤上に追加
        addInitializedPiece(Duck.class, 1, 2, true);
        addInitializedPiece(Giraffe.class, 2, 3, true);
        addInitializedPiece(Elephant.class, 0, 3, true);
        addInitializedPiece(Lion.class, 1, 3, true);
    }

    private void addInitializedPiece(Class<?> pieceType, int xPosition, int yPosition, boolean is1playerPiece) {
        try {
            Constructor<?> constructor = pieceType.getConstructor(int.class, int.class, boolean.class);
            Piece piece = (Piece) constructor.newInstance(xPosition, yPosition, is1playerPiece);
            piece.setOnMouseClicked(e -> onPieceClicked(e));
            this.board.getChildren().add(piece);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onPieceClicked(MouseEvent event) {
        System.out.print("clicked " );
        Piece clickedPiece = (Piece) event.getPickResult().getIntersectedNode();
        System.out.println(clickedPiece);
        
        if (clickedPiece.hasSelected()) {
            //選択された駒の場合
            clickedPiece.setHasSelected(false);
            this.board.getChildren().removeIf(child -> RangePane.class.equals(child.getClass()));
            this.selectedPiece = null;
        } else if (clickedPiece.is1playersPiece() == is1playerTurn) {
            //見方の駒の場合
            
            //見方駒だが選択中の駒と異なる場合は処理終了
            if(this.selectedPiece != null && this.selectedPiece != clickedPiece.getClass()) return;
            
            clickedPiece.setHasSelected(true);
            showMoveRange(clickedPiece);
            this.selectedPiece = clickedPiece.getClass();
        } else {
            //敵の駒の場合
            return;
        }
    }
    
    private void showMoveRange(Piece selectedPiece) {
        List<Point> points = selectedPiece.getCanMovePoints();
        System.out.println(points);
        for(Point point : points) {
            Piece inRangePiece = getPiece(point.x, point.y);
            if (inRangePiece == null) {
                board.add(new RangePane(this::clickedRangePane), point.x, point.y);
            } else {
                if (selectedPiece.isEnemy(inRangePiece)) {
                    this.board.add(new RangePane(this::clickedRangePane),
                            inRangePiece.getColumnIndex(),
                            inRangePiece.getRowIndex());
                }
            }
        }
    }
    
    /**
     * 
     * @param columnIndex
     * @param rowIndex
     * @return
     */
    private Piece getPiece(int columnIndex, int rowIndex) {
        for (Node piece : this.board.getChildren()) {
            if (!Piece.class.equals(piece.getClass().getSuperclass())) continue;
            if (columnIndex == GridPane.getColumnIndex(piece)
                    && rowIndex == GridPane.getRowIndex(piece)) {
                return (Piece)piece;
            }
        }
        return null;
    }
    
    /**
     * RangePane(駒の移動先のパネル)が押下された際の動作。
     * RangePneを全て除去し、選択中の駒(取った駒のラベルクリックも含む)をクリックしたパネルの位置に移動させる。
     * @param event
     */
    public void clickedRangePane(MouseEvent event) {
        System.out.println("clickedRangePane " + event);
        
        // 移動対象の駒を取得する
        Piece selectedPiece = null;
        for(Node child : this.board.getChildren()) {
            if (!Piece.class.equals(child.getClass().getSuperclass())) continue;
            Piece temp = (Piece)child;
            if (temp.hasSelected()) {
                selectedPiece = temp;
            }
        }
        
        Pane clickedPane = (Pane)event.getSource();
        int xPoint = GridPane.getColumnIndex(clickedPane);
        int yPoint = GridPane.getRowIndex(clickedPane);
        
        // 移動先に相手駒がある場合は手持ちの駒に加える
        Piece clickedPiece = getPiece(xPoint, yPoint);
        if (clickedPiece != null && selectedPiece.isEnemy(clickedPiece)) {
            this.board.getChildren().remove(clickedPiece);
            if (Lion.class.equals(clickedPiece.getClass())) {
                boolean isWin1Player = !clickedPiece.is1playersPiece();
                BattleFinishedDialog.show(isWin1Player);
                init();
                this.is1playerTurn = DecideFirstPlayerDialog.show();
                return;
            } else {
                upPieceCount(clickedPiece);
            }
        }
        
        // 選択されている駒を移動させる
        GridPane.setConstraints(selectedPiece, xPoint, yPoint);
        selectedPiece.move(xPoint, yPoint);
        selectedPiece.setHasSelected(false);
        this.board.getChildren().removeIf(child -> RangePane.class.equals(child.getClass()));
        this.selectedPiece = null;
        
        this.is1playerTurn = !this.is1playerTurn;
    }
    
    /**
     * 
     * @param deadPiece
     */
    private void upPieceCount(Piece deadPiece) {
        String fieldPrefix;
        if (deadPiece.is1playersPiece()) {
            fieldPrefix = "taken" + deadPiece.getClass().getSimpleName();
        } else {
            fieldPrefix = "took" + deadPiece.getClass().getSimpleName();
        }
        
        Field holdCount;
        Field countLabel;
        try {
            holdCount = this.getClass().getDeclaredField(fieldPrefix + "Count");
            holdCount.setInt(this, holdCount.getInt(this) + 1);
            countLabel = this.getClass().getDeclaredField(fieldPrefix + "s");
            Method setText = Label.class.getMethod("setText", String.class);
            String newText = deadPiece.getClass().getSimpleName() + " * " + holdCount.getInt(this);
            setText.invoke(countLabel.get(this), newText);
        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はコードがバグっているのでStackTrace出力後に握りつぶす
        }
        
    }

    public void putPieceToBoard(MouseEvent event) {
        this.board.getChildren().removeIf(child -> RangePane.class.equals(child.getClass()));
        Node source =  (Node)event.getSource();
        int x = GridPane.getColumnIndex(source);
        int y = GridPane.getRowIndex(source);
        addInitializedPiece(this.selectedPiece, x, y, this.is1playerTurn);
        this.selectedPiece = null;
        this.is1playerTurn = !this.is1playerTurn;
    }

    private void onTakePieceLabelClicked(Class<?> pieceType, boolean is1Player) {
        
        if(this.is1playerTurn == is1Player) return;
        
        if(this.selectedPiece == null) {
            //盤上で駒が有るか無いかの一覧を作る
            int columnSize = 3;
            int rowSize = 4;
            boolean[][] piecePoints = new boolean[rowSize][columnSize];
            for(Node child : this.board.getChildren()) {
                if(child.getClass().equals(Group.class)) continue;
                piecePoints[GridPane.getRowIndex(child)][GridPane.getColumnIndex(child)] = true;
            }
            
            //一覧に沿って空いてるマスにRangePaneを追加していく
            for (int y = 0; y < rowSize; y++) {
                for(int x = 0; x < columnSize; x++) {
                    if(!piecePoints[y][x]) this.board.add(new RangePane(this::putPieceToBoard), x, y);
                }
            }
            
            //対象の駒を保持する
            this.selectedPiece = pieceType;
        } else {
            this.selectedPiece = null;
        }
    }
}
