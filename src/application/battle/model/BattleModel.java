package application.battle.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import application.battle.history.History;
import application.battle.piece.Lion;
import application.battle.piece.Piece;
import application.battle.piece.PieceType;

public class BattleModel implements IBattleModel {
    
    private final int columnSize = 3;
    private final int rowSize = 4;
    
    private Map<Point, Piece> pieceOnBoard;
    
    private Map<PieceType, Integer> pieceCountOf1P = new HashMap<>();
    private Map<PieceType, Integer> pieceCountOf2P = new HashMap<>();
    
    private Piece selectedPiece = null;
    
    /**
     * 駒が選択されていない場合は最後の{@link java.util.Optional.get()}で例外が発生するが<br>
     * それはメソッドの設計方針と異なるので呼び出し側の実装誤りとし、対処しない。
     * 
     * @return 選択された駒の位置
     */
    private Point selectedPiecePoint() {
        return this.pieceOnBoard.entrySet().stream()
                .filter(entry -> entry.getValue().equals(selectedPiece))
                .map(entry -> entry.getKey()).findFirst().get();
    }
    
    private boolean is1PlayerTurn;
    
    @Override
    public History init(boolean is1PlayerTurn) {
        this.is1PlayerTurn = is1PlayerTurn;
        InitPiceCount();
        InitPieceOnBoard();
        return createHistory();
    }
    
    private void InitPiceCount() {
        Consumer<Map<PieceType, Integer>> putEntryExpression = m -> {
            m.put(PieceType.Duck, 0);
            m.put(PieceType.Elephant, 0);
            m.put(PieceType.Giraffe, 0);
        };
        putEntryExpression.accept(this.pieceCountOf1P);
        putEntryExpression.accept(this.pieceCountOf2P);
    }
    
    private void InitPieceOnBoard() {
        this.pieceOnBoard = new HashMap<>();
        // 1P
        addInitializedPiece(PieceType.Duck, new Point(1, 2), true);
        addInitializedPiece(PieceType.Elephant, new Point(0, 3), true);
        addInitializedPiece(PieceType.Giraffe, new Point(2, 3), true);
        addInitializedPiece(PieceType.Lion, new Point(1, 3), true);
        
        // 2P
        addInitializedPiece(PieceType.Duck, new Point(1, 1), false);
        addInitializedPiece(PieceType.Elephant, new Point(2, 0), false);
        addInitializedPiece(PieceType.Giraffe, new Point(0, 0), false);
        addInitializedPiece(PieceType.Lion, new Point(1, 0), false);
    }
    
    private void addInitializedPiece(PieceType type, Point point, boolean is1PlayerPiece) {
        Piece piece = type.toPiece(is1PlayerPiece);
        this.pieceOnBoard.put(point, piece);
    }
    
    private History createHistory() {
        History now = new History();
        now.setIs1PlayerTurn(this.is1PlayerTurn);
        now.setPieceCountOf1P(this.pieceCountOf1P);
        now.setPieceCountOf2P(this.pieceCountOf2P);
        now.setPieceOnBoard(this.pieceOnBoard);
        return now;
    }
    
    @Override
    public List<Point> getCanMovePoint(Point piecePoint) {
        List<Point> points = new ArrayList<>();
        Piece clickedPiece = this.pieceOnBoard.get(piecePoint);
        
        if (clickedPiece.is1PlayersPiece() != this.is1PlayerTurn) {
            this.selectedPiece = null;
            return points;
        }
        if (clickedPiece == this.selectedPiece) {
            this.selectedPiece = null;
            return points;
        }
        
        this.selectedPiece = clickedPiece;
        
        for (int xDirection = 0; xDirection < 3; xDirection++) {
            for (int yDirection = 0; yDirection < 3; yDirection++) {
                // ここでのxDirection＝＞0：左、1：そのまま、2：右
                // ここでのyDirection＝＞0：上、1：そのまま、2：下
                if (!canMoveSelectedPiece(xDirection, yDirection)) continue;
                Point canMovePoint = createMovePoint(xDirection, yDirection);
                points.add(canMovePoint);
            }
        }
        
        return points;
    }
    
    private Point createMovePoint(int xDirection, int yDirection) {
        int columnIndex = calcMovePointIndex(selectedPiecePoint().x, xDirection);
        int rowIndex = calcMovePointIndex(selectedPiecePoint().y, yDirection);
        return new Point(columnIndex, rowIndex);
    }
    
    private boolean canMoveSelectedPiece(int xDirection, int yDirection) {
        if (!this.selectedPiece.getMoveRange()[xDirection][yDirection]) return false;
        if (!isInsideSelectedPiece(xDirection, yDirection)) return false;
        Point movePoint = createMovePoint(xDirection, yDirection);
        if (this.pieceOnBoard.containsKey(movePoint)
                && !this.selectedPiece.isEnemy(this.pieceOnBoard.get(movePoint)))
            return false;
        
        return true;
    }
    
    private boolean isInsideSelectedPiece(int columnIndex, int rowIndex) {
        // 盤面左からはみ出すパターン
        if ((selectedPiecePoint().x + columnIndex) == 0) return false;
        // 盤面右からはみ出すパターン
        if ((selectedPiecePoint().x + columnIndex) == 4) return false;
        // 盤面上からはみ出すパターン
        if ((selectedPiecePoint().y + rowIndex) == 0) return false;
        // 盤面下からはみ出すパターン
        if ((selectedPiecePoint().y + rowIndex) == 5) return false;
        
        return true;
    }
    
    /**
     * 二つの引数の横方向、縦方向は揃えてください
     * 
     * @param nowIndex
     * @param rangeIndex
     * @return
     */
    private int calcMovePointIndex(int nowIndex, int rangeIndex) {
        int newIndex = -99;
        switch (rangeIndex) {
            case 0:
                // 横方向：選択駒の左、縦方向：選択駒の上
                newIndex = nowIndex - 1;
                break;
            case 1:
                // 横方向：選択駒と同じ、縦方向：選択駒と同じ
                newIndex = nowIndex;
                break;
            case 2:
                // 横方向：選択駒の右、縦方向：選択駒の下
                newIndex = nowIndex + 1;
                break;
        }
        return newIndex;
    }
    
    @Override
    public List<Point> getCanPopPoint(PieceType storePiece, boolean is1PlayersPiece) {
        List<Point> points = new ArrayList<>();
        
        if (this.is1PlayerTurn != is1PlayersPiece) {
            this.selectedPiece = null;
            return points;
        }
        Function<Boolean, Boolean> existPieceToPut = b -> {
            Function<Map<PieceType, Integer>, Boolean> hasOneOrMore = m -> m.get(storePiece) >= 1;
            if (b) return hasOneOrMore.apply(this.pieceCountOf1P);
            else return hasOneOrMore.apply(this.pieceCountOf2P);
        };
        if (!existPieceToPut.apply(is1PlayersPiece)) {
            this.selectedPiece = null;
            return points;
        }
        
        this.selectedPiece = storePiece.toPiece(is1PlayersPiece);
        
        for (int x = 0; x < this.columnSize; x++) {
            for (int y = 0; y < this.rowSize; y++) {
                Point p = new Point(x, y);
                if (this.pieceOnBoard.containsKey(p)) continue;
                points.add(p);
            }
        }
        return points;
    }
    
    @Override
    public History movePiece(Point movePoint) {
        Piece deadPiece = this.pieceOnBoard.get(movePoint);
        if (deadPiece != null && !deadPiece.getClass().equals(Lion.class))
            updatePieceCount(deadPiece.toPieceType(), !deadPiece.is1PlayersPiece(), 1);
        else if (!this.pieceOnBoard.containsValue(this.selectedPiece))
            updatePieceCount(this.selectedPiece.toPieceType(), this.is1PlayerTurn, -1);
        
        if (this.pieceOnBoard.containsValue(this.selectedPiece))
            this.pieceOnBoard.remove(selectedPiecePoint());
        
        if (this.pieceOnBoard.containsKey(movePoint))
            this.pieceOnBoard.replace(movePoint, this.selectedPiece);
        else
            this.pieceOnBoard.put(movePoint, this.selectedPiece);
        
        this.is1PlayerTurn = !this.is1PlayerTurn;
        this.selectedPiece = null;
        
        return createHistory();
    }
    
    private void updatePieceCount(PieceType type, boolean is1PlayeUpdate, int value) {
        Consumer<Map<PieceType, Integer>> updateExpression = countMap -> {
            int count = countMap.get(type).intValue();
            countMap.replace(type, count + value);
        };
        if (is1PlayeUpdate) updateExpression.accept(this.pieceCountOf1P);
        else updateExpression.accept(this.pieceCountOf2P);
    }
}
