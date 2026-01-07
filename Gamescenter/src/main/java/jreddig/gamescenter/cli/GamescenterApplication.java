package jreddig.gamescenter.cli;

import jreddig.gamescenter.core.GameFactory;
import jreddig.gamescenter.games.ttt.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class GamescenterApplication {

    public static void main(String[] args) {
        GameFactory factory = new TttFactory();
        TicTacToe game = (TicTacToe) factory.create();
        game.init();

        game.setObserver(state -> printState((TttState) state));
        printState(game.state());

        var in = new Scanner(System.in);
        var human = new HumanMoveStrategy(in);
        var ai = new RandomMoveStrategy();

        char who = 'X';
        while (!game.isFinished()) {
            TttPlace move = (who == 'X') ? human.nextMove(game) : ai.nextMove(game);
            game.handle(move);
            who = (who == 'X') ? 'O' : 'X';
        }
        System.out.println("Game over.");
    }

    private static void printState(TttState s) {
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

