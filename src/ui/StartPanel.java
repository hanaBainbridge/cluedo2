package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
	private JLabel title = new JLabel("Welcome to Cluedo");
	private JButton startBtn = new JButton("Start Game!");
	private boolean startGame = false;
	
	public StartPanel() {
		this.setLayout(new BorderLayout()); // Sets the layout to Boarder
		this.setPreferredSize(new Dimension(600,600));
		setFontProperties(title);
		this.setBackground(Color.WHITE);
		this.add(title, BorderLayout.NORTH);
		this.add(startBtn, BorderLayout.SOUTH);
		
	}
	
	private void setFontProperties(JLabel text) {
		title.setFont(new Font("Arial", Font.BOLD, 32));
		title.setForeground(Color.BLACK);	
	}

	public static void main(String[] args) {new StartPanel();}

}
