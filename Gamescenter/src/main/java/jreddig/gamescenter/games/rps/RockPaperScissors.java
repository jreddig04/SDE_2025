package jreddig.gamescenter.games.rps;

import jreddig.gamescenter.core.*;

import java.util.Random;

public final class RockPaperScissors implements Game {
    private final Random rnd = new Random();
    private String status = "RUNNING";
    private boolean finished = false;
    private RpsMove player, ai;

    private GameObserver observer;
    public void setObserver(GameObserver obs) { this.observer = obs; }
    private void notifyObs() { if (observer != null) observer.onStateChange(state()); }

    @Override public void init() { status = "RUNNING"; finished = false; player = null; ai = null; }

    @Override public void handle(Command c) {
        if (finished) return;
        if (c instanceof RpsMove m) {
            player = m;
            ai = RpsMove.values()[rnd.nextInt(3)];
            status = outcome(player, ai);
            finished = true;
            notifyObs();
        }
    }

    @Override public GameState state() { return new RpsState(status, player, ai); }
    @Override public boolean isFinished() { return finished; }
    @Override public GameDescriptor descriptor() {
        return new GameDescriptor("rps", "Rock-Paper-Scissors", "Simple one-round RPS");
    }

    private String outcome(RpsMove p, RpsMove a) {
        if (p == a) return "draw";
        return switch (p) {
            case ROCK     -> (a == RpsMove.SCISSORS) ? "player wins" : "ai wins";
            case PAPER    -> (a == RpsMove.ROCK)     ? "player wins" : "ai wins";
            case SCISSORS -> (a == RpsMove.PAPER)    ? "player wins" : "ai wins";
        };
    }
}

