package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	
	public BoardPanel() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.BLACK);
	}
	

	public static void main(String[] args) {new BoardPanel();}

}
