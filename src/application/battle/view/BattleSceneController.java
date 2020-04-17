package application.battle.view;

import java.awt.Point;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.battle.board.RangePane;
import application.battle.piece.PieceType;
import application.battle.presenter.BattlePresenter;
import application.battle.presenter.IBattlePresenter;
import application.dialog.BattleFinishedDialog;
import application.dialog.DecideFirstPlayerDialog;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class BattleSceneController implements IBattleViewController, Initializable {
    
    private IBattlePresenter presenter;
    
    private GridPane gridPane;
    private Label tookDucks;
    private Label tookGiraffes;
    private Label tookElepahnts;
    private Label takenDucks;
    private Label takenGiraffes;
    private Label takenElepahnts;
    
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
        EventHandler<MouseEvent> event = e -> {
            Pane clickedPane = (Pane) e.getSource();
            int xPoint = GridPane.getColumnIndex(clickedPane);
            int yPoint = GridPane.getRowIndex(clickedPane);
            this.presenter.clickedMoveRange(new Point(xPoint, yPoint));
        };
//        points.forEach(p -> this.gridPane.add(new RangePane(event), p.x, p.y));
        points.stream().map(p -> {
            RangePane range = new RangePane(event);
            GridPane.setColumnIndex(range, p.x);
            GridPane.setRowIndex(range, p.y);
            return range;
        }).forEach(r -> this.gridPane.getChildren().add(r));
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
        this.gridPane.getChildren().removeIf(c -> c.getClass().equals(RangePane.class));
    }
    
    @Override
    public void showPiece(PieceType type, Point showPoint, Boolean is1PlayersPiece) {
        String imageFile = this.imageFolder + type.toString() + ".jpeg";
        ImageView pieceImage = new ImageView(new Image(imageFile, 64, 64, true, true));
        if (!is1PlayersPiece) pieceImage.setRotate(180);
        GridPane.setConstraints(pieceImage, showPoint.x, showPoint.y);
        GridPane.setMargin(pieceImage, margin);
        this.gridPane.getChildren().add(pieceImage);
    }
    
    @Override
    public void hidePiece(Point point) {
        this.gridPane.getChildren()
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
}
