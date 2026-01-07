package jreddig.gamescenter.core;

import java.util.List;
import java.util.Optional;

public interface GameRegistry {
    List<GameFactory> list();
    Optional<GameFactory> findById(String id);
}

