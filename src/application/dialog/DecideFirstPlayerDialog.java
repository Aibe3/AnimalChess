package application.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class DecideFirstPlayerDialog {
    
    private static DialogCore core = new DialogCore();
    
    private static String message = "1P先攻で開始しますか？";
    
    /**
     * 
     * @return true:1P先行, false:2P先行
     */
    public static boolean show() {
        Alert dialog = new Alert(
                AlertType.INFORMATION,
                message,
                new ButtonType("NO", ButtonData.CANCEL_CLOSE),
                new ButtonType("OK", ButtonData.YES)
        );
        
        ButtonType result = core.show(dialog);
        if (result.getButtonData() == ButtonData.YES) {
            return true;
        } else {
            return false;
        }
    }
}
