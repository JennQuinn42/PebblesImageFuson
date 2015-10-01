package com.jt.beads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Editor extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1905868719577381583L;
	private JPanel contentPane;
	final JLabel lblViewImage;
	final JLayeredPane editor;
	private Color currentSelection = BeadColours.colorArray[0];
	private JPanel[][] boardPanels;
	private BufferedImage lastLoadedImage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor frame = new Editor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	


	/**
	 * Create the frame.
	 */
	public Editor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);

		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnMenu.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save Image");
		mntmSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveImage();
			}
			
		});
		mnMenu.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnMenu.add(mntmExit);
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel toolBar = new JPanel();
		toolBar.setPreferredSize(new Dimension(150, 100));
		contentPane.add(toolBar, BorderLayout.WEST);
		toolBar.setLayout(null);
		
		JPanel toolsPanel = new JPanel();
		toolsPanel.setBounds(0, 0, 150, 121);
		toolBar.add(toolsPanel);
		toolsPanel.setLayout(null);
		
		JLabel lblTools = new JLabel("Tools");
		lblTools.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTools.setBounds(10, 11, 46, 14);
		toolsPanel.add(lblTools);
		
		ColourPanel colourPanel = new ColourPanel();
		colourPanel.setBounds(0, 159, 150, 320);
		toolBar.add(colourPanel);
		colourPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		colourPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		
		JPanel perlerPanel = new JPanel();
		colourPanel.add(perlerPanel);
		perlerPanel.setLayout(new BorderLayout(0, 0));
		
		JToggleButton tglbtnPerlerBeads = new JToggleButton("Perler Beads");
		tglbtnPerlerBeads.setMargin(new Insets(1,15,1,15));
		
		perlerPanel.add(tglbtnPerlerBeads, BorderLayout.NORTH);
		
		final JPanel perlerColourPanel = new JPanel();
		perlerPanel.add(perlerColourPanel);
		perlerColourPanel.setLayout(new GridLayout(8, 6, 0, 0));
		
		final JPanel currentColourPanel = new JPanel();
		
		currentColourPanel.setBackground(currentSelection);
		
		final JLabel lblColourChooser = new JLabel("Colour Chooser");
		
		ActionListener colorPressedListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ColourButton cb = (ColourButton)e.getSource();	
				Color col = cb.getColor();
				
				currentColourPanel.setBackground(col);
				currentSelection = col;
				int colourIndex = -1;
				for(int i = 0; i < BeadColours.colorArray.length; i++){
					if(col.equals(BeadColours.colorArray[i])){
						colourIndex = i;
						break;
					}
				}
				String colourName = BeadColours.colorNameArray[colourIndex];
				lblColourChooser.setText(colourName);
			
			}
			
		};
		
		for(int i = 0; i < 48; i++){
			ColourButton temp = new ColourButton(Color.RED);
			temp.addActionListener(colorPressedListener);
			perlerColourPanel.add(temp);
		}
		
		JPanel hamaPanel = new JPanel();
		colourPanel.add(hamaPanel);
		hamaPanel.setLayout(new BorderLayout(0, 0));
		
		JToggleButton tglbtnHama = new JToggleButton("Hama Beads");
		tglbtnHama.setMargin(new Insets(1,15,1,15));
		hamaPanel.add(tglbtnHama, BorderLayout.NORTH);
		
		
		final JPanel hamaColourPanel = new JPanel();
		hamaPanel.add(hamaColourPanel, BorderLayout.CENTER);
		hamaColourPanel.setLayout(new GridLayout(4, 6, 0, 0));
		
		//Add hama bead colours
		Color[] hamaColoursArray = BeadColours.colorArray;
		for(int i = 0; i < hamaColoursArray.length; i++){
			ColourButton temp = new ColourButton(hamaColoursArray[i]);
			temp.addActionListener(colorPressedListener);
			temp.setToolTipText(BeadColours.getNameWithColour(hamaColoursArray[i]));
			hamaColourPanel.add(temp);
		}
	
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 121, 150, 39);
		toolBar.add(panel);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		toolsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.setLayout(null);
		
		lblColourChooser.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panel.add(lblColourChooser);
		lblColourChooser.setBounds(35, 11, 112, 17);
		currentColourPanel.setBounds(5, 11, 24, 17);
		currentColourPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		panel.add(currentColourPanel);
		
		editor = new JLayeredPane();
		contentPane.add(editor, BorderLayout.CENTER);
		editor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		editor.setLayout(new GridLayout());
		
		lblViewImage = new JLabel();
		editor.add(lblViewImage, JLayeredPane.DEFAULT_LAYER);
		lblViewImage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		lblViewImage.setBounds(10, 11, 414, 206);
		
		
		tglbtnPerlerBeads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				perlerColourPanel.setVisible(!perlerColourPanel.isVisible());
			}
		});
		tglbtnPerlerBeads.doClick();
		tglbtnHama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hamaColourPanel.setVisible(!hamaColourPanel.isVisible());
			}
		});
		
		mntmOpen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						
						OpenDialog openDialog = createDialog();
						openDialog.setVisible(true);
					}
					
				}).start();
				
		
			}
			
		});
	}
	
	private void saveImage(){
		final BufferedImage image = new BufferedImage(lastLoadedImage.getWidth(),lastLoadedImage.getWidth(),lastLoadedImage.getType());
		
		if(boardPanels != null){
			
			Graphics g = image.getGraphics();
			int pixelSize = 3;
			for(int w = 0 ; w < boardPanels.length; w ++)
			{
				for(int h = 0 ; h < boardPanels[0].length; h++)
				{
					g.setColor(boardPanels[w][h].getBackground());
					g.fillRect(w, h, pixelSize, pixelSize);

				}
			}
			
		}
		
		JFileChooser saver = new JFileChooser();
		saver.setDialogTitle("Please choose a location to save..");
		//saver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//saver.showOpenDialog(this);
		saver.showSaveDialog(this);
		//FileFilter filter = new FileNameExtensionFilter("Image file","png");
		//saver.setFileFilter(filter);
		
		String tempPath = saver.getSelectedFile().getAbsolutePath();
		if(!tempPath.endsWith(".png")){
			tempPath += ".png";
		}
		final String path = tempPath;
		//System.out.println(image);
		//System.out.println(".png");
		//System.out.println(path);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					ImageIO.write(image, "png", new File(path));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error when saving image..");
				}
			}
			
		}).start();
	}
	
	public void loadImage(BufferedImage image, int size, String colourRange,boolean doCleanUp){
		
		if(image != null){
			lastLoadedImage = image;
		}
		Color[][] beads = Pixelator.pixelate(size, image, true);
		boardPanels = new JPanel[beads.length][beads[0].length];
		System.out.println("pixelated");
		ImageIcon pickedImage = null;

		if(beads != null){
			editor.removeAll();
			editor.setLayout(new GridLayout(beads[0].length, 1, 1, 1));
			
			for(int i = 0; i < beads[0].length; ++i){
				JPanel row = new JPanel(new GridLayout(1,beads.length,1,0));
				for(int j = 0; j < beads.length; ++j){
					final JPanel temp = new JPanel();
					temp.setSize(size, size);
					temp.setBackground(beads[j][i]);
					boardPanels[j][i] = temp;
					//editor.add(temp);
					row.add(temp);
					temp.setToolTipText(BeadColours.getNameWithColour(beads[j][i]));
					temp.addMouseListener(new MouseListener(){

						@Override
						public void mouseClicked(MouseEvent arg0) {}

						@Override
						public void mouseEntered(MouseEvent arg0) {}

						@Override
						public void mouseExited(MouseEvent arg0) {}

						@Override
						public void mousePressed(MouseEvent arg0) {
							temp.setBackground(currentSelection);
							temp.setToolTipText(BeadColours.getNameWithColour(currentSelection));
						}

						@Override
						public void mouseReleased(MouseEvent arg0) {}
						
					});
					
					//editor.paintComponents(editor.getGraphics());
					
				}
				editor.add(row);
				System.out.println("line " + (i+1) + " of " + beads[0].length);
			}
			
			
		}else{
			lblViewImage.setText("Did not load Image correctly");
		}
		
		repaint();
	
	}
	
	public OpenDialog createDialog(){
		OpenDialog od = new OpenDialog(this);
		od.setVisible(true);
		
		return od;
	}
}
