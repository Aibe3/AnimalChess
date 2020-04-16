package application.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class FinishedDialog {

    private static DialogCore core = new DialogCore();

    private static String player = "貴方の";
    private static String enemy = "敵の";
    private static String win ="負けです";
    
    /**
     * 勝敗を表示するダイアログを表示する。
     * @param isWinPlayer プレイヤーが勝ったか否か
     */
    public static void show(boolean isWinPlayer) {
        String showMessage;
        if (isWinPlayer) showMessage = player + win;
        else showMessage = enemy + win;
        Alert dialog = new Alert(AlertType.INFORMATION,
                showMessage,
                new ButtonType("OK", ButtonData.OK_DONE)
        );
        core.show(dialog);
    }
}
