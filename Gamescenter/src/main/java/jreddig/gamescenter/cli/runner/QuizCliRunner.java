package jreddig.gamescenter.cli.runner;

import jreddig.gamescenter.cli.FileHighscoreStorageAdapter;
import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.HighscoreStorage;
import jreddig.gamescenter.games.quiz.AnswerCommand;
import jreddig.gamescenter.games.quiz.MathQuiz;
import jreddig.gamescenter.games.quiz.QuizState;

import java.nio.file.Paths;
import java.util.Scanner;

public class QuizCliRunner implements GameCliRunner {

    @Override
    public void run(Game game, Scanner in) {
        MathQuiz quiz = (MathQuiz) game;
        HighscoreStorage storage = new FileHighscoreStorageAdapter(Paths.get("highscores"));

        quiz.setObserver(state -> {
            QuizState s = (QuizState) state;
            if (s.lastAnswerCorrect() != null) {
                if (s.lastAnswerCorrect()) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong!");
                }
                System.out.printf("Score: %d/%d%n", s.score(), s.totalQuestions());
            }
        });

        while (!quiz.isFinished()) {
            QuizState s = (QuizState) quiz.state();
            if (s.currentQuestion() == null) break;

            System.out.println();
            System.out.println("Question " + (s.questionIndex() + 1) + " of " + s.totalQuestions());
            System.out.println(s.currentQuestion());
            System.out.print("Your answer: ");

            String answer = in.next();
            quiz.handle(new AnswerCommand(answer));
        }

        QuizState finalState = (QuizState) quiz.state();
        int score = finalState.score();
        storage.saveScore("quiz", score);

        System.out.printf("%nQuiz finished! Final score: %d/%d%n",
                finalState.score(), finalState.totalQuestions());

        System.out.println("Top scores:");
        for (int s : storage.loadTopScores("quiz", 5)) {
            System.out.println("  " + s);
        }
    }
}


