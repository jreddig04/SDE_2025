package jreddig.gamescenter.cli.runner;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.games.ttt.*;

import java.util.Scanner;

public class TttCliRunner implements GameCliRunner {

    @Override
    public void run(Game game, Scanner in) {
        TicTacToe ttt = (TicTacToe) game;

        ttt.setObserver(s -> printTtt((TttState) s));
        printTtt(ttt.state());

        var human = new LoggingMoveStrategy(new HumanMoveStrategy(in), System.out, "Human");
        var ai    = new LoggingMoveStrategy(new RandomMoveStrategy(), System.out, "AI");

        char who = 'X';

        while (!ttt.isFinished()) {
            TttPlace move = (who == 'X')
                    ? human.nextMove(ttt)
                    : ai.nextMove(ttt);

            ttt.handle(move);
            who = (who == 'X') ? 'O' : 'X';
        }
    }

    private void printTtt(TttState s) {
        var d = s.board();
        System.out.printf("""
             %c | %c | %c
            ---+---+---
             %c | %c | %c
            ---+---+---
             %c | %c | %c
            turn: %c   status: %s

            """,
                d[0][0], d[0][1], d[0][2],
                d[1][0], d[1][1], d[1][2],
                d[2][0], d[2][1], d[2][2],
                s.current(), s.status());
    }
}



