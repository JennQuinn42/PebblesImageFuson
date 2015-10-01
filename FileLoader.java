package com.jt.beads;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * Image file management class for IDE code challenge
 * @author Jenn Quinn based from
 * @author Kevin Phair
 * @version 1.0
 * 29 September 2015
 */

public class FileLoader {

private String lastLoadedImageName;
	
	public String getLastLoadedImageName(){
		return lastLoadedImageName;
	}

	public BufferedImage read() {
		BufferedImage pickedImage = null;
		File imageName = getSourceFilename();
		lastLoadedImageName = imageName.getName();
		// Make sure input file is valid before trying to open it
	    if (imageName != null ) {
	        // open image for input
	    	 BufferedImage inFile = null;
				try {
					inFile = ImageIO.read(imageName);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	   pickedImage = inFile;
	    }
	    return pickedImage;
	}
	
	/**
	 * Method to ask the user to pick an image and 
	 * load it into the application
	 * @return BufferedImage retrieved by fileChooser
	 */
	public Color[][] readImage() {
		BufferedImage pickedImage = null;
		File imageName = getSourceFilename();
		Color[][] beads = null;
		// Make sure input file is valid before trying to open it
		if (imageName != null ) {
			// open image for input
			BufferedImage inFile = null;
			try {
				inFile = ImageIO.read(imageName);

			} catch (IOException e) {
				e.printStackTrace();
			}
			pickedImage = inFile;
			Pixelator pixel = new Pixelator();
			beads = pixel.pixelate(6, pickedImage,true);
		}
		return beads;
	}

	/**
	 * Bring up the file selector window to get a filename for loading
	 * @return File to be loaded/saved
	 */
	static public File getSourceFilename () {
		JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		FileFilter filter =  new FileNameExtensionFilter("Picture Files","jpg", "jpeg", "png");
		jfc.setFileFilter(filter);
		jfc.showOpenDialog(null);
		return jfc.getSelectedFile();
	}
}
