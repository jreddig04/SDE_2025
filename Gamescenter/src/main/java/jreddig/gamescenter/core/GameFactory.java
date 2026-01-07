package jreddig.gamescenter.core;

public interface GameFactory {
    Game create();

    GameDescriptor descriptor();
}

