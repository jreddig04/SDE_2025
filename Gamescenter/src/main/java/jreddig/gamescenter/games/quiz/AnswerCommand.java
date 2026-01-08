package jreddig.gamescenter.games.quiz;

import jreddig.gamescenter.core.Command;

public record AnswerCommand(String answer) implements Command { }
