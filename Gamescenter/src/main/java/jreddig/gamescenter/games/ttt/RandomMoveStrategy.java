package jreddig.gamescenter.games.ttt;

import java.util.Random;

public final class RandomMoveStrategy implements MoveStrategy {
    private final Random rnd = new Random();

    @Override public TttPlace nextMove(TicTacToe game) {
        int r, c;
        do {
            r = rnd.nextInt(3);
            c = rnd.nextInt(3);
        } while (game.boardAt(r, c) != ' ');
        return new TttPlace(r, c);
    }
}

