package com.animalchess.battle.model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import com.animalchess.battle.history.History;
import com.animalchess.battle.model.BattleModel;
import com.animalchess.battle.piece.Duck;
import com.animalchess.battle.piece.Elephant;
import com.animalchess.battle.piece.Giraffe;
import com.animalchess.battle.piece.Lion;
import com.animalchess.battle.piece.Piece;
import com.animalchess.battle.piece.PieceType;

class BattleModelTest {
    
    private BattleModel model = new BattleModel();
    
    @Test
    void testInit() {
        Consumer<Boolean> testHistoryProperty = is1PlayerTurn -> {
            History initHistry = this.model.init(is1PlayerTurn);
            assertTrue(initHistry.is1PlayerTurn() == is1PlayerTurn);
            assertTrue(isSameBoard(initHistry.getPieceOnBoard(), createInitBoard()));
            assertTrue(isSamePieceCount(initHistry.getPieceCountOf1P(), createInitPieceCount()));
            assertTrue(isSamePieceCount(initHistry.getPieceCountOf2P(), createInitPieceCount()));
        };
        testHistoryProperty.accept(true);
        testHistoryProperty.accept(false);
    }
    
    private Map<Point, Piece> createInitBoard() {
        Map<Point, Piece> answer = new HashMap();
        answer.put(new Point(0, 0), new Giraffe(false));
        answer.put(new Point(1, 0), new Lion(false));
        answer.put(new Point(2, 0), new Elephant(false));
        answer.put(new Point(1, 1), new Duck(false));
        answer.put(new Point(1, 2), new Duck(true));
        answer.put(new Point(0, 3), new Elephant(true));
        answer.put(new Point(1, 3), new Lion(true));
        answer.put(new Point(2, 3), new Giraffe(true));
        return answer;
    }
    
    private Map<PieceType, Integer> createInitPieceCount() {
        Map<PieceType, Integer> answer = new HashMap();
        answer.put(PieceType.Duck, 0);
        answer.put(PieceType.Elephant, 0);
        answer.put(PieceType.Giraffe, 0);
        return answer;
    }
    
    private boolean isSameBoard(Map<Point, Piece> target, Map<Point, Piece> answer) {
        BiFunction<Entry<Point, Piece>, Entry<Point, Piece>, Boolean> isSameSituation = (e1, e2) -> {
            if(!isSamePoint(e1.getKey(), e2.getKey())) return false;
            if(!isSamePiece(e1.getValue(), e2.getValue())) return false;
            return true;
        };
        return target.entrySet().stream().allMatch(e1 -> {
            return answer.entrySet().stream().anyMatch(e2 -> isSameSituation.apply(e1, e2));
        });
    }
    
    private boolean isSamePoint(Point p1, Point p2) {
        if(p1.x != p2.x) return false;
        if(p1.y != p2.y) return false;
        return true;
    }
    
    private boolean isSamePiece(Piece p1, Piece p2) {
        if(p1.toPieceType() != p2.toPieceType()) return false;
        if(p1.is1PlayersPiece() ^ p2.is1PlayersPiece()) return false;
        return true;
    }
    
    private boolean isSamePieceCount(Map<PieceType, Integer> target, Map<PieceType, Integer> answer) {
        return target.keySet().stream().allMatch(key -> target.get(key) == answer.get(key));
    }
    
    @Test
    void testGetCanMovePoint() {
        
        List<Point> canMovePoints = null;
        List<Point> answer = new ArrayList<>();
        
        this.model.init(true);
        canMovePoints = this.model.getCanMovePoint(new Point(1, 2));
        answer.add(new Point(1, 1));
        assertTrue(isSamePoints(canMovePoints, answer));
        
        this.model.init(true);
        canMovePoints = this.model.getCanMovePoint(new Point(0, 3));
        answer.clear();
        answer.add(new Point(0, 2));
        assertTrue(isSamePoints(canMovePoints, answer));
        
        this.model.init(true);
        canMovePoints = this.model.getCanMovePoint(new Point(2, 3));
        answer.clear();
        assertTrue(isSamePoints(canMovePoints, answer));
        
        this.model.init(true);
        canMovePoints = this.model.getCanMovePoint(new Point(1, 3));
        answer.clear();
        answer.add(new Point(0, 2));
        answer.add(new Point(2, 2));
        assertTrue(isSamePoints(canMovePoints, answer));
        
        /*
         * 一旦Model.init(true)直後にそれぞれの味方駒を押下した試験を書いたけど
         * 　・初期化せずにそのまま続けてメソッド呼び出し
         * 　・先行が2Pでの呼び出し
         * 　・互いに数手実施後からの呼び出し
         * 　　↑先行後攻、互いの動かせる手のパターンの掛け算
         * とか考慮パターンが多すぎて書ききれない。
         * 頑張ってそれぞれプレイヤーの手をロジックで表す？それはミイラとりがミイラになってんな。
         * 関数型言語の記事で言われてる通り、確かに状態が複雑だとテストが書けないな。。。
         * 
         * １社目の案件の単体試験で１メソッドの試験をデータパターン総当たりで行おうとして
         * 160パターンくらいの状態の組み合わせ表をエクセルで作ってその表データをエディタで
         * javaの２次元配列になるように修正しeclipseのテストクラスに試験パターンとしては
         * っつけるというアホみたいな事した経験あるんだけど当時は高稼動の日々に擦り切れそう
         * になっていたはずだけどそれにしてもその手段は脳筋どころじゃなく骨髄まで筋繊維でで
         * きてますって感じだ。
         * 
         * パターンが書ききれないので関数型言語をやってみたい旨を書きたかったのだが、思った
         * ことを書いているせいか酔っているせいかツイッターみたいになってしまった。
         */
    }
    
    private boolean isSamePoints(List<Point> target, List<Point> answer) {
        return target.stream().allMatch(p1 -> answer.stream().anyMatch(p2 -> isSamePoint(p1, p2)));
    }
    
    @Test
    void testGetCanPopPoint() {
        
        List<Point> canPopPoints = null;
        List<Point> answer = new ArrayList<>();
        
        moveForwardGame();
        
        //1Pの家鴨を出す
        canPopPoints = this.model.getCanPopPoint(PieceType.Duck, true);
        //そもそも将棋のルールすら詳しく知らないけど歩or家鴨が持ち駒から1段目に出たらどうなんの
        answer.add(new Point(0, 0));
        answer.add(new Point(2, 0));
        answer.add(new Point(0, 1));
        answer.add(new Point(0, 2));
        answer.add(new Point(1, 2));
        answer.add(new Point(2, 2));
        assertTrue(isSamePoints(canPopPoints, answer));
    }
    
    /**
     * 三手ゲームを進める
     */
    private void moveForwardGame() {
        //2P初手で初期化
        this.model.init(false);
        
        //2Pの象を前へ
        this.model.getCanMovePoint(new Point(2, 0));
        this.model.movePiece(new Point(2, 1));
        
        //1Pの家鴨を前へ、2Pの家鴨を奪取
        this.model.getCanMovePoint(new Point(1, 2));
        this.model.movePiece(new Point(1, 1));
        
        //2Pの麒麟を(駒から見て)左前へ、1Pの家鴨を奪取
        this.model.getCanMovePoint(new Point(0, 0));
        this.model.movePiece(new Point(1, 1));
    }
    
    @Test
    void testMovePiece() {
        moveForwardGame();
        //1Pの獅子を(駒から見て)前へ
        this.model.getCanMovePoint(new Point(1, 3));
        History target = this.model.movePiece(new Point(1, 2));
        
        Map<Point, Piece> answer = new HashMap<>();
        answer.put(new Point(1, 0), new Lion(false));
        answer.put(new Point(1, 1), new Giraffe(false));
        answer.put(new Point(2, 1), new Elephant(false));
        answer.put(new Point(1, 2), new Lion(true));
        answer.put(new Point(0, 3), new Elephant(true));
        answer.put(new Point(2, 3), new Giraffe(true));
        
        assertTrue(isSameBoard(target.getPieceOnBoard(), answer));
    }
}
