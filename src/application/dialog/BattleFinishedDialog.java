package application.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public class BattleFinishedDialog {

    private static DialogCore core = new DialogCore();

    private static String firstPlayer = "1Pの";
    private static String secondPlayer = "2Pの";
    private static String win ="勝ちです";
    
    /**
     * 勝敗を表示するダイアログを表示する。
     * @param isWinPlayer プレイヤーが勝ったか否か
     */
    public static void show(boolean isWinPlayer) {
        String showMessage;
        if (isWinPlayer) showMessage = firstPlayer + win;
        else showMessage = secondPlayer + win;
        Alert dialog = new Alert(AlertType.INFORMATION,
                showMessage,
                new ButtonType("OK", ButtonData.OK_DONE)
        );
        core.show(dialog);
    }
}
