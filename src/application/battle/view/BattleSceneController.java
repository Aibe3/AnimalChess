package application.battle.view;

import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.SceneController;
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
    private Label tookDucksOf1P;
    @FXML
    private Label tookElephantsOf1P;
    @FXML
    private Label tookGiraffesOf1P;
    @FXML
    private Label tookDucksOf2P;
    @FXML
    private Label tookElephantsOf2P;
    @FXML
    private Label tookGiraffesOf2P;
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
        this.presenter = new BattlePresenter(this);
        this.presenter.init(DecideFirstPlayerDialog.show());
    }
    
    @Override
    public void showMoveArea(List<Point> points) {
        points.stream().map(point -> {
            CanMovePane canMovePane = new CanMovePane(event -> {
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
    
    @Override
    public void hideMoveArea() {
        this.board.getChildren().removeIf(c -> c.getClass().equals(CanMovePane.class));
    }
    
    @Override
    public void showPiece(PieceType type, Point showPoint, boolean is1PlayersPiece) {
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
    public void hidePiece() {
        this.board.getChildren().removeIf(c -> c.getClass().equals(ImageView.class));
    }
    
    @Override
    public void updateTakePiece(PieceType type, boolean is1PlayerPiece, int count) {
        String fieldLabelName = "took" + type.toString() + "sOf";
        if (is1PlayerPiece) fieldLabelName = fieldLabelName + "1P";
        else fieldLabelName = fieldLabelName + "2P";
        
        try {
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
    public void gameSet(boolean isWin1Player) {
        BattleFinishedDialog.show(isWin1Player);
        presenter.init(DecideFirstPlayerDialog.show());
    }
    
    @FXML
    public void onTookDucksOf1PClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Duck, true);
    }
    
    @FXML
    public void onTookElephantsOf1PClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Elephant, true);
    }
    
    @FXML
    public void onTookGiraffesOf1PClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Giraffe, true);
    }
    
    @FXML
    public void onTookDucksOf2PClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Duck, false);
    }
    
    @FXML
    public void onTookElephantsOf2PClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Elephant, false);
    }
    
    @FXML
    public void onTookGiraffesOf2PClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Giraffe, false);
    }
    
    @FXML
    public void onPlayFirstClicked(MouseEvent event) {
        this.presenter.clickedStorePiece(PieceType.Duck, true);
    }
    
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
