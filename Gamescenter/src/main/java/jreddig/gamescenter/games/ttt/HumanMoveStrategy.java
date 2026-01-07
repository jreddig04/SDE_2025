package jreddig.gamescenter.games.ttt;

import java.util.Scanner;

public final class HumanMoveStrategy implements MoveStrategy {
    private final Scanner in;

    public HumanMoveStrategy(Scanner in) {
        this.in = in;
    }

    @Override
    public TttPlace nextMove(TicTacToe game) {
        System.out.print("move (row col) 0..2: ");
        int r = in.nextInt(), c = in.nextInt();
        return new TttPlace(r, c);
    }
}

