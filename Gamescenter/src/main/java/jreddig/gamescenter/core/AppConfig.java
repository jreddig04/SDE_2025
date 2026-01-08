package jreddig.gamescenter.core;

public final class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();

    private final int minGuess = 1;
    private final int maxGuess = 100;

    private AppConfig() { }

    public static AppConfig instance() {
        return INSTANCE;
    }

    public int minGuess() {
        return minGuess;
    }

    public int maxGuess() {
        return maxGuess;
    }
}

