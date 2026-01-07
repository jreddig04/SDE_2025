package jreddig.gamescenter.core;

public interface Game {
    void init();

    void handle(Command cmd);

    void setObserver(GameObserver obs);

    GameState state();

    boolean isFinished();

    GameDescriptor descriptor();
}
