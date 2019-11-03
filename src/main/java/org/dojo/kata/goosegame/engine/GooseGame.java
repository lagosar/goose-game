package org.dojo.kata.goosegame.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.dojo.kata.goosegame.box.Board;
import org.dojo.kata.goosegame.box.Dice;
import org.dojo.kata.goosegame.box.Player;

/**
 * Goose game is responsible to read game configuration and manage player
 * commands input.
 * 
 * @author lagos
 *
 */
public class GooseGame {

	GooseConfiguration conf = new GooseConfiguration();

	Map<String, GooseOperationExecutor> executors = new HashMap<>();

	Board board = new Board(conf.getBoardSize(), conf.getBridgePosition(), conf.getBridgeGap(), conf.getGooses());

	List<Dice> dices;

	GooseOperationExecutor playerAdd = (argument) -> {
		return board.addPlayer(argument[0]);
	};

	GooseOperationExecutor mover = (arguments) -> {
		Player movingPlayer = board.getPlayer(arguments[0].trim());
		int span = 0;
		int[] diceResults = null;
		if (arguments.length > 1) {
			diceResults = Arrays.asList(arguments).stream().skip(1).mapToInt(s -> Integer.parseInt(s)).toArray();
		} else {
			diceResults = dices.stream().mapToInt(Dice::roll).toArray();
		}
		CliWriter.printDiceRoll(movingPlayer.name, diceResults);
		span = IntStream.of(diceResults).sum();
		return board.movePlayer(movingPlayer, span);
	};
	
	GooseOperationExecutor helper = (arguments) -> {
		CliWriter.printHelp();
		return true;
	};

	public GooseGame() {
		dices = new ArrayList<>();
		for (int i = 0; i < conf.getDiceNr(); i++) {
			dices.add(new Dice(conf.getDiceSize()));
		}
		executors.put("add player", playerAdd);
		executors.put("move", mover);
		executors.put("h", helper);
	}

	public boolean execute(String operation) {

		String key = null;
		GooseOperationExecutor executor = null;
		try {
			key = executors.keySet().stream().filter(s -> operation.startsWith(s)).findFirst().get();
			executor = executors.get(key);
		} catch (NoSuchElementException e) {
			CliWriter.printError("Sorry, unknown command.");
			return true;
		}
		
		String[] arguments = operation.replace(key, "").trim().split("\\s+|,\\s?|\\.\\s*");
		boolean gameContinues = true;
		try {
			gameContinues = executor.execute(arguments);
		} catch (RuntimeException e) {
			CliWriter.printError("Error in given parameters.");
		}
		return gameContinues;

	}

}
