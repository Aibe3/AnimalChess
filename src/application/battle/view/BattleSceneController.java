package application.battle.view;

import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.SceneController;
import application.battle.board.RangePane;
import application.battle.piece.PieceType;
import application.battle.presenter.BattlePresenter;
import application.battle.presenter.IBattlePresenter;
import application.dialog.BattleFinishedDialog;
import application.dialog.DecideFirstPlayerDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BattleSceneController implements IBattleViewController, Initializable {
    
    private IBattlePresenter presenter;
    
    @FXML
    private GridPane board;
    @FXML
    private Label tookDucks;
    @FXML
    private Label tookGiraffes;
    @FXML
    private Label tookElepahnts;
    @FXML
    private Label takenDucks;
    @FXML
    private Label takenGiraffes;
    @FXML
    private Label takenElepahnts;
//  @FXML
//  private Button start;
    @FXML
    private Button reset;
    @FXML
    private Button back;
    
    // 駒のイメージファイルを格納したフォルダパス
    protected final String imageFolder = "application/battle/piece/image/";
    
    // 全ての駒に共通するImageViewとGridPaneとのマージン
    private final Insets margin = new Insets(5, 0, 0, 12);
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        presenter = new BattlePresenter(this);
    }
    
    @Override
    public void showMoveArea(List<Point> points) {
//        points.forEach(p -> this.gridPane.add(new RangePane(event), p.x, p.y));
        points.stream().map(point -> {
            RangePane canMovePane = new RangePane(event -> {
                Node clickedPane = (Node) event.getSource();
                int x = GridPane.getColumnIndex(clickedPane);
                int y = GridPane.getRowIndex(clickedPane);
                this.presenter.clickedMoveRange(new Point(x, y));
            });
            GridPane.setColumnIndex(canMovePane, point.x);
            GridPane.setRowIndex(canMovePane, point.y);
            return canMovePane;
        }).forEach(canMovePane -> this.board.getChildren().add(canMovePane));
    }
    
//    /**
//     * RangePane(駒の移動先のパネル)が押下された際の動作。
//     * RangePneを全て除去し、選択中の駒(取った駒のラベルクリックも含む)をクリックしたパネルの位置に移動させる。
//     * @param event
//     */
//    public void clickedMoveArea(MouseEvent event) {
//        Pane clickedPane = (Pane)event.getSource();
//        int xPoint = GridPane.getColumnIndex(clickedPane);
//        int yPoint = GridPane.getRowIndex(clickedPane);
//        this.presenter.clickedMoveRange(new Point(xPoint, yPoint));
//    }
    
    @Override
    public void hideMoveArea() {
        this.board.getChildren().removeIf(c -> c.getClass().equals(RangePane.class));
    }
    
    @Override
    public void showPiece(PieceType type, Point showPoint, Boolean is1PlayersPiece) {
        String imageFile = this.imageFolder + type.toString() + ".jpeg";
        ImageView pieceImage = new ImageView(new Image(imageFile, 64, 64, true, true));
        pieceImage.setOnMouseClicked(e -> {
            Node clickedImage = (Node) e.getSource();
            int x = GridPane.getColumnIndex(clickedImage);
            int y = GridPane.getRowIndex(clickedImage);
            this.presenter.clickedPiece(new Point(x, y));
        });
        if (!is1PlayersPiece) pieceImage.setRotate(180);
        GridPane.setConstraints(pieceImage, showPoint.x, showPoint.y);
        GridPane.setMargin(pieceImage, margin);
        this.board.getChildren().add(pieceImage);
    }
    
    @Override
    public void hidePiece(Point point) {
        this.board.getChildren()
            .filtered(c -> point.x == GridPane.getColumnIndex(c) && point.y == GridPane.getRowIndex(c))
            .removeIf(c -> c.getClass().equals(ImageView.class));
    }
    
    @Override
    public void updatePieceCountLabel(PieceType type, Boolean is1PlayerPiece, int count) {
        // 持ち駒のラベルを更新
        String fieldLabelName = type.toString() + "s";
        if (is1PlayerPiece) {
            fieldLabelName = "took" + fieldLabelName;
        } else {
            fieldLabelName = "taken" + fieldLabelName;
        }
        
        try {
            // ラベルのテキストの更新
            Field countLabel = this.getClass().getDeclaredField(fieldLabelName);
            Method setText = Label.class.getMethod("setText", String.class);
            String newText = type.toString() + " * " + count;
            setText.invoke(countLabel.get(this), newText);
        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はコードがバグっているのでStackTrace出力後に握りつぶす
        }
    }
    
    @Override
    public void gameSet(Boolean isWin1Player) {
        BattleFinishedDialog.show(isWin1Player);
        presenter.init(DecideFirstPlayerDialog.show());
    }
    
    @FXML
    public void onTookDucksClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Duck, true);
    }
    
    @FXML
    public void onTookGiraffesClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Giraffe, true);
    }
    
    @FXML
    public void onTookElephantsClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Elephant, true);
    }
    
    @FXML
    public void onTakenDucksClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Duck, false);
    }
    
    @FXML
    public void onTakenGiraffesClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Giraffe, false);
    }
    
    @FXML
    public void onTakenElephantsClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Elephant, false);
    }
    
    @FXML
    public void onPlayFirstClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Duck, true);
    }
    
//    @FXML
//    public void onStartClicked(MouseEvent event) {
//        //TODO:今のところこのボタンResetと変わらないからあんまり意味ない
//        init();
//        this.is1playerTurn = DecideFirstPlayerDialog.show();
//    }
//
    @FXML
    public void onResetClicked(MouseEvent event) {
        this.presenter.init(DecideFirstPlayerDialog.show());
    }
    
    @FXML
    public void onBackClicked(MouseEvent event) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.changeScene("menu/MenuScene.fxml");
    }
}
