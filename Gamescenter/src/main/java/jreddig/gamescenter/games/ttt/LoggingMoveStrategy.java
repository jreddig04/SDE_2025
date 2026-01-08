package jreddig.gamescenter.games.ttt;

import java.io.PrintStream;

public class LoggingMoveStrategy implements MoveStrategy {

    private final MoveStrategy delegate;
    private final PrintStream out;
    private final String name;

    public LoggingMoveStrategy(MoveStrategy delegate, PrintStream out, String name) {
        this.delegate = delegate;
        this.out = out;
        this.name = name;
    }

    @Override
    public TttPlace nextMove(TicTacToe game) {
        TttPlace move = delegate.nextMove(game);
        out.printf("[%s] chose move (%d,%d)%n", name, move.r(), move.c());
        return move;
    }
}

