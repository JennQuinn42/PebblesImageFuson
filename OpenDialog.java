package com.jt.beads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class OpenDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtImageName;
	private BufferedImage imageFile;
	private Editor frame;

	public static void main(String[] args){
		Editor e = new Editor();
		e.setVisible(false);
		new OpenDialog(e).setVisible(true);
	}
	/**
	 * Create the dialog.
	 */
	public OpenDialog(final Editor frame) {
		super(frame,"Load image..");
		this.frame = frame;
		setBounds(100, 100, 220, 237);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(UIManager.getColor("Button.light"));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtImageName = new JTextField();
		txtImageName.setBackground(Color.LIGHT_GRAY);
		txtImageName.setText("Image name..");
		txtImageName.setEditable(false);
		txtImageName.setBounds(10, 21, 100, 20);
		contentPanel.add(txtImageName);
		txtImageName.setColumns(10);
		
		JButton openImage = new JButton("Open..");
		openImage.setBounds(120, 20, 74, 23);
		contentPanel.add(openImage);
		openImage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileLoader fl = new FileLoader();
				imageFile = fl.read();
				txtImageName.setText(fl.getLastLoadedImageName());
			}
			
		});
		
		JLabel lblColourRange = new JLabel("Colour Range");
		lblColourRange.setBounds(10, 52, 79, 14);
		contentPanel.add(lblColourRange);
		
		String[] colRanges = {"Hama Midi", "Hama Mini", "Perler"};
		final JComboBox comboBox = new JComboBox(colRanges);
		comboBox.setBounds(10, 72, 120, 20);
		contentPanel.add(comboBox);
		
		JLabel lblPixelSize = new JLabel("Pixel Size");
		lblPixelSize.setBounds(10, 104, 79, 14);
		contentPanel.add(lblPixelSize);
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(3), new Integer(2), null, new Integer(1)));
		spinner.setBounds(10, 129, 66, 20);
		contentPanel.add(spinner);
		
		final JCheckBox doCleanUp = new JCheckBox("Clean up?");
		doCleanUp.setBounds(105,129, 90,20);
		contentPanel.add(doCleanUp);
		
		JButton loadButton = new JButton("Load Image");
		loadButton.setBounds(10, 165, 100, 23);
		contentPanel.add(loadButton);
		loadButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.loadImage(imageFile, (int)spinner.getValue(),(String)comboBox.getSelectedItem(),doCleanUp.isSelected());	
				((JDialog)contentPanel.getParent().getParent().getParent().getParent()).dispose();			
			}
			
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				((JDialog)contentPanel.getParent().getParent().getParent().getParent()).dispose();			
			}
			
		});
		btnCancel.setBounds(120, 165, 74, 23);
		contentPanel.add(btnCancel);
	}
}
