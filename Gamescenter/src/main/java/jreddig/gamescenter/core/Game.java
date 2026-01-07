package jreddig.gamescenter.core;

public interface Game {
    void init();

    void handle(Command cmd);

    GameState state();

    boolean isFinished();

    GameDescriptor descriptor();
}