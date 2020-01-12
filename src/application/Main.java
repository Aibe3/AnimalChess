package application;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

            // scene管理クラスにStageを設定しておく
            SceneController sceneController = SceneController.getInstance();
            sceneController.setStage(primaryStage);

            // Menu画面の呼び出し
            sceneController.changeScene("menu/MenuScene.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
