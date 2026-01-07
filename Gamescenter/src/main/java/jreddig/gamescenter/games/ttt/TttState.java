package jreddig.gamescenter.games.ttt;

import jreddig.gamescenter.core.GameState;

public record TttState(char[][] board, char current, String status) implements GameState {
    public char at(int r, int c) { return board[r][c]; }
}

