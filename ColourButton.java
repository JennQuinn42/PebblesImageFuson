package com.jt.beads;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JButton;

public class ColourButton extends JButton{

	private Color color;
	
	public ColourButton(Color color){
		this.color = color;
		setBackground(color);
		setMargin(new Insets(4,4,4,4));
	}
	
	public Color getColor(){
		return color;
	}
	
	
}
