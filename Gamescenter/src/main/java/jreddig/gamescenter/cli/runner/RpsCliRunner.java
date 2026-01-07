package jreddig.gamescenter.cli.runner;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.games.rps.RockPaperScissors;
import jreddig.gamescenter.games.rps.RpsMove;
import jreddig.gamescenter.games.rps.RpsState;

import java.util.Scanner;

public class RpsCliRunner implements GameCliRunner {

    @Override
    public void run(Game game, Scanner in) {
        RockPaperScissors rps = (RockPaperScissors) game;

        rps.setObserver(s -> {
            var st = (RpsState) s;
            System.out.printf("You: %s, AI: %s => %s%n", st.player(), st.ai(), st.status());
        });

        System.out.println("Choose move: [r]ock, [p]aper, [s]cissors");
        RpsMove mv = readRps(in);
        rps.handle(mv);
    }

    private RpsMove readRps(Scanner in) {
        while (true) {
            String token = in.next().trim().toLowerCase();
            switch (token) {
                case "r", "rock"     -> { return RpsMove.ROCK; }
                case "p", "paper"    -> { return RpsMove.PAPER; }
                case "s", "scissors" -> { return RpsMove.SCISSORS; }
                default -> System.out.print("Use r/p/s (rock/paper/scissors): ");
            }
        }
    }
}

