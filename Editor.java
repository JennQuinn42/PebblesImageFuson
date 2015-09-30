package com.jt.beads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class Editor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1905868719577381583L;
	private JPanel contentPane;

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
		currentColourPanel.setBounds(100, 11, 24, 17);
		currentColourPanel.setBackground(Color.BLUE);
		
		ActionListener colorPressedListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ColourButton cb = (ColourButton)e.getSource();	
				Color col = cb.getColor();
				
				currentColourPanel.setBackground(col);
				
				
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
		hamaColourPanel.setLayout(new GridLayout(8, 6, 0, 0));
		
		for(int i = 0; i < 48; i++){
			ColourButton temp = new ColourButton(Color.BLUE);
			temp.addActionListener(colorPressedListener);
			hamaColourPanel.add(temp);
		}
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 121, 150, 39);
		toolBar.add(panel);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		toolsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.setLayout(null);
		
		JLabel lblColourChooser = new JLabel("Colour Chooser");
		lblColourChooser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblColourChooser.setBounds(10, 11, 92, 17);
		panel.add(lblColourChooser);
		
		
		panel.add(currentColourPanel);
		
		final JPanel editor = new JPanel();
		contentPane.add(editor, BorderLayout.CENTER);
		editor.setLayout(new FlowLayout());
		
		final JLabel lblNewLabel = new JLabel();
		editor.add(lblNewLabel);
		lblNewLabel.setBounds(10, 11, 414, 206);
		
		
		tglbtnPerlerBeads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				perlerColourPanel.setVisible(!perlerColourPanel.isVisible());
			}
		});
		tglbtnHama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hamaColourPanel.setVisible(!hamaColourPanel.isVisible());
			}
		});
		
		mntmOpen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon pickedImage = null;
				
				BufferedImage image;
				FileLoader fl = new FileLoader();
				image = fl.read();
				if(image != null){
					if(image.getHeight() < lblNewLabel.getHeight() && image.getWidth() < lblNewLabel.getWidth()){
						pickedImage = new ImageIcon(image);
					}else{
						pickedImage = new ImageIcon(image.getScaledInstance(editor.getWidth(), 
								editor.getHeight(), Image.SCALE_SMOOTH));
					}
					lblNewLabel.setIcon(pickedImage);
					lblNewLabel.setText("");
				}
				else{
					lblNewLabel.setText("Did not load Image correctly");
				}
				
				repaint();
			}
			
		});
	}
}