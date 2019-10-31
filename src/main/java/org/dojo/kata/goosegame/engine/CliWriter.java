package org.dojo.kata.goosegame.engine;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.dojo.kata.goosegame.box.Player;

public final class CliWriter {

	private static final PrintStream printStream = new PrintStream(System.out);

	public static void printGameStartMessage() {
		printStream.println("Hello! This is The Goose Game!");
	}

	public static void printGameEndMessage() {
		printStream.println("Nice playing with you, bye!");
	}

	public static void printExistingPlayer(String playerName) {
		printStream.println(playerName + ": already existing player");
	}

	public static void printPlayersList(Set<Player> players) {
		printStream.println("Players : " + players.stream().map(Player::toString).collect(Collectors.joining(", ")));
	}

	public static void printBounce(String name, int intermediateTarget) {
		printStream.print(name + " bounces! ");
		printStream.println(name + " returns to " + intermediateTarget + ". ");
	}

	public static void printPlayerMove(String name, String from, String to) {
		printStream.print(name + " moves from " + from + " to " + to + ". ");
	}

	public static void printPlayerWins(String name) {
		printStream.println(name + " Wins!!!");
	}

	public static void printDiceRoll(String name, int[] diceResults) {
		String diceValues = Arrays.toString(diceResults);
		printStream.print(name + " rolls " + diceValues.substring(1, diceValues.length() - 1) + ". ");
	}

	public static void printActionQuestion() {
		printStream.print("What do you wanna do? (h=help): ");
	}

	public static void printError(String errorMessage) {
		printStream.println(errorMessage);
	}

	public static void printTrick(String trickedPlayer, String destination, String origin) {
		printStream.printf("On %s there is %s who returns to %s", destination, trickedPlayer, origin);

	}

	public static void printBoardEffect(String playerName, String printformat, String destinationCellName) {
		printStream.printf(printformat, playerName, destinationCellName);
	}

	public static void printEndTurn() {
		printStream.println();
	}

}
