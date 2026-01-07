package jreddig.gamescenter.games.guess;

import jreddig.gamescenter.core.*;

import java.util.Random;

public class NumberGuess implements Game {

    private int target;
    private int attempts;
    private boolean finished;
    private Integer lastGuess;
    private String status;

    private GameObserver observer;

    @Override
    public void init() {
        target = new Random().nextInt(100) + 1; // random 1..100
        attempts = 0;
        lastGuess = null;
        finished = false;
        status = "RUNNING";
        notifyObs();
    }

    @Override
    public void handle(Command c) {
        if (!(c instanceof GuessCommand g)) return;

        attempts++;
        lastGuess = g.guess();

        if (g.guess() == target) {
            status = "CORRECT";
            finished = true;
        } else if (g.guess() < target) {
            status = "HIGHER";
        } else {
            status = "LOWER";
        }
        notifyObs();
    }

    @Override
    public GameState state() {
        return new GuessState(attempts, status, lastGuess);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public GameDescriptor descriptor() {
        return new GameDescriptor("guess", "Number Guess", "Guess a number between 1 and 100");
    }

    @Override
    public void setObserver(GameObserver obs) {
        this.observer = obs;
    }

    private void notifyObs() {
        if (observer != null)
            observer.onStateChange(state());
    }
}

