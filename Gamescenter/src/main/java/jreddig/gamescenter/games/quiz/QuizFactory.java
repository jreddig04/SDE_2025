package jreddig.gamescenter.games.quiz;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameDescriptor;
import jreddig.gamescenter.core.GameFactory;

public class QuizFactory implements GameFactory {
    @Override
    public Game create() {
        return new MathQuiz();
    }

    @Override
    public GameDescriptor descriptor() {
        return new GameDescriptor("quiz", "Math Quiz", "Answer simple math questions");
    }
}

