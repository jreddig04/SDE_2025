package jreddig.gamescenter.games.rps;

import jreddig.gamescenter.core.GameState;

public record RpsState(String status, RpsMove player, RpsMove ai) implements GameState {
}
