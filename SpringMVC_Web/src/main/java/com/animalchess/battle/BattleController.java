package com.animalchess.battle;

import java.awt.Point;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.animalchess.battle.piece.PieceType;
import com.animalchess.battle.view.BattleViewController;

@Controller
public class BattleController {
    
    @Autowired
    private BattleViewController viewController;
    
    @GetMapping("/battle")
    public String battleGet(Model model, @RequestParam("is1PlayerTurn")boolean is1PlayerTurn){
        model.addAttribute("battleForm", this.viewController.init(is1PlayerTurn));
        return "battle";
    }
    
    @PostMapping("/battle/init")
    public String init(Model model, BattleForm form, HttpServletRequest request){
        model.addAttribute("battleForm", this.viewController.init(form.getIs1PlayerTurn()));
        return "battle";
    }
    
    @PostMapping("/battle/clickedPiece")
    public String clickedPiece(Model model, BattleForm form, HttpServletRequest request) {
        Point clickedPoint = parsePoint(form.getClickedPoint());
        model.addAttribute("battleForm", this.viewController.clickedPiece(clickedPoint));
        return "battle";
    }
    
    @PostMapping("/battle/clickedCanMovePoint")
    public String clickedCanMovePoint(Model model, BattleForm form, HttpServletRequest request) {
        Point movePoint = parsePoint(form.getClickedPoint());
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
        PieceType type = Enum.valueOf(PieceType.class, form.getStorePieceType());
        model.addAttribute("battleForm", this.viewController.clickedStorePiece(type, form.getIs1PlayerStorePiece()));
        return "battle";
    }
}
