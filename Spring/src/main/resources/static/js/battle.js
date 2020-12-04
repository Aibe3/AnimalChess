/**
 * 
 */

$(function(){
    
    const clickedPieceCount = function(clickedTag, is1PlayerStorePiece){
        addHidden("is1PlayerStorePiece", is1PlayerStorePiece);
        const setRequestType = function(typeName){
            if(clickedTag.innerText.startsWith(typeName)) addHidden("storePieceType", typeName);
        };
        setRequestType("Giraffe");
        setRequestType("Elephant");
        setRequestType("Duck");
        
        submit("/battle/clickedTookPiece");
    };
    
    $(".pieceCountOf1P > p").click(function(){
        clickedPieceCount(this, true);
    });
    
    $(".pieceCountOf2P > p").click(function(){
        clickedPieceCount(this, false);
    });
});

const addHidden = function(name, value){
    const param = document.createElement("input");
    param.setAttribute("type", "hidden");
    param.setAttribute("name", name);
    param.setAttribute("value", value);
    document.battleForm.appendChild(param);
};

const submit = function(action){
    document.battleForm.action = action;
    document.battleForm.submit();
};

const putPieceImage = function(type, point){
    const img = $("<img>", {
        alt: "",
        src: "../pieceImage/" + type + ".jpeg",
        on: {
            click: function(event){
                addHidden("clickedPoint", point);
                submit("/battle/clickedPiece");
            }
        }
    });
    $("#" + point).append(img);
};

const putCanMovePoint = function(point){
    const div = $("<div></div>", {
        "class": "canMovePoint",
        on: {
            click: function(event){
                addHidden("clickedPoint", point);
                submit("/battle/clickedCanMovePoint");
            }
        }
    });
    $("#" + point).append(div);
};

const init = function(){
    const is1PlayerTurn = this.document.getElementsByName("is1PlayerTurn")[0];
    is1PlayerTurn.value = window.confirm("1P先攻で開始しますか？");
    submit("/battle/init");
};

const back = function(){
    document.battleForm.method = "get";
    submit("/menu");
};

const gameSet = function(winer){
    if(winer != null){
        alert(winer + "の勝ちです。");
        init();
    }
};
