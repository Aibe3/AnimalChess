/**
 * 
 */

const onLoad = function(){
    const is1PlayerTurn = this.document.getElementsByName("is1PlayerTurn")[0];
    if(is1PlayerTurn.value == "") {
        is1PlayerTurn.value = window.confirm("1P先攻で開始しますか？");
        this.document.battleForm.action = "/battle/init";
        this.document.battleForm.submit();
    }
};

$(function(){
//    $("body").on("load", function(){
//        const is1PlayerTurn = this.document.getElementsByName("is1PlayerTurn")[0];
//        if(is1PlayerTurn.value == "") {
//            is1PlayerTurn.value = window.confirm("1P先攻で開始しますか？");
//            this.document.battleForm.action = "/battle/init";
//            this.document.battleForm.submit();
//        }
//    });
//    
    const addHidden = function(name, value){
        const param = document.createElement("input");
        param.setAttribute("type", "hidden");
        param.setAttribute("name", name);
        param.setAttribute("value", value);
        document.battleForm.appendChild(param);
    };
    
    $("img").click(function(){
        addHidden("clickedPoint", this.parent("td").id);
        this.document.battleForm.action = "/battle/clickedPiece";
        this.document.battleForm.submit();
    });
    
    $("div .canMovePoint").click(function(){
        addHidden("canMovePoint", this.parent("td").id);
        this.document.battleForm.action = "/battle/clickedCanMovePoint";
        this.document.battleForm.submit();
    });
    
    const clickedPieceCount = function(is1PlayersPiece){
        addHidden("is1PlayersPiece", is1PlayersPiece);
        const setRequestType = function(typeName){
            if(this.innerText.startWith(typeName)) addHidden("clickedType", typeName);
        };
        setRequestType("Giraffe");
        setRequestType("Elephant");
        setRequestType("Duck");
        
        this.document.battleForm.action = "/battle/clickedTookPiece";
        this.document.battleForm.submit();
    };
    
    $(".pieceCountOf1P > p").click(function(){
        clickedPieceCount(true);
    });
    
    $(".pieceCountOf2P > p").click(function(){
        clickedPieceCount(false);
    });
});
