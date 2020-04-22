package application.menu;

import java.net.URL;
import java.util.ResourceBundle;

import application.SceneController;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class MenuSceneController implements Initializable {
    
    @FXML
    private Button battleButton;
    
    @FXML
    private Button configButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }
    
    @FXML
    public void onBattleClicked(MouseEvent event) {
        System.out.println("battle clicked");
        
        SceneController sceneController = SceneController.getInstance();
        sceneController.changeScene("battle/BattleScene.fxml");
    }
    
    @FXML
    public void onConfigClicked(MouseEvent event) {
        System.out.println("config clicked");
        
        javafx.scene.control.Alert a = new Alert(javafx.scene.control.Alert.AlertType.WARNING);
        a.setContentText("すまん、まだ実装できてないんや。黙って閉じてくれ。");
        a.show();
    }
    
}
