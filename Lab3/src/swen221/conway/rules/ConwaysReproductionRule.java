// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.conway.rules;

import swen221.conway.model.*;

/**
 * This should implement Conway's rule for reproduction:
 * 
 * "Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction"
 * 
 * @author David J. Pearce
 *
 */
public class ConwaysReproductionRule implements Rule {
	
	@Override
	public int apply(int x, int y, BoardView board) {
		// default implementation which does nothing.
		return Rule.NOT_APPLICABLE;		
	}
}
