package jreddig.gamescenter.games.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathQuizTest {

    @Test
    void initialStateIsRunningWithFirstQuestion() {
        MathQuiz quiz = new MathQuiz();
        quiz.init();

        QuizState state = (QuizState) quiz.state();

        assertFalse(quiz.isFinished());
        assertEquals("RUNNING", state.status());
        assertEquals(0, state.questionIndex());
        assertEquals(3, state.totalQuestions());
        assertEquals(0, state.score());
        assertNotNull(state.currentQuestion());
        assertNull(state.lastAnswerCorrect());
    }

    @Test
    void allCorrectAnswersFinishQuizWithFullScore() {
        MathQuiz quiz = new MathQuiz();
        quiz.init();

        while (!quiz.isFinished()) {
            QuizState s = (QuizState) quiz.state();
            String question = s.currentQuestion();
            assertNotNull(question);

            String answer;
            if (question.contains("2 + 2")) {
                answer = "4";
            } else if (question.contains("3 * 5")) {
                answer = "15";
            } else if (question.contains("10 - 6")) {
                answer = "4";
            } else {
                fail("Unexpected question: " + question);
                return;
            }

            quiz.handle(new AnswerCommand(answer));
        }

        QuizState finalState = (QuizState) quiz.state();

        assertTrue(quiz.isFinished());
        assertEquals(3, finalState.totalQuestions());
        assertEquals(3, finalState.score());
        assertEquals("DONE", finalState.status());
    }
}

