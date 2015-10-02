package com.jt.beads;

import java.awt.Color;

class BeadColours{
	
	public static final Color WHITE = new Color(255,255,255);
	public static final Color CREAM = new Color(185,186,158);
	public static final Color YELLOW = new Color(187,149,8);
	public static final Color ORANGE = new Color(171,1,1);
	public static final Color RED = new Color(162,2,20);
	public static final Color PINK = new Color(209,102,132);
	public static final Color PURPLE = new Color(56,9,90);
	public static final Color BLUE = new Color(17,0,84);
	
	public static final Color LIGHT_BLUE = new Color(28,27,139);
	public static final Color GREEN = new Color(0,78,59);
	public static final Color LIGHT_GREEN = new Color(75,171,131);
	public static final Color DARK_BROWN = new Color(35,23,26);
	public static final Color GREY = new Color(67,75,93);
	public static final Color BLACK = new Color(11,12,16);
	public static final Color REDDISH_BROWN = new Color(113,17,13);
	public static final Color LIGHT_BROWN = new Color(131,55,25);
	
	public static final Color DARK_RED = new Color(130,23,34);
	public static final Color FLESH = new Color(178,104,101);
	public static final Color BEIGE = new Color(191,151,119);
	public static final Color OLIVE_GREEN = new Color(36,42,46);
	public static final Color CLARET = new Color(125,0,33);
	public static final Color BURGUNDY = new Color(44,12,27);
	public static final Color TURQUOISE = new Color(27,115,147);
	
	public static final Color PASTEL_YELLOW = new Color(225,204,60);
	public static final Color PASTEL_RED = new Color(197,75,71);
	public static final Color PASTEL_PURPLE = new Color(95,40,141);
	public static final Color PASTEL_BLUE = new Color(18,105,175);
	public static final Color PASTEL_GREEN = new Color(0,125,107);
	public static final Color PASTEL_PINK = new Color(186,79,160);
	
	public static final Color AZURE = new Color(88,168,188);
	public static final Color TEDDY_BEAR_BROWN = new Color(215,151,45);
	
	public static final Color DARK_GREY = new Color(100,100,100);
	public static final Color LIGHT_GREY = new Color(222,222,220);

	public static final Color CLEAR = new Color(240,251,251);
	public static final Color FUCHSIA = new Color(252,83,122);
	public static final Color CERISE = new Color(254,149,153);
	
	public static String getNameWithColour(Color colour){
		for(int i = 0; i < colorArray.length; i++){
			Color c = colorArray[i];
			if(c.equals(colour)){
				return colorNameArray[i];
			}
		}
		return null;
	}

	public static final Color[] colorArray = {WHITE,CREAM,YELLOW,ORANGE,RED,PINK,PURPLE,BLUE,LIGHT_BLUE,GREEN,LIGHT_GREEN,DARK_BROWN,GREY,BLACK,REDDISH_BROWN,LIGHT_BROWN,
	DARK_RED,FLESH, BEIGE,OLIVE_GREEN,CLARET,BURGUNDY,TURQUOISE,PASTEL_YELLOW,PASTEL_RED,PASTEL_PURPLE,PASTEL_BLUE,PASTEL_GREEN,
	PASTEL_PINK,AZURE,TEDDY_BEAR_BROWN, DARK_GREY, LIGHT_GREY, CLEAR, FUCHSIA, CERISE};
	
	public static final String[] colorNameArray = {"White","Cream","Yellow","Orange","Red","Pink","Purple","Blue","Light Blue",
			"Green","Light Green","Dark Brown","Grey","Black","Reddish Brown","Light Brown",
			"Dark Red","Flesh", "Beige","Olive Green","Claret","Burgundy","Turquiose","Pastel Yellow","Pastel Red","Pastel Purple",
			"Pastel Blue","Pastel Green","Pastel Pink","Azure","Teddy Bear Brown","Dark Grey", "Light Grey", "Clear", 
			"Fuchsia", "Cerise"};

}
