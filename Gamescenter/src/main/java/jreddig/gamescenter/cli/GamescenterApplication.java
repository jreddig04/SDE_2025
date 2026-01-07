package jreddig.gamescenter.cli;

import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameFactory;
import jreddig.gamescenter.core.GameRegistry;
import jreddig.gamescenter.games.rps.RpsFactory;
import jreddig.gamescenter.games.ttt.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class GamescenterApplication {

    public static void main(String[] args) {
        GameRegistry registry = new InMemoryRegistry(List.of(
                new TttFactory(),
                new RpsFactory()
        ));

        Map<String, GameCliRunner> runners = Map.of(
                "tictactoe", new TttCliRunner(),
                "rps",       new RpsCliRunner()
        );

        var in = new Scanner(System.in);
        System.out.println("=== Arcade ===");

        var factories = registry.list();
        for (int i = 0; i < factories.size(); i++) {
            var d = factories.get(i).descriptor();
            System.out.printf("[%d] %s (%s)%n", i + 1, d.name(), d.id());
        }

        System.out.print("Select game: ");
        int sel = safeReadIndex(in, 1, factories.size()) - 1;

        GameFactory factory = factories.get(sel);
        String gameId = factory.descriptor().id();
        GameCliRunner runner = runners.get(gameId);

        if (runner == null) {
            System.out.println("No CLI runner registered for game id: " + gameId);
            return;
        }

        Game game = factory.create();
        game.init();
        runner.run(game, in);

        System.out.println("Bye!");
    }

    private static int safeReadIndex(Scanner in, int min, int max) {
        int v;
        while (true) {
            if (in.hasNextInt()) {
                v = in.nextInt();
                if (v >= min && v <= max) return v;
            } else in.next();
            System.out.print("Enter a number between " + min + " and " + max + ": ");
        }
    }
}


