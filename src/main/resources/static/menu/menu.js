/**
 * メニュー画面で発生するイベントを記述するファイル
 */

const onClickBattle = function(){
    this.document.menuForm.method = "get";
    this.document.menuForm.action = "/battle";
    this.document.menuForm.submit();
}

const onConfigClicked = function(){
    alert("すまん、まだ実装できてないんや。黙って閉じてくれ。");
}
