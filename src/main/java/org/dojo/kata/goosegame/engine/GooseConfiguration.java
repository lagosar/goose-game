package org.dojo.kata.goosegame.engine;

import java.util.Arrays;
import java.util.List;

/**
 * Unified access point for Goose Game configuration.
 * 
 * @author lagos
 *
 */
public class GooseConfiguration {

	int boardSize = 63;
	int diceNr = 2;
	int diceSize = 6;
	int bridgePosition = 5;
	int bridgeGap = 6;
	List<Integer> gooses = Arrays.asList(4, 8, 13, 22, 26);

	public int getBoardSize() {
		return boardSize;
	}

	public int getDiceNr() {
		return diceNr;
	}

	public int getDiceSize() {
		return diceSize;
	}

	public int getBridgePosition() {
		return bridgePosition;
	}

	public List<Integer> getGooses() {
		return gooses;
	}

	public int getBridgeGap() {
		return bridgeGap;
	}

}
