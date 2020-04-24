package com.animalchess.battle;

import java.awt.Point;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.animalchess.battle.piece.PieceType;
import com.animalchess.battle.view.BattleViewController;

@Controller
public class BattleController {
    
    @Autowired
    private BattleViewController viewController;
    
    @GetMapping("/battle")
    public String battleGet(Model model){
        return "battle";
    }
    
    @PostMapping("/battle")
    public String battlePost(Model model){
        return "battle";
    }
    
    @PostMapping("/battle/init")
    public String init(Model model, BattleForm form, HttpServletRequest request){
        boolean is1PlayerTurn = (boolean)request.getAttribute("is1PlayerTurn");
        model.addAttribute("battleForm", this.viewController.init(is1PlayerTurn));
        return "battle";
    }
    
    @PostMapping("/battle/clickedPiece")
    public String clickedPiece(Model model, BattleForm form, HttpServletRequest request) {
        Point clickedPoint = parsePoint(request.getAttribute("clickedPoint"));
        model.addAttribute("battleForm", this.viewController.clickedPiece(clickedPoint));
        return "battle";
    }
    
    @PostMapping("/battle/clickedCanMovePoint")
    public String clickedCanMovePoint(Model model, BattleForm form, HttpServletRequest request) {
        Point movePoint = parsePoint(request.getAttribute("canMovePoint"));
        model.addAttribute("battleForm", this.viewController.clickedMoveRange(movePoint));
        return "battle";
    }
    
    /**
     * ${x}-${y}の形式で表される盤面上の位置を解析し{@link Point}のインスタンスに変換する。<br>
     * 形式が異なると例外が発生するが、使用目的と異なる為対処はしない。<br>
     * @param pointOfRequestParam ${x}-${y}の形式の文字列
     * @return 解析結果から生成された{@link Point}インスタンス
     */
    private Point parsePoint(Object pointOfRequestParam) {
        String[] indexs = String.valueOf(pointOfRequestParam).split("-");
        return new Point(Integer.valueOf(indexs[0]), Integer.valueOf(indexs[1]));
    }
    
    @PostMapping("/battle/clickedTookPiece")
    public String clickedTookPiece(Model model, BattleForm form, HttpServletRequest request) {
        boolean is1PlayersPiece = (boolean)request.getAttribute("is1PlayersPiece");
        String typeName = String.valueOf(request.getAttribute("clickedType"));
        PieceType type = Enum.valueOf(PieceType.class, typeName);
        model.addAttribute("battleForm", this.viewController.clickedStorePiece(type, is1PlayersPiece));
        return "battle";
    }
}
