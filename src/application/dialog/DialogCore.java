package application.dialog;

import java.util.Optional;

import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;

/**
 * 
 */
class DialogCore {
    
    public DialogCore() {
    }
    
    /**
     * ダイアログを受け取り表示して値を返すクラス。<br>
     * 今後{@link Dialog#showAndWait()}の後に、結果に対して行う事を引数でもらったりするのも面白いなと思っている。
     * 
     * @param dialog
     * @return
     */
    public ButtonType show(Dialog dialog) {
        // Optionalが使ってみたくてこの実装にしてしまったが。。。
        // 本来この状態でnullが帰ることはなさそう。。。
        // Dialog.showAndWait()の後にDaialog.getResult()で全然大丈夫だぁ。。。
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }
}
