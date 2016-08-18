package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private GridBagConstraints gbc = new GridBagConstraints(); // Defines properties about component spacing.
	private JPanel actionsPanel = new JPanel(); // The action buttons will be stored here.
	private JPanel cardsPanel = new JPanel(); // This panel shows the player's cards.
	private JButton suggestionBtn = new JButton("Make suggestion");
	private JButton accusationBtn = new JButton("Make accusation");
	private JButton rollBtn = new JButton("Roll dice");
	private Insets insets = new Insets(0,0,0,0); // Change later to a spacing more appropreite.
	
	public BoardPanel() {
		gbc.insets = insets;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.WHITE);
		
		actionsPanel.setPreferredSize(new Dimension(100, 210));
		actionsPanel.setBackground(Color.GRAY);
		actionsPanel.add(suggestionBtn);
		actionsPanel.add(accusationBtn);
		actionsPanel.add(rollBtn);
		
		cardsPanel.setPreferredSize(new Dimension(500, 210));
		cardsPanel.setBackground(Color.GRAY);
		
		this.add(cardsPanel, gbc);
		gbc.gridx ++;
		this.add(actionsPanel, gbc);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 400, 650, 400);
		g.drawLine(500, 400, 500, 650);
		g.setColor(Color.GRAY);
		g.fillRect(0, 400, 650, 250);
	}
	

	public static void main(String[] args) {new BoardPanel();}

}
