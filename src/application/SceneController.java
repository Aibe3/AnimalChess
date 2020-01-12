package application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SceneController {

    private Stage stage;

    /**
     * {@link Main#start(Stage)}から呼び出されStageを格納する。
     * @param stage primary stage
     */
    public void setStage(Stage stage) {
        if(this.stage == null) {
            this.stage = stage;
        }else {
            new Exception("nullじゃない時に呼び出されたら設計思想と異なるのでエラー");
        }
    }

    private static SceneController instance;
    private SceneController() {}
    /**
     * Singletonパターン<br>
     * この設計でどのControllerからも簡単に画面の切り替えが行える
     * @return
     */
    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * 現在表示されている場面を切り替える
     * 
     * @param newScene 新しく表示される場面
     */
    public void changeScene(Scene newScene) {
        this.stage.setScene(newScene);
        this.stage.show();
    }

    /**
     * 指定されたパスでfxmlファイルを取得し、現在表示されている場面を切り替える
     * 
     * @param scenePath fxmlファイルのパス
     */
    public void changeScene(String scenePath) {
        URL location = getClass().getResource(scenePath);
        try {
            Scene scene;
            Object temp = FXMLLoader.load(location);
            Class rootClass = temp.getClass();
            if (AnchorPane.class.equals(rootClass)) {
                scene = new Scene((AnchorPane) temp);
            } else if (BorderPane.class.equals(rootClass)) {
                scene = new Scene((BorderPane) temp);
            } else {
                throw new Exception(rootClass.getName() + "は想定外のrootです");
            }
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            this.changeScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
