package org.dojo.kata.goosegame.box;

public class Dice {

	int diceSize;

	public Dice(int size) {
		diceSize = size;
	}

	public int roll() {
		return (int) (Math.random() * diceSize + 1);
	};

}
