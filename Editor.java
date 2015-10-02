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
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


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
	private JPanel panel;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmExit;
	private JMenu mnTheme;
	private JMenuItem mntmBackground;
	private JMenuItem mntmFont;
	private JPanel toolBar;
	private JLabel lblTools;
	private JPanel toolsPanel;
	private ColourPanel colourPanel;
	private JPanel perlerPanel;
	private JToggleButton tglbtnPerlerBeads;
	final JPanel perlerColourPanel;
	final JPanel currentColourPanel;
	final JLabel lblColourChooser;
	private JPanel hamaPanel;
	private JToggleButton tglbtnHama;
	final JPanel hamaColourPanel;
	private JMenuItem mntmUndo;
	private JMenu mnEdit;
	private Color defaultColour;
	private static boolean isClicked = false;
	private Color bgColor;
	private int pixelSize;

	private DrawActionManager drawManager = new DrawActionManager();


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

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		mntmUndo = new JMenuItem("Undo");
		mnEdit.add(mntmUndo);
		mntmUndo.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Z, 
				java.awt.Event.CTRL_MASK));
		mntmUndo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawManager.undo();
			}

		});

		mnTheme = new JMenu("Theme");
		menuBar.add(mnTheme);

		mntmNew = new JMenuItem("New Image");
		mnFile.add(mntmNew);
		
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_N, 
				java.awt.Event.CTRL_MASK));
		
		mntmBackground = new JMenuItem("Background Colour");
		mntmBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Color userColour = JColorChooser.showDialog(null, "Background Colour", defaultColour);
				
				if(userColour != null){
					bgColor = userColour;
					setBackgroundColour(userColour);
				}				
			}
		});
		mnTheme.add(mntmBackground);

		mntmFont = new JMenuItem("Font Colour");
		mntmFont.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Color userColour = JColorChooser.showDialog(null, "Font Colour", Color.BLACK);
				if(userColour != null){
					
					setTextColour(userColour);
				}	
			}
		});
		mnTheme.add(mntmFont);

		mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_O, 
				java.awt.Event.CTRL_MASK));
		mntmSave = new JMenuItem("Save Image");
		mntmSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveImage();
			}

		});
		mnFile.add(mntmSave);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S, 
				java.awt.Event.CTRL_MASK));
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);



		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		toolBar = new JPanel();
		toolBar.setPreferredSize(new Dimension(150, 100));
		contentPane.add(toolBar, BorderLayout.WEST);
		toolBar.setLayout(null);

		toolsPanel = new JPanel();
		toolsPanel.setBounds(0, 0, 150, 121);
		toolBar.add(toolsPanel);
		toolsPanel.setLayout(null);

		lblTools = new JLabel("Tools");
		lblTools.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTools.setBounds(10, 11, 46, 14);
		toolsPanel.add(lblTools);

		colourPanel = new ColourPanel();
		colourPanel.setBounds(0, 159, 150, 320);
		toolBar.add(colourPanel);
		colourPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		colourPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));


		perlerPanel = new JPanel();
		colourPanel.add(perlerPanel);
		perlerPanel.setLayout(new BorderLayout(0, 0));

		tglbtnPerlerBeads = new JToggleButton("Perler Beads");
		tglbtnPerlerBeads.setMargin(new Insets(1,15,1,15));

		perlerPanel.add(tglbtnPerlerBeads, BorderLayout.NORTH);

		perlerColourPanel = new JPanel();
		perlerPanel.add(perlerColourPanel);
		perlerColourPanel.setLayout(new GridLayout(8, 6, 0, 0));

		currentColourPanel = new JPanel();

		currentColourPanel.setBackground(currentSelection);

		lblColourChooser = new JLabel("Colour Chooser");

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

		hamaPanel = new JPanel();
		colourPanel.add(hamaPanel);
		hamaPanel.setLayout(new BorderLayout(0, 0));

		tglbtnHama = new JToggleButton("Hama Beads");
		tglbtnHama.setMargin(new Insets(1,15,1,15));
		hamaPanel.add(tglbtnHama, BorderLayout.NORTH);


		hamaColourPanel = new JPanel();
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


		panel = new JPanel();
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
		tglbtnPerlerBeads.setVisible(false);

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
		
		mntmNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new Thread(new Runnable(){

					@Override
					public void run() {

						NewDialog newDialog = createNewDialog();
						newDialog.setVisible(true);
					}

				}).start();


			}

		});
		
		bgColor = Color.DARK_GRAY;
		setBackgroundColour(Color.DARK_GRAY);
		setTextColour(Color.LIGHT_GRAY);
	}

	private void saveImage(){
		final BufferedImage image = new BufferedImage(lastLoadedImage.getWidth(),lastLoadedImage.getWidth(),lastLoadedImage.getType());

		Color temp = bgColor;

		if(boardPanels != null){

			Graphics g = image.getGraphics();
			int pixelSize = this.pixelSize;
			for(int w = 0 ; w < boardPanels.length; w ++)
			{
				for(int h = 0 ; h < boardPanels[0].length; h++)
				{
					if(!temp.equals(boardPanels[w][h].getBackground())){
						g.setColor(boardPanels[w][h].getBackground());
						g.fillRect(w*pixelSize, h*pixelSize, pixelSize, pixelSize);
					}


				}
			}

		}

		JFileChooser saver = new JFileChooser();
		saver.setDialogTitle("Please choose a location to save..");

		saver.showSaveDialog(this);

		String tempPath = saver.getSelectedFile().getAbsolutePath();
		if(!tempPath.endsWith(".png")){
			tempPath += ".png";
		}
		final String path = tempPath;

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

	@SuppressWarnings("unused")
	public void loadImage(BufferedImage image, int size, String colourRange,boolean doCleanUp){
		this.pixelSize = size;
		if(image != null){
			lastLoadedImage = image;
		}
		Color[][] beads = Pixelator.pixelate(size, image, true);
		boardPanels = new JPanel[beads.length][beads[0].length];

		
		
		ImageIcon pickedImage = null;
	
		if(beads != null){
			editor.removeAll();
			editor.setLayout(new GridLayout(beads[0].length, 1, 1, 1));

			for(int i = 0; i < beads[0].length; ++i){
				JPanel row = new JPanel(new GridLayout(1,beads.length,1,1));
				row.setBackground(editor.getBackground());
				for(int j = 0; j < beads.length; ++j){
					final JPanel temp = new JPanel();
					temp.setSize(size, size);
					
					temp.setBackground(beads[j][i]);
					if(beads[j][i] == null){
						temp.setBackground(bgColor);
					}
					boardPanels[j][i] = temp;

					row.add(temp);
					temp.setToolTipText(BeadColours.getNameWithColour(beads[j][i]));
					temp.addMouseListener(new MouseListener(){

						@Override
						public void mouseClicked(MouseEvent arg0) {

						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							if(isClicked){
								if(!drawManager.currentDrawing.getActionList().contains(temp)){
									drawManager.currentDrawing.add(new DrawAction(temp,temp.getBackground()));
									//drawManager.addDrawAction(new DrawAction(temp,temp.getBackground()));
									temp.setBackground(currentSelection);
									temp.setToolTipText(BeadColours.getNameWithColour(currentSelection));
								}
							}

						}

						@Override
						public void mouseExited(MouseEvent arg0) {}

						@Override
						public void mousePressed(MouseEvent arg0) {

							isClicked = true;
							//drawManager.addDrawAction(new DrawAction(temp,temp.getBackground()));
							drawManager.currentDrawing = new LongDrawAction(temp,temp.getBackground());
							temp.setBackground(currentSelection);
							temp.setToolTipText(BeadColours.getNameWithColour(currentSelection));

						}

						@Override
						public void mouseReleased(MouseEvent arg0) {
							isClicked = false;
							drawManager.addDrawAction(drawManager.currentDrawing);
							drawManager.currentDrawing = null;
						}

					});



				}
				editor.add(row);
			}


		}else{
			lblViewImage.setText("Did not load Image correctly");
		}

		tglbtnHama.doClick();
		tglbtnHama.doClick();
		
		repaint();
		
		

	}

	
	public NewDialog createNewDialog(){
		NewDialog nd = new NewDialog(this);
		nd.setVisible(true);

		return nd;
	}
	
	public OpenDialog createDialog(){
		OpenDialog od = new OpenDialog(this);
		od.setVisible(true);

		return od;
	}

	private void setBackgroundColour(Color newColour){
		contentPane.setBackground(newColour);
		panel.setBackground(newColour);
		menuBar.setBackground(newColour);
		mnFile.setBackground(newColour);
		mnEdit.setBackground(newColour);
		mntmUndo.setBackground(newColour);
		mntmNew.setBackground(newColour);
		mntmOpen.setBackground(newColour);
		mntmSave.setBackground(newColour);
		mntmExit.setBackground(newColour);
		mnTheme.setBackground(newColour);
		mntmBackground.setBackground(newColour);
		mntmFont.setBackground(newColour);
		toolBar.setBackground(newColour);
		lblTools.setBackground(newColour);
		toolsPanel.setBackground(newColour);
		colourPanel.setBackground(newColour);
		perlerPanel.setBackground(newColour);
		tglbtnPerlerBeads.setBackground(newColour);
		perlerColourPanel.setBackground(newColour);
		currentColourPanel.setBackground(newColour);
		lblColourChooser.setBackground(newColour);
		hamaPanel.setBackground(newColour);
		tglbtnHama.setBackground(newColour);
		hamaColourPanel.setBackground(newColour);
		UIManager.put("ToggleButton.select", newColour);
		SwingUtilities.updateComponentTreeUI(tglbtnHama);
	}

	private void setTextColour(Color newColour){
		contentPane.setForeground(newColour);
		panel.setForeground(newColour);
		menuBar.setForeground(newColour);
		mnFile.setForeground(newColour);
		mntmNew.setForeground(newColour);
		mntmOpen.setForeground(newColour);
		mntmSave.setForeground(newColour);
		mnEdit.setForeground(newColour);
		mntmUndo.setForeground(newColour);
		mntmExit.setForeground(newColour);
		mnTheme.setForeground(newColour);
		mntmBackground.setForeground(newColour);
		mntmFont.setForeground(newColour);
		toolBar.setForeground(newColour);
		lblTools.setForeground(newColour);
		toolsPanel.setForeground(newColour);
		colourPanel.setForeground(newColour);
		perlerPanel.setForeground(newColour);
		tglbtnPerlerBeads.setForeground(newColour);
		perlerColourPanel.setForeground(newColour);
		currentColourPanel.setForeground(newColour);
		lblColourChooser.setForeground(newColour);
		hamaPanel.setForeground(newColour);
		tglbtnHama.setForeground(newColour);
		hamaColourPanel.setForeground(newColour);
	}
}
