package jreddig.gamescenter.core;

import java.util.List;

public interface HighscoreStorage {
    void saveScore(String gameId, int score);
    List<Integer> loadTopScores(String gameId, int limit);
}

