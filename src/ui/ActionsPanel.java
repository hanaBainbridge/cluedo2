package ui;

import game_elements.Board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionsPanel extends JPanel {
	private JButton suggestionBtn = new JButton("Make suggestion");
	private JButton accusationBtn = new JButton("Make accusation");
	private JButton rollBtn = new JButton("Roll dice");
	private boolean rollPressed = false;
	private ArrayList<ImageIcon> diceIcons= new ArrayList<ImageIcon>();
	private int[] rolledNums;
	private Board board;
	
	public ActionsPanel(Board b) {
		super();
		board = b;
		setPreferredSize(new Dimension(100, 210));
		setBackground(Color.GRAY);
		add(suggestionBtn);
		// Suggestion button listener should go here.
		add(accusationBtn);
		// Accusation button listener should go here.
		add(rollBtn);
		//determines action when roll button is pressed;
		 rollBtn.addActionListener((ActionEvent e) -> {
			 rollPressed = true;
			 rolledNums = board.getCurrentPlayer().rollDice();
			 this.repaint();
			 }); 
		 
		//dice icons
		diceIcons.add(new ImageIcon("d1.png"));
		diceIcons.add(new ImageIcon("d2.png"));
		diceIcons.add(new ImageIcon("d3.png"));
		diceIcons.add(new ImageIcon("d4.png"));
	    diceIcons.add(new ImageIcon("d5.png"));
		diceIcons.add(new ImageIcon("d6.png"));
	}
	
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		paintDice(g);
	}
	
	private void paintDice(Graphics g) {
		if(rollPressed){
			diceIcons.get((rolledNums[0]-1)).paintIcon(this, g, 400, 500);
			diceIcons.get((rolledNums[1]-1)).paintIcon(this, g, 400, 500);
			rollPressed=false;
		}
	}
	
	public static void main(String[] args) {}

}
