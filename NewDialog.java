package com.jt.beads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

public class NewDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtImageName;
	private BufferedImage imageFile;
	private Editor frame;

	
	
	/**
	 * Create the dialog.
	 */
	public NewDialog(final Editor frame) {
		setTitle("New Image");
		setBounds(100, 100, 178, 228);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		Color backgroundColour = frame.getContentPane().getBackground();
		Color textColour = frame.getContentPane().getForeground();
		
		contentPanel.setBackground(backgroundColour);
		contentPanel.setForeground(textColour);
		setBackground(backgroundColour);
		setForeground(textColour);
		
		JLabel lblPixelsWidth = new JLabel("Pixels in Width");
		lblPixelsWidth.setBounds(10, 11, 91, 20);
		lblPixelsWidth.setBackground(backgroundColour);
		lblPixelsWidth.setForeground(textColour);
		
		contentPanel.add(lblPixelsWidth);
		
		JLabel lblPixelsHeight = new JLabel("Pixels in Height");
		lblPixelsHeight.setBounds(10, 73, 91, 21);
		lblPixelsHeight.setBackground(backgroundColour);
		lblPixelsHeight.setForeground(textColour);
		contentPanel.add(lblPixelsHeight);
		
		final JSpinner spnrWidth = new JSpinner();
		spnrWidth.setBounds(10, 42, 68, 20);
		spnrWidth.setBackground(backgroundColour);
		spnrWidth.setForeground(textColour);
		spnrWidth.setModel(new SpinnerNumberModel(new Integer(100), new Integer(2), null, new Integer(1)));
		contentPanel.add(spnrWidth);
		
		final JSpinner spnrHeight = new JSpinner();
		spnrHeight.setBounds(10, 105, 68, 20);
		spnrHeight.setBackground(backgroundColour);
		spnrHeight.setForeground(textColour);
		spnrHeight.setModel(new SpinnerNumberModel(new Integer(100), new Integer(2), null, new Integer(1)));
		contentPanel.add(spnrHeight);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(backgroundColour);
			buttonPane.setForeground(textColour);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCreate = new JButton("Create");
				btnCreate.setBackground(backgroundColour);
				btnCreate.setForeground(textColour);
				btnCreate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int dummySize = 3;
						int width = (int)spnrWidth.getValue() * dummySize;
						int height = (int)spnrHeight.getValue() * dummySize;
						
						imageFile = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						Graphics2D g = imageFile.createGraphics();
						g.setColor( Color.WHITE);
						g.fillRect(0, 0, width, height);
						g.dispose();
						frame.loadImage(imageFile, dummySize,"Hama Midi",false);	
						((JDialog)contentPanel.getParent().getParent().getParent().getParent()).dispose();
					}
				});
				btnCreate.setActionCommand("OK");
				buttonPane.add(btnCreate);
				getRootPane().setDefaultButton(btnCreate);
			}
		
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setBackground(backgroundColour);
				btnCancel.setForeground(textColour);
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						((JDialog)contentPanel.getParent().getParent().getParent().getParent()).dispose();						
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}	
}
