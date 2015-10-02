package com.jt.beads;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

public class LongDrawAction implements Action{

	private ArrayList<DrawAction> actions = new ArrayList<DrawAction>();
	
	public LongDrawAction(JPanel pixel, Color previousColour) {
		actions.add(new DrawAction(pixel,previousColour));
	}
	
	public void add(DrawAction action){
		actions.add(action);
	}
	
	public ArrayList<DrawAction> getActionList(){
		return actions;
	}

	
}
