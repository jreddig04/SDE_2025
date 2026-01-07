package jreddig.gamescenter.games.ttt;

import jreddig.gamescenter.core.Command;

public record TttPlace(int r, int c) implements Command { }
