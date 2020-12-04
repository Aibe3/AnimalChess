package application.battle.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CanMovePane extends Pane {
    
    public CanMovePane(EventHandler<MouseEvent> mouseClickedEvent) {
        this.widthProperty().add(64);
        this.heightProperty().add(64);
        this.setStyle("-fx-background-color:#FFFF99; -fx-opacity:0.5;");
        this.setOnMouseClicked(event -> mouseClickedEvent.handle(event));
    }
}
