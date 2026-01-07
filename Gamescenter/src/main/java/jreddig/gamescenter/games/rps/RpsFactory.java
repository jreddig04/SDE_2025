package jreddig.gamescenter.games.rps;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameDescriptor;
import jreddig.gamescenter.core.GameFactory;

public final class RpsFactory implements GameFactory {
    @Override public Game create() { return new RockPaperScissors(); }
    @Override public GameDescriptor descriptor() {
        return new GameDescriptor("rps", "Rock-Paper-Scissors", "Simple one-round RPS");
    }
}

