package jreddig.gamescenter.cli.menu;

import jreddig.gamescenter.cli.runner.*;
import jreddig.gamescenter.core.Game;
import jreddig.gamescenter.core.GameFactory;
import jreddig.gamescenter.core.GameRegistry;
import jreddig.gamescenter.games.guess.GuessFactory;
import jreddig.gamescenter.games.quiz.QuizFactory;
import jreddig.gamescenter.games.rps.RpsFactory;
import jreddig.gamescenter.cli.menu.InMemoryRegistry;
import jreddig.gamescenter.games.ttt.TttFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


@SpringBootApplication
public class GamescenterApplication {

    public static void main(String[] args) {

        GameRegistry registry = new InMemoryRegistry(List.of(
                new TttFactory(),
                new RpsFactory(),
                new GuessFactory(),
                new QuizFactory()
        ));

        Map<String, GameCliRunner> runners = Map.of(
                "tictactoe", new TttCliRunner(),
                "rps",       new RpsCliRunner(),
                "guess", new GuessCliRunner(),
                "quiz", new QuizCliRunner()
        );

        var in = new Scanner(System.in);

        while (true) {
            System.out.println("=== Arcade ===");

            var factories = registry.list();
            for (int i = 0; i < factories.size(); i++) {
                var d = factories.get(i).descriptor();
                System.out.printf("[%d] %s (%s)%n", i + 1, d.name(), d.id());
            }
            System.out.print("Select game: ");

            int sel = safeReadIndex(in, 1, factories.size()) - 1;
            GameFactory factory = factories.get(sel);
            String id = factory.descriptor().id();

            GameCliRunner runner = runners.get(id);
            if (runner == null) {
                System.out.println("No runner registered.");
                continue;
            }

            boolean playAgain = true;

            while (playAgain) {
                Game game = factory.create();
                game.init();

                runner.run(game, in);

                var choice = CliUtil.ask(in);

                switch (choice) {
                    case AGAIN -> playAgain = true;
                    case MENU  -> playAgain = false;
                    case EXIT  -> { System.out.println("Bye!"); return; }
                }
            }
        }
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





