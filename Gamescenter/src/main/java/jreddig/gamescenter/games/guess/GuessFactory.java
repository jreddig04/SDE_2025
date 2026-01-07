package jreddig.gamescenter.games.guess;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameDescriptor;
import jreddig.gamescenter.core.GameFactory;

public class GuessFactory implements GameFactory {
    @Override
    public Game create() {
        return new NumberGuess();
    }

    @Override
    public GameDescriptor descriptor() {
        return new GameDescriptor("guess", "Number Guess", "Guess a number between 1 and 100");
    }
}

