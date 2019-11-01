package org.dojo.kata.goosegame;

import java.util.Scanner;

import org.dojo.kata.goosegame.engine.CliWriter;
import org.dojo.kata.goosegame.engine.GooseGame;

/**
 * # The Goose Game Kata Goose game is a game where two or more players move
 * pieces around a track by rolling a die. The aim of the game is to reach
 * square number sixty-three before any of the other players and avoid
 * obstacles. ([wikipedia](https://en.wikipedia.org/wiki/Game_of_the_Goose))
 * 
 * This kata has been invented by [Matteo Vaccari](https://github.com/xpmatteo),
 * you can find the original slides
 * [here](https://www.slideshare.net/pierodibello/il-dilettevole-giuoco-delloca-coding-dojo).
 * 
 * @author lagos
 *
 */
public class Main {

	/**
	 * @param args
	 *            not yet used
	 */
	public static void main(String[] args) {

		CliWriter.printGameStartMessage();

		boolean continueGame = true;

		GooseGame gooseGame = new GooseGame();

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
