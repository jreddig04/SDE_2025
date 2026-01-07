package jreddig.gamescenter.games.guess;

import jreddig.gamescenter.core.GameState;

public record GuessState(int attempts, String status, Integer lastGuess) implements GameState {}
