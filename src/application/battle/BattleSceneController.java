package application.battle;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.SceneController;
import application.battle.board.RangePane;
import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import application.battle.piece.Piece;
import application.dialog.BattleFinishedDialog;
import application.dialog.DecideFirstPlayerDialog;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BattleSceneController implements Initializable {

    @FXML
    private GridPane board;

    @FXML
    private Label tookDucks;
    private int tookDuckCount;

    @FXML
    private Label tookGiraffes;
    private int tookGiraffeCount;

    @FXML
    private Label tookElephants;
    private int tookElephantCount;

    @FXML
    private Label takenDucks;
    private int takenDuckCount;

    @FXML
    private Label takenGiraffes;
    private int takenGiraffeCount;

    @FXML
    private Label takenElephants;
    private int takenElephantCount;

    @FXML
    private ToggleButton playFirst;

    @FXML
    private Button start;

    @FXML
    private Button reset;

    @FXML
    private Button back;

    private Class<?> selectedPiece = null;

    private boolean is1playerTurn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        //TODO:現状このタイミングでStartDialog.show()を呼び出すとうメニュー画面でダイアログが出てしまう
        //TODO:個人的にはゲーム画面が表示されたloadedのタイミングでダイアログを表示し、先攻後攻を判断したい
        this.is1playerTurn = DecideFirstPlayerDialog.show();
    }

    /**
     * 
     */
    public void init() {

        // 盤上の駒を一旦全て削除
        this.board.getChildren().removeIf(child -> !child.getClass().equals(Group.class));

        // 盤上の駒を初期化
        addInitializedPiece(Duck.class, 1, 1, false);
        addInitializedPiece(Giraffe.class, 0, 0, false);
        addInitializedPiece(Elephant.class, 2, 0, false);
        addInitializedPiece(Lion.class, 1, 0, false);

        addInitializedPiece(Duck.class, 1, 2, true);
        addInitializedPiece(Giraffe.class, 2, 3, true);
        addInitializedPiece(Elephant.class, 0, 3, true);
        addInitializedPiece(Lion.class, 1, 3, true);
        
        // 持ち駒を初期化
        updatePieceCountLabel(Duck.class, false, 0);
        updatePieceCountLabel(Giraffe.class, false, 0);
        updatePieceCountLabel(Elephant.class, false, 0);
        
        updatePieceCountLabel(Duck.class, true, 0);
        updatePieceCountLabel(Giraffe.class, true, 0);
        updatePieceCountLabel(Elephant.class, true, 0);
    }

    /**
     * 
     * @param pieceType
     * @param xPosition
     * @param yPosition
     * @param is1playerPiece
     */
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

    /**
     * 
     * @param event
     */
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
                finishiedBattle(is1playerTurn);
                return;
            }
            increasePieceCount(clickedPiece.getClass(), is1playerTurn);
        }
        
        // 選択されている駒を移動させる
        GridPane.setConstraints(selectedPiece, xPoint, yPoint);
        selectedPiece.move(xPoint, yPoint);
        selectedPiece.setHasSelected(false);
        this.board.getChildren().removeIf(child -> RangePane.class.equals(child.getClass()));
        this.selectedPiece = null;
        
        this.is1playerTurn = !this.is1playerTurn;
    }

    private void finishiedBattle(Boolean isWin1Player) {
        BattleFinishedDialog.show(isWin1Player);
        init();
        this.is1playerTurn = DecideFirstPlayerDialog.show();
    }

    private void increasePieceCount(Class<?> pieceClass, Boolean is1PlayerPiece) {
        String fieldPrefix;
        if (is1PlayerPiece) {
            fieldPrefix = "taken" + pieceClass.getSimpleName();
        } else {
            fieldPrefix = "took" + pieceClass.getSimpleName();
        }
        
        try {
            // 保持している件数の更新
            Field holdCount;
            holdCount = this.getClass().getDeclaredField(fieldPrefix + "Count");
            int count = holdCount.getInt(this) + 1;
            holdCount.setInt(this, count);
            updatePieceCountLabel(pieceClass, is1PlayerPiece, count);
        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はコードがバグっているのでStackTrace出力後に握りつぶす
        }
    }

    private void decreasePieceCount(Class<?> pieceClass, Boolean is1PlayerPiece) {
        String fieldPrefix;
        if (is1PlayerPiece) {
            fieldPrefix = "taken" + pieceClass.getSimpleName();
        } else {
            fieldPrefix = "took" + pieceClass.getSimpleName();
        }
        
        try {
            // 保持している件数の更新
            Field holdCount;
            holdCount = this.getClass().getDeclaredField(fieldPrefix + "Count");
            int count = holdCount.getInt(this) - 1;
            holdCount.setInt(this, count);
            updatePieceCountLabel(pieceClass, is1PlayerPiece, count);
        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はコードがバグっているのでStackTrace出力後に握りつぶす
        }
    }

    private void updatePieceCountLabel(Class<?> pieceClass, Boolean is1PlayerPiece, int count) {
        String fieldLabelName = pieceClass.getSimpleName() + "s";
        if (is1PlayerPiece) {
            fieldLabelName = "took" + fieldLabelName;
        } else {
            fieldLabelName = "taken" + fieldLabelName;
        }
        
        try {
            // ラベルのテキストの更新
            Field countLabel;
            countLabel = this.getClass().getDeclaredField(fieldLabelName);
            Method setText = Label.class.getMethod("setText", String.class);
            String newText = pieceClass.getSimpleName() + " * " + count;
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
        decreasePieceCount(this.selectedPiece, this.is1playerTurn);
        this.selectedPiece = null;
        this.is1playerTurn = !this.is1playerTurn;
    }

    private void onTakePieceLabelClicked(Class<?> pieceType, boolean is1Player) {
        
        if(this.is1playerTurn != is1Player) return;
        
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

    @FXML
    public void onTookDucksClicked(MouseEvent event) {
        onTakePieceLabelClicked(Duck.class, true);
    }

    @FXML
    public void onTookGiraffesClicked(MouseEvent event) {
        onTakePieceLabelClicked(Giraffe.class, true);
    }

    @FXML
    public void onTookElephantsClicked(MouseEvent event) {
        onTakePieceLabelClicked(Elephant.class, true);
    }

    @FXML
    public void onTakenDucksClicked(MouseEvent event) {
        onTakePieceLabelClicked(Duck.class, false);
    }

    @FXML
    public void onTakenGiraffesClicked(MouseEvent event) {
        onTakePieceLabelClicked(Giraffe.class, false);
    }

    @FXML
    public void onTakenElephantsClicked(MouseEvent event) {
        onTakePieceLabelClicked(Elephant.class, false);
    }

    @FXML
    public void onPlayFirstClicked(MouseEvent event) {
        onTakePieceLabelClicked(Duck.class, true);
    }

    @FXML
    public void onStartClicked(MouseEvent event) {
        //TODO:今のところこのボタンResetと変わらないからあんまり意味ない
        init();
        this.is1playerTurn = DecideFirstPlayerDialog.show();
    }

    @FXML
    public void onResetClicked(MouseEvent event) {
        init();
        this.is1playerTurn = DecideFirstPlayerDialog.show();
    }

    @FXML
    public void onBackClicked(MouseEvent event) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.changeScene("menu/MenuScene.fxml");
    }
}
