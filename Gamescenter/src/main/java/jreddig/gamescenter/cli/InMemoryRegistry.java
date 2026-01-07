package jreddig.gamescenter.cli;

import jreddig.gamescenter.core.GameFactory;
import jreddig.gamescenter.core.GameRegistry;

import java.util.List;
import java.util.Optional;

public record InMemoryRegistry(List<GameFactory> factories) implements GameRegistry {
    @Override public List<GameFactory> list() { return factories; }
    @Override public Optional<GameFactory> findById(String id) {
        return factories.stream().filter(f -> f.descriptor().id().equals(id)).findFirst();
    }
}

