package jreddig.gamescenter.games.ttt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicTacToeTest {

    @Test
    void xWinsRow0() {
        var g = new TicTacToe();
        g.init();
        g.handle(new TttPlace(0,0));
        g.handle(new TttPlace(1,0));
        g.handle(new TttPlace(0,1));
        g.handle(new TttPlace(1,1));
        g.handle(new TttPlace(0,2));

        var s = (TttState) g.state();
        assertTrue(g.isFinished());
        assertEquals("X wins", s.status());
        assertEquals('X', s.at(0,0));
        assertEquals('X', s.at(0,1));
        assertEquals('X', s.at(0,2));
    }
}

