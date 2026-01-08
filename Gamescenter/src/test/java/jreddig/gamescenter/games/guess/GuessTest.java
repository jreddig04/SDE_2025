package jreddig.gamescenter.games.guess;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuessTest {

    private int getTarget(NumberGuess game) throws Exception {
        Field f = NumberGuess.class.getDeclaredField("target");
        f.setAccessible(true);
        return f.getInt(game);
    }

    @Test
    void correctGuessFinishesGame() throws Exception {
        var g = new NumberGuess();
        g.init();

        int target = getTarget(g);

        g.handle(new GuessCommand(target));

        assertTrue(g.isFinished(), "Game should be finished after correct guess");
        GuessState state = (GuessState) g.state();
        assertEquals("CORRECT", state.status());
        assertEquals(target, state.lastGuess());
    }

    @Test
    void lowerAndHigherHints() throws Exception {
        var g = new NumberGuess();
        g.init();
        int target = getTarget(g);

        if (target > 1) {
            g.handle(new GuessCommand(target - 1));
            GuessState s = (GuessState) g.state();
            assertEquals("HIGHER", s.status(), "Guess below target should yield HIGHER");
        }

        g = new NumberGuess();
        g.init();
        target = getTarget(g);

        if (target < 100) {
            g.handle(new GuessCommand(target + 1));
            GuessState s = (GuessState) g.state();
            assertEquals("LOWER", s.status(), "Guess above target should yield LOWER");
        }
    }

    @Test
    void attemptsAreCounted() throws Exception {
        var g = new NumberGuess();
        g.init();
        int target = getTarget(g);

        g.handle(new GuessCommand(target + (target < 100 ? 1 : -1)));
        g.handle(new GuessCommand(target));

        GuessState s = (GuessState) g.state();
        assertEquals(2, s.attempts(), "There should be exactly 2 attempts");
        assertTrue(g.isFinished());
    }
}

