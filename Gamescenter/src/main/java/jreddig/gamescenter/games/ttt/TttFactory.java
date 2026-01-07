package jreddig.gamescenter.games.ttt;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameDescriptor;
import jreddig.gamescenter.core.GameFactory;

public final class TttFactory implements GameFactory {
    @Override
    public Game create() {
        return new TicTacToe();
    }

    @Override
    public GameDescriptor descriptor() {
        return new GameDescriptor("tictactoe", "Tic-Tac-Toe", "3x3 classic");
    }
}
