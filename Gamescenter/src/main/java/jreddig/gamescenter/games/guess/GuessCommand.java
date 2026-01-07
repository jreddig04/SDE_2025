package jreddig.gamescenter.games.guess;

import jreddig.gamescenter.core.Command;

public record GuessCommand(int guess) implements Command {}
