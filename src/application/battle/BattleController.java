package application.battle;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.SceneController;
import application.battle.board.Board;
import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import application.battle.piece.Piece;
import application.dialog.FinishedDialog;
import application.dialog.StartDialog;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BattleController implements Initializable {

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

    // 
    private boolean hasSelectedPiece = false;
    
    // 
    private boolean isPlayerTurn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        //TODO:現状このタイミングでStartDialog.show()を呼び出すとうメニュー画面でダイアログが出てしまう
        //TODO:個人的にはゲーム画面が表示されたloadedのタイミングでダイアログを表示し、先攻後攻を判断したい
        this.isPlayerTurn = StartDialog.show();
    }

    /**
     * 
     */
    public void init() {

        // 盤上の駒を一旦全て削除
        // TODO:initializeが呼ばれた瞬間のchildren[0]が何者なのかわかっていないが、このif文で保持してグリッドの境界線を保持している
        Node firstChild = null;
        int size = this.board.getChildren().size();
        if (size >= 1) {
            firstChild = this.board.getChildren().get(0);
            this.board.getChildren().clear();
            this.board.getChildren().add(firstChild);
        }

        // 相手の駒を生成し盤上に追加
        addInitializedPiece(Duck.class, 1, 1, true);
        addInitializedPiece(Giraffe.class, 0, 0, true);
        addInitializedPiece(Elephant.class, 2, 0, true);
        addInitializedPiece(Lion.class, 1, 0, true);

        // 自分の駒を生成し盤上に追加
        addInitializedPiece(Duck.class, 1, 2, false);
        addInitializedPiece(Giraffe.class, 2, 3, false);
        addInitializedPiece(Elephant.class, 0, 3, false);
        addInitializedPiece(Lion.class, 1, 3, false);

        // 互いの取得駒のラベルを初期化
        this.tookDuckCount = 0;
        this.tookDucks.setText("Duck * " + this.takenDuckCount);
        this.tookGiraffeCount = 0;
        this.tookGiraffes.setText("Giraffe * " + this.tookGiraffeCount);
        this.tookElephantCount = 0;
        this.tookElephants.setText("Elephant * " + this.tookElephantCount);
        this.takenDuckCount = 0;
        this.takenDucks.setText("Duck * " + this.takenDuckCount);
        this.takenGiraffeCount = 0;
        this.takenGiraffes.setText("Giraffe * " + this.tookGiraffeCount);
        this.takenElephantCount = 0;
        this.takenElephants.setText("Elephant * " + this.tookElephantCount);

    }

    /**
     * 
     * @param pieceType
     * @param xPosition
     * @param yPosition
     * @param isEnemy
     */
    private void addInitializedPiece(Class<?> pieceType, int xPosition, int yPosition, boolean isEnemy) {
        try {
            Constructor<?> constructor = pieceType.getConstructor(int.class, int.class, boolean.class);
            Piece piece = (Piece) constructor.newInstance(xPosition, yPosition, isEnemy);
            piece.setOnMouseClicked(e -> clickedPiece(e));
            this.board.getChildren().add(piece);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param event
     */
    private void clickedPiece(MouseEvent event) {
        System.out.print("clicked " );
        Piece selectedPiece = (Piece) event.getPickResult().getIntersectedNode();
        System.out.println(selectedPiece);
        
        if (selectedPiece.isEnemy() != isPlayerTurn) return;
        
        if (selectedPiece.hasSelected()) {
            selectedPiece.setHasSelected(false);
            board.getChildren().removeIf(child -> Pane.class.equals(child.getClass()));
            hasSelectedPiece = false;
        } else {
            selectedPiece.setHasSelected(true);
            showRangePane(selectedPiece);
            hasSelectedPiece = true;
        }
        
        this.isPlayerTurn = !this.isPlayerTurn;
    }
    
    private void showRangePane(Piece selectedPiece) {
        List<Point> points = selectedPiece.getCanMovePoints();
        System.out.println(points);
        for(Point point : points) {
            Piece inRangePiece = getPiece(point.x, point.y);
            if (inRangePiece == null) {
                addRangePane(point.x, point.y);
            } else {
                if (selectedPiece.isEnemy(inRangePiece)) {
                    addRangePane(inRangePiece.getColumnIndex(), inRangePiece.getRowIndex());
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
        for (Node piece : board.getChildren()) {
            if (!Piece.class.equals(piece.getClass().getSuperclass())) continue;
            if (columnIndex == GridPane.getColumnIndex(piece)
                    && rowIndex == GridPane.getRowIndex(piece)) {
                return (Piece)piece;
            }
        }
        return null;
    }
    
    private void addRangePane(int columnIndex, int rowIndex) {
        Pane p = new Pane();
        p.widthProperty().add(64);
        p.heightProperty().add(64);
        p.setOpacity(0.5);
        p.setStyle("-fx-background-color:#FFFF99;");
        p.setOnMouseClicked(e -> clickedRangePane(e));
        board.add(p, columnIndex, rowIndex);
    }
    
    public void clickedRangePane(MouseEvent event) {
        System.out.println("clickedRangePane " + event);
        
        // 移動対象の駒を取得する
        Piece selectedPiece = null;
        for(Node child : board.getChildren()) {
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
            board.getChildren().remove(clickedPiece);
            if (Lion.class.equals(clickedPiece.getClass())) {
                boolean isWinPlayer = clickedPiece.isEnemy();
                FinishedDialog.show(isWinPlayer);
                init();
                return;
            } else {
                upPieceCount(clickedPiece);
            }
        }
        
        // 選択されている駒を移動させる
        GridPane.setConstraints(selectedPiece, xPoint, yPoint);
        selectedPiece.move(xPoint, yPoint);
        selectedPiece.setHasSelected(false);
        board.getChildren().removeIf(child -> Pane.class.equals(child.getClass()));
        hasSelectedPiece = false;
    }
    
    /**
     * 
     * @param deadPiece
     */
    private void upPieceCount(Piece deadPiece) {
        String fieldPrefix;
        if (deadPiece.isEnemy()) {
            fieldPrefix = "took" + deadPiece.getClass().getSimpleName();
        } else {
            fieldPrefix = "taken" + deadPiece.getClass().getSimpleName();
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

    @FXML
    public void onTookDucksClicked(MouseEvent event) {

    }

    @FXML
    public void onTookGiraffesClicked(MouseEvent event) {

    }

    @FXML
    public void onTookElephantsClicked(MouseEvent event) {

    }

    @FXML
    public void onTookLionsClicked(MouseEvent event) {

    }

    @FXML
    public void onTakenDucksClicked(MouseEvent event) {

    }

    @FXML
    public void onTakenGiraffesClicked(MouseEvent event) {

    }

    @FXML
    public void onTakenElephantsClicked(MouseEvent event) {

    }

    @FXML
    public void onTakenLionsClicked(MouseEvent event) {

    }

    @FXML
    public void onPlayFirstClicked(MouseEvent event) {

    }

    @FXML
    public void onStartClicked(MouseEvent event) {
        init();
        this.isPlayerTurn = StartDialog.show();
    }

    @FXML
    public void onResetClicked(MouseEvent event) {
        init();
    }

    @FXML
    public void onBackClicked(MouseEvent event) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.changeScene("menu/MenuScene.fxml");
    }
}
