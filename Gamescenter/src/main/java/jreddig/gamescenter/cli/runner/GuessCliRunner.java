package jreddig.gamescenter.cli.runner;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.games.guess.GuessCommand;
import jreddig.gamescenter.games.guess.GuessState;
import jreddig.gamescenter.games.guess.NumberGuess;

import java.util.Scanner;

public class GuessCliRunner implements GameCliRunner {

    @Override
    public void run(Game game, Scanner in) {
        NumberGuess g = (NumberGuess) game;

        g.setObserver(state -> {
            var s = (GuessState) state;
            if (s.lastGuess() != null)
                System.out.printf("Your guess: %d → %s%n", s.lastGuess(), s.status());
        });

        System.out.println("I picked a number between 1 and 100. Try to guess it!");

        while (!g.isFinished()) {
            System.out.print("Enter a number: ");
            if (in.hasNextInt()) {
                int guess = in.nextInt();
                g.handle(new GuessCommand(guess));
            } else {
                in.next(); // skip invalid
            }
        }

        System.out.println("Correct! You won in " + ((GuessState) g.state()).attempts() + " attempts.");
    }
}

