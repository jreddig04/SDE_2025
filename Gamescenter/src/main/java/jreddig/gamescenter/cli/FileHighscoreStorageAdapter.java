package jreddig.gamescenter.cli;

import jreddig.gamescenter.core.HighscoreStorage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileHighscoreStorageAdapter implements HighscoreStorage {

    private final Path dir;

    public FileHighscoreStorageAdapter(Path dir) {
        this.dir = dir;
    }

    @Override
    public void saveScore(String gameId, int score) {
        try {
            Files.createDirectories(dir);
            Path file = dir.resolve(gameId + ".txt");
            String line = Integer.toString(score);
            Files.writeString(file, line + System.lineSeparator(),
                    StandardCharsets.UTF_8, CREATE, APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<Integer> loadTopScores(String gameId, int limit) {
        Path file = dir.resolve(gameId + ".txt");
        if (!Files.exists(file)) return List.of();

        try {
            return Files.readAllLines(file, StandardCharsets.UTF_8).stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .sorted(Comparator.reverseOrder())
                    .limit(limit)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

