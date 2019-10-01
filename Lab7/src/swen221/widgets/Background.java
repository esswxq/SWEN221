// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package swen221.widgets;

import java.awt.Color;

import swen221.core.Canvas;
import swen221.util.AbstractWidget;
import swen221.util.Rectangle;

/**
 * A very simple widget which provides a simple background colour on which other
 * widgets live
 * 
 * @author David J. Pearce
 *
 */
public class Background extends AbstractWidget {	
	private Color color = Color.WHITE;
	
	public Background(Rectangle dimensions) {
		super(dimensions);
	}
		
	@Override
	public void paint(Canvas canvas) {		
		canvas.fillRectangle(getDimensions(), color);
		super.paint(canvas);
	}	
}
