package org.dojo.kata.goosegame.box;

import java.util.function.Function;

public class Space {
	
	String name;
	
	public String getName(){return name;};
	
	public boolean  endsTurn(){return true;};
	
    public Function<Integer, Integer> getSpaceRule() {return (diceResult) -> 0;}
    
    public String getPrintTemplate(){return "";};

}
