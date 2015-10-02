package com.jt.beads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BeadInfo extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public BeadInfo(final Editor frame, Color[][] beads) {

		Color backgroundColour = frame.getContentPane().getBackground();
		Color textColour = frame.getContentPane().getForeground();

		contentPanel.setBackground(backgroundColour);
		contentPanel.setForeground(textColour);
		setBackground(backgroundColour);
		setForeground(textColour);

		setTitle("Image Information");
		setBounds(100, 100, 308, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JTextArea txtBeadInfo = new JTextArea();
		txtBeadInfo.setEditable(false);
		txtBeadInfo.setBackground(backgroundColour);
		txtBeadInfo.setForeground(textColour);
		txtBeadInfo.setBounds(10, 11, 272, 206);
		txtBeadInfo.setText(beadsInfoString(beads));

		contentPanel.add(txtBeadInfo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(backgroundColour);
			buttonPane.setForeground(textColour);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						((JDialog)contentPanel.getParent().getParent().getParent().getParent()).dispose();	
					}
				});
				okButton.setActionCommand("OK");
				okButton.setBackground(backgroundColour);
				okButton.setForeground(textColour);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private String beadsInfoString(Color[][] beads){
		String info = "Amount\t\tBead Name";
		info += "\n----------------------------------------\n";
		String[] coloursName = new String[BeadColours.colorArray.length];
		int[] colourCount = new int[BeadColours.colorArray.length];
		for(int i = 0; i < beads[0].length; ++i){
			for(int j = 0; j < beads.length; ++i){

				for(int k = 0; k < BeadColours.colorArray.length; ++k){
					if(beads[j][i] != null && beads[i][j].equals(BeadColours.colorArray[k])){
						++colourCount[k];
						coloursName[k] = BeadColours.getNameWithColour(beads[i][j]);
						break;
					} 
				}
			}
		}

		for(int i = 0; i < colourCount.length; ++i){
			if(colourCount[i] != 0){
				info+= colourCount[i] + "\t\t" + coloursName[i];
			}
		}



		return info;
	}
}
