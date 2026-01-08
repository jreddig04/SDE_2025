package jreddig.gamescenter.games.rps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RpsTest {
    @Test
    void finishesAfterOneMove() {
        var g = new RockPaperScissors();
        g.init();
        assertFalse(g.isFinished());
        g.handle(RpsMove.ROCK);
        assertTrue(g.isFinished());
        var s = (RpsState) g.state();
        assertNotNull(s.player());
        assertNotNull(s.ai());
        assertNotNull(s.status());
    }
}
