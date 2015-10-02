package com.jt.beads;

import java.awt.Color;

import javax.swing.JPanel;

public class DrawAction implements Action{
	
	private JPanel pixel;
	private Color previousColour;
	
	public DrawAction(JPanel pixel, Color previousColour){
		this.pixel = pixel;
		this.previousColour = previousColour;
	}

	public JPanel getPixel() {
		return pixel;
	}

	public Color getPreviousColour() {
		return previousColour;
	}

	public String toString(){
		return pixel + " of Colour " + previousColour;
	}
}
