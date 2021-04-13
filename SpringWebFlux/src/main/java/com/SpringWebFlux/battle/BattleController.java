package com.SpringWebFlux.battle;

import java.awt.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.SpringWebFlux.battle.piece.PieceType;
import com.SpringWebFlux.battle.view.BattleViewController;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    private BattleViewController viewController;

    @PostMapping("/init")
    public Mono<BattleFormResponse> init(@RequestParam("is1PlayerTurn") boolean is1PlayerTurn) {
        return Mono.just(this.viewController.init(is1PlayerTurn));
    }

    @GetMapping("/PieceRange")
    public Mono<BattleFormResponse> clickedPiece(@RequestParam("x") int x, @RequestParam("y") int y) {
        return Mono.just(this.viewController.clickedPiece(new Point(x, y)));
    }

    @PostMapping("/Move")
    public Mono<BattleFormResponse> clickedCanMovePoint(@RequestBody int x, @RequestBody int y) {
        return Mono.just(this.viewController.clickedMoveRange(new Point(x, y)));
    }

    @GetMapping("/TookPieceRange")
    public Mono<BattleFormResponse> clickedTookPiece(@RequestParam("PieceType") String type,
            @RequestParam("is1PlayerTurn") boolean is1PlayersPiece) {
        return Mono.just(this.viewController.clickedStorePiece(Enum.valueOf(PieceType.class, type), is1PlayersPiece));
    }

    @DeleteMapping("/HistoryBack")
    public Mono<BattleFormResponse> clickedHistoryBack() {
        return null;
    }
}
