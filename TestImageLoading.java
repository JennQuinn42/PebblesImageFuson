package com.jt.beads;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class TestImageLoading extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private FileLoader fl = new FileLoader();
	private JLabel lblNewLabel;
	private BufferedImage image;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestImageLoading frame = new TestImageLoading();
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
	public TestImageLoading() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon pickedImage = null;

				image = fl.read();
				if(image != null){
					if(image.getHeight() < lblNewLabel.getHeight() && image.getWidth() < lblNewLabel.getWidth()){
						pickedImage = new ImageIcon(image);
					}else{
						pickedImage = new ImageIcon(image.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH));
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
		btnOpen.setBounds(335, 228, 89, 23);
		contentPane.add(btnOpen);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 11, 414, 206);
		contentPane.add(lblNewLabel);
	}
}
