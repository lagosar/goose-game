package org.dojo.kata.goosegame;

import java.util.Scanner;

import org.dojo.kata.goosegame.engine.CliWriter;
import org.dojo.kata.goosegame.engine.GooseGame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CliWriter.printGameStartMessage();

		boolean continueGame = true;

		GooseGame gooseGame = new GooseGame(); // game status container - config
												// file

		try (Scanner scanner = new Scanner(System.in)) {

			do {
				CliWriter.printActionQuestion();
				String operation = scanner.nextLine();

				continueGame = gooseGame.execute(operation);

				CliWriter.printEndTurn();

			} while (continueGame);

			CliWriter.printGameEndMessage();
		}
	}

}
