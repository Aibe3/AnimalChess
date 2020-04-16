package application.battle.view;

import java.awt.Point;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.battle.piece.PieceType;
import application.battle.presenter.BattlePresenter;
import application.battle.presenter.IBattlePresenter;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class BattleSceneController implements IBattleViewController, Initializable {

    private IBattlePresenter presenter;

    private GridPane gridPane;
    private Label tookDucks;
    private Label tookGiraffes  ;
    private Label tookElepahnts ;
    private Label takenDucks    ;
    private Label takenGiraffes ;
    private Label takenElepahnts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        presenter = new BattlePresenter(this);
        
    }

    @Override
    public void showMoveArea(List<Point> points) {
        // TODO Auto-generated method stub

    }

    @Override
    public void hideMoveArea(List<Point> points) {
        // TODO Auto-generated method stub

    }

    @Override
    public void movePiece(Point currentPoint, Point movePoint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void popPiece(PieceType type, Boolean is1PlayersPiece, Point putPoint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pushPiece(PieceType type, Boolean is1PlayersPiece) {
        // TODO Auto-generated method stub

    }

    @Override
    public void gameSet(Boolean isWin1Player) {
        // TODO Auto-generated method stub

    }

}
