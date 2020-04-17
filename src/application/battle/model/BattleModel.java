package application.battle.model;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.battle.history.History;
import application.battle.piece.Duck;
import application.battle.piece.Elephant;
import application.battle.piece.Giraffe;
import application.battle.piece.Lion;
import application.battle.piece.Piece;
import application.battle.piece.PieceType;

public class BattleModel implements IBattleModel {
    
    private final int columnSize = 3;
    private final int rowSize = 4;
    
    private Map<Point, Piece> pieceOnBoard = new HashMap<>();
    
    private int tookDuckCount = 0;
    private int tookGiraffeCount = 0;
    private int tookElepahntCount = 0;
    private int takenDuckCount = 0;
    private int takenGiraffeCount = 0;
    private int takenElepahntCount = 0;
    
    private Piece selectedPiece = null;
    
    private Point selectedPiecePoint() {
        return this.pieceOnBoard.entrySet().stream().filter(e -> e.getValue().equals(selectedPiece)).map(e -> e.getKey())
                .findFirst().get();
    }
    
    private Boolean is1PlayerTurn;
    
    @Override
    public History init(Boolean is1PlayerTurn) {
        this.is1PlayerTurn = is1PlayerTurn;
        InitTakePieceCount();
        InitPieceOnBoard();
        return createHistory();
    }
    
    private void InitTakePieceCount() {
        this.tookDuckCount = 0;
        this.tookGiraffeCount = 0;
        this.tookElepahntCount = 0;
        this.takenDuckCount = 0;
        this.takenGiraffeCount = 0;
        this.takenElepahntCount = 0;
    }
    
    private void InitPieceOnBoard() {
        // 1P
        addInitializedPiece(Duck.class, 1, 2, true);
        addInitializedPiece(Giraffe.class, 2, 3, true);
        addInitializedPiece(Elephant.class, 0, 3, true);
        addInitializedPiece(Lion.class, 1, 3, true);
        
        // 2P
        addInitializedPiece(Duck.class, 1, 1, false);
        addInitializedPiece(Giraffe.class, 0, 0, false);
        addInitializedPiece(Elephant.class, 2, 0, false);
        addInitializedPiece(Lion.class, 1, 0, false);
    }
    
    private void addInitializedPiece(Class<? extends Piece> pieceType, int xPosition, int yPosition, boolean is1PlayerPiece) {
        try {
            Constructor<?> constructor = pieceType.getConstructor(int.class, int.class, boolean.class);
            Piece piece = (Piece) constructor.newInstance(xPosition, yPosition, is1PlayerPiece);
            this.pieceOnBoard.put(new Point(xPosition, yPosition), piece);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private History createHistory() {
        History result = new History();
        result.setPieceOnBoard(this.pieceOnBoard);
        result.setIs1PlayerTurn(this.is1PlayerTurn);
        result.setTookDuckCount(this.tookDuckCount);
        result.setTookElepahntCount(this.tookElepahntCount);
        result.setTookGiraffeCount(this.tookGiraffeCount);
        result.setTakenDuckCount(this.takenDuckCount);
        result.setTakenElepahntCount(this.takenElepahntCount);
        result.setTakenGiraffeCount(this.takenGiraffeCount);
        return result;
    }
    
    @Override
    public List<Point> getCanMoveRange(Point piecePoint) {
        List<Point> points = new ArrayList<>();
        Piece temp = this.pieceOnBoard.get(piecePoint);
        
        if (temp.is1PlayersPiece() != this.is1PlayerTurn) return points;
        if (temp == this.selectedPiece) return points;
        
        this.selectedPiece = temp;
        
        for (int columnIndex = 0; columnIndex < this.columnSize; columnIndex++) {
            for (int rowIndex = 0; rowIndex < this.rowSize; rowIndex++) {
                if (!this.selectedPiece.getRange()[columnIndex][rowIndex]) continue;
                if (!canMoveSelectedPiece(columnIndex, rowIndex)) continue;
                
                int x = calcMoveRangeIndex(selectedPiecePoint().x, columnIndex);
                int y = calcMoveRangeIndex(selectedPiecePoint().y, rowIndex);
                points.add(new Point(x, y));
            }
        }
        
        return points;
    }
    
    private boolean canMoveSelectedPiece(int columnIndex, int rowIndex) {
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
     * @param nowIndex 
     * @param rangeIndex 
     * @return 
     */
    private int calcMoveRangeIndex(int nowIndex, int rangeIndex) {
        int newIndex = -99;
        switch (rangeIndex) {
            case 0:
                //横方向：選択駒の左、縦方向：選択駒の上
                newIndex = nowIndex - 1;
                break;
            case 1:
                //横方向：選択駒と同じ、縦方向：選択駒と同じ
                newIndex = nowIndex;
                break;
            case 2:
                //横方向：選択駒の右、縦方向：選択駒の下
                newIndex = nowIndex + 1;
                break;
        }
        return newIndex;
    }
    
    @Override
    public List<Point> getCanPopRange(PieceType storePiece, Boolean is1PlayersPiece) {
        List<Point> result = new ArrayList<>();
        
        if (this.is1PlayerTurn != is1PlayersPiece) return result;
        
        for (int x = 0; x < this.columnSize; x++) {
            for (int y = 0; y < this.rowSize; y++) {
                Point p = new Point(x, y);
                if (this.pieceOnBoard.containsKey(p)) continue;
                result.add(p);
            }
        }
        return result;
    }
    
    @Override
    public History movePiece(Point movePoint) {
        Point temp = selectedPiecePoint();
        this.pieceOnBoard.remove(temp);
        this.pieceOnBoard.replace(movePoint, this.selectedPiece);
        return createHistory();
    }
}
