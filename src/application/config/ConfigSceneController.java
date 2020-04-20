package application.config;

import application.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ConfigSceneController {
    
    @FXML
    private Button back;
    
    @FXML
    public void onBackClicked(MouseEvent event) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.changeScene("menu/MenuScene.fxml");
    }
}
