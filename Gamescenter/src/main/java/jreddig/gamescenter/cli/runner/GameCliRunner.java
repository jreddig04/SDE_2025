package jreddig.gamescenter.cli.runner;

import jreddig.gamescenter.core.Game;

import java.util.Scanner;

public interface GameCliRunner {
    void run(Game game, Scanner in);
}

