package com.jt.beads;

import java.awt.Color;
import java.util.Stack;

public class DrawActionManager {

	public LongDrawAction currentDrawing;

	private Stack<Action> stack = new Stack<Action>();

	public void undo(){
		if(!stack.isEmpty()){
			Action a = stack.peek();
			if(a instanceof LongDrawAction){
				for(DrawAction da : ((LongDrawAction)a).getActionList()){
					undoAction(da);
				}
				stack.pop();
			}else{
				DrawAction da = (DrawAction)a;
				undoAction(da);
				stack.pop();
			}
		}
	}

	private void undoAction(DrawAction da){
		Color c = da.getPreviousColour();
		da.getPixel().setBackground(c);
		da.getPixel().setToolTipText(BeadColours.getNameWithColour(c));
	}

	public void addDrawAction(Action da){
		stack.push(da);
	}

}
