package jreddig.gamescenter.games.quiz;


import jreddig.gamescenter.core.*;

import java.util.List;

public abstract class AbstractQuizGame implements Game {

    protected List<Question> questions;
    protected int index;
    protected int score;
    protected boolean finished;
    protected String status;
    protected Boolean lastAnswerCorrect;

    private GameObserver observer;

    @Override
    public final void init() {
        questions = buildQuestions();
        index = 0;
        score = 0;
        finished = false;
        status = "RUNNING";
        lastAnswerCorrect = null;
        notifyObserver();
    }

    @Override
    public final void handle(Command cmd) {
        if (!(cmd instanceof AnswerCommand a) || finished) {
            return;
        }

        if (index >= questions.size()) {
            finished = true;
            status = "DONE";
            notifyObserver();
            return;
        }

        Question q = questions.get(index);
        boolean correct = isCorrect(q, a.answer());
        lastAnswerCorrect = correct;

        if (correct) {
            score++;
            onCorrect(q);
        } else {
            onWrong(q);
        }

        index++;
        if (index >= questions.size()) {
            finished = true;
            status = "DONE";
        }

        notifyObserver();
    }

    @Override
    public GameState state() {
        String currentText = null;
        if (!finished && index < questions.size()) {
            currentText = questions.get(index).prompt();
        }
        return new QuizState(
                currentText,
                Math.min(index, questions.size()),
                questions.size(),
                score,
                status,
                lastAnswerCorrect
        );
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    public void setObserver(GameObserver observer) {
        this.observer = observer;
    }

    protected void notifyObserver() {
        if (observer != null) {
            observer.onStateChange(state());
        }
    }

    @Override
    public abstract GameDescriptor descriptor();


    protected abstract List<Question> buildQuestions();

    protected boolean isCorrect(Question q, String userAnswer) {
        if (userAnswer == null) return false;
        return q.correctAnswer().trim().equalsIgnoreCase(userAnswer.trim());
    }

    protected void onCorrect(Question q) { }

    protected void onWrong(Question q) { }
}

