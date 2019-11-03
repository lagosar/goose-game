package org.dojo.kata.goosegame.box;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.dojo.kata.goosegame.engine.CliWriter;

/**
 * Board class, as physical board of goose game, keeps track of the game status, the position of the players and of special purpose spaces.
 * 
 * @author lagos
 *
 */
public class Board {

	Set<Player> players = new HashSet<>();
	ArrayList<Space> spaces;
	int boardSize;

	public boolean addPlayer(String playerName) {
		if (!players.add(new Player(playerName))) {
			CliWriter.printExistingPlayer(playerName);
			return true;
		}
		CliWriter.printPlayersList(players);
		return true;
	}

	public Player getPlayer(String playerName) {
		return players.stream().filter(s -> s.equals(new Player(playerName))).findFirst().get();
	}

	public boolean movePlayer(Player movingPlayer, int diceResult) {

		int boardEffect = 0;
		final int playerOriginal = movingPlayer.position;
		int intermediateTarget = movingPlayer.position + diceResult;
		CliWriter.printPlayerMove(movingPlayer.name, getCellName(playerOriginal), getCellName(intermediateTarget));
		do {
			Space space = spaces.get(intermediateTarget);
			boardEffect = space.getSpaceRule().apply(diceResult);
			intermediateTarget = intermediateTarget + boardEffect;
			CliWriter.printBoardEffect(movingPlayer.name, space.getPrintTemplate(), getCellName(intermediateTarget));
		} while (boardEffect != 0);

		if (intermediateTarget > boardSize) {
			intermediateTarget = boardSize - (intermediateTarget - boardSize);
			CliWriter.printBounce(movingPlayer.name, intermediateTarget);
			movingPlayer.position = intermediateTarget;
			return true;
		}

		final int destination = intermediateTarget;

		Player trickedPlayer = players.stream().filter(p -> p.position == destination && !p.equals(movingPlayer))
				.findAny().orElse(null);
		if (trickedPlayer != null) {
			trickedPlayer.position = playerOriginal;
			CliWriter.printTrick(trickedPlayer.name, getCellName(destination), getCellName(playerOriginal));
			movingPlayer.position = destination;
			return true;
		}

		if (intermediateTarget == boardSize) {
			CliWriter.printPlayerWins(movingPlayer.name);
			return false;
		}

		movingPlayer.position = destination;

		CliWriter.printEndTurn();

		return true;

	}

	private String getCellName(final int space) {
		return spaces.get(space).getName() != null ? spaces.get(space).getName() : space + "";
	}

	public Board(int size, int bridgePosition, int bridgeGap, List<Integer> gooses) {
		spaces = Stream.generate(Space::new).limit(size).collect(toCollection(ArrayList::new));
		this.boardSize = size;

		spaces.add(0, new Space() {
			@Override
			public String getName() {
				return "Start";
			}
		});

		spaces.add(bridgePosition, new Space() {
			@Override
			public String getName() {
				return "The Bridge";
			}

			@Override
			public Function<Integer, Integer> getSpaceRule() {
				return (diceResult) -> bridgeGap;
			}

			@Override
			public boolean endsTurn() {
				return false;
			}

			@Override
			public String getPrintTemplate() {
				return "%s jumps to %s. ";
			}

		});

		spaces.add(size - 1, new Space() {
			@Override
			public String getName() {
				return "Winning Cell";
			}

		});

		gooses.forEach(i -> spaces.add(i, new Space() {
			@Override
			public String getName() {
				return i + ", The Goose";
			}

			@Override
			public Function<Integer, Integer> getSpaceRule() {
				return (diceResult) -> diceResult;
			}

			@Override
			public boolean endsTurn() {
				return false;
			}

			@Override
			public String getPrintTemplate() {
				return "%s moves again and goes to %s. ";
			}

		}));
	}

	public Space getCell(int position) {
		return spaces.get(position);
	}

}
