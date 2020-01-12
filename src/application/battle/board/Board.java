package application.battle.board;

import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Board {

    //
    private GridPane board;

    public Board(GridPane sceneBoard) {
        this.board = sceneBoard;
        init();
    }

    /**
     * 
     */
    public void init() {

        // 相手の駒を生成し盤上に追加
        Duck enemyDuck = new Duck(1, 1, true);
        this.board.getChildren().add(enemyDuck);
        Giraffe enemyGiraffe = new Giraffe(0, 0, true);
        this.board.getChildren().add(enemyGiraffe);
        Elephant enemyElephant = new Elephant(2, 0, true);
        this.board.getChildren().add(enemyElephant);
        Lion enemyLion = new Lion(1, 0, true);
        this.board.getChildren().add(enemyLion);

        // 自分の駒を生成し盤上に追加
        Duck myDuck = new Duck(1, 2, false);
        this.board.getChildren().add(myDuck);
        Giraffe myGiraffe = new Giraffe(2, 3, true);
        this.board.getChildren().add(myGiraffe);
        Elephant myElephant = new Elephant(0, 3, true);
        this.board.getChildren().add(myElephant);
        Lion myLion = new Lion(1, 3, true);
        this.board.getChildren().add(myLion);
    }

}
