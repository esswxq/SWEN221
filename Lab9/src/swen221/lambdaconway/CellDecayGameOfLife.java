// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.lambdaconway;

import static swen221.lambdaconway.GameOfLife.*;

import swen221.lambdaconway.model.*;
import swen221.lambdaconway.util.Pair;
import swen221.lambdaconway.util.Point;
import swen221.lambdaconway.view.BoardFrame;

public class CellDecayGameOfLife {
	/**
	 * The standard rule set for Conway's "Game of Life".
	 */
	public static final Rule[] CellDecayRules = {
			// TODO: The underproduction rule
			(Pair<Point,Board> p) -> neighbours(p) < 2 ? helpPlus(p) : null,
			// TODO: The reproduction rule
			(Pair<Point,Board> p) -> neighbours(p) == 3 ? helpMinus(p) : null,
			// TODO: The overpopulation rule
			(Pair<Point,Board> p) -> neighbours(p) > 3 ? helpPlus(p) : null,
	};
	
	public static int helpPlus(Pair<Point, Board> p) {
		int i = p.second().getCellState(p.first().getX(), p.first().getY());
		i++;
		if(i >= 9) return i = 9;
		else return i;
	}
	
	public static int helpMinus(Pair<Point, Board> p) {
		int i = p.second().getCellState(p.first().getX(), p.first().getY());
		i--;
		if(i <= 0) return i = 0;
		else return i;
	}
	
	
	/**
	 * The entry point for the GameOfLife application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Board board = new Board(50,50);
		Simulation sim = new Simulation(board,CellDecayRules);
		new BoardFrame(sim);
	}
}
