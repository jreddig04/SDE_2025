package jreddig.gamescenter.games.quiz;

import jreddig.gamescenter.core.GameDescriptor;

import java.util.List;

public class MathQuiz extends AbstractQuizGame {

    @Override
    protected List<Question> buildQuestions() {
        return List.of(
                new Question("What is 2 + 2?", "4"),
                new Question("What is 3 * 5?", "15"),
                new Question("What is 10 - 6?", "4")
        );
    }

    @Override
    public GameDescriptor descriptor() {
        return new GameDescriptor("quiz", "Math Quiz", "Answer simple math questions");
    }

    @Override
    protected void onCorrect(Question q) {
        status = "LAST_CORRECT";
    }

    @Override
    protected void onWrong(Question q) {
        status = "LAST_WRONG";
    }
}

