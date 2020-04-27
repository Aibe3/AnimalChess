/**
 * メニュー画面で発生するイベントを記述するファイル
 */

const onClickBattle = function(){
    const is1PlayerTurn = this.document.createElement("input");
    is1PlayerTurn.setAttribute("type", "hidden");
    is1PlayerTurn.setAttribute("name", "is1PlayerTurn");
    is1PlayerTurn.setAttribute("value", window.confirm("1P先攻で開始しますか？"));
    this.document.menuForm.appendChild(is1PlayerTurn);
    
    this.document.menuForm.action = "/battle";
    this.document.menuForm.submit();
}

const onConfigClicked = function(){
    alert("すまん、まだ実装できてないんや。黙って閉じてくれ。");
}
