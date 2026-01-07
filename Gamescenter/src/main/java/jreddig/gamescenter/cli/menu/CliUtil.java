package jreddig.gamescenter.cli.menu;

import java.util.Scanner;

public class CliUtil {

    public static enum MenuChoice { AGAIN, MENU, EXIT }

    public static MenuChoice ask(Scanner in) {
        while (true) {
            System.out.print("(p)lay again, (m)enu, (e)xit: ");
            String s = in.next().trim().toLowerCase();

            switch (s) {
                case "p", "play", "again" -> { return MenuChoice.AGAIN; }
                case "m", "menu"          -> { return MenuChoice.MENU; }
                case "e", "exit"          -> { return MenuChoice.EXIT; }
            }
        }
    }
}



