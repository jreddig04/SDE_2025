package jreddig.gamescenter.games.quiz;

import jreddig.gamescenter.core.GameState;

public record QuizState(
        String currentQuestion,
        int questionIndex,
        int totalQuestions,
        int score,
        String status,
        Boolean lastAnswerCorrect
) implements GameState { }

