package application.battle.board;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RangePane extends Pane {

    public RangePane(EventHandler<MouseEvent> mouseClickedEvent) {
        this.widthProperty().add(64);
        this.heightProperty().add(64);
        this.setStyle("-fx-background-color:#FFFF99; -fx-opacity:0.5;");
        this.setOnMouseClicked(event -> mouseClickedEvent.handle(event));
    }
}
