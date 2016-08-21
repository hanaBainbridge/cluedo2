package ui;

import game_elements.Board;
import game_elements.Player;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class BoardPanel extends JPanel {
	private GridBagConstraints gbc = new GridBagConstraints(); // Defines properties about component spacing.
	private JPanel actionsPanel = new JPanel(); // The action buttons will be stored here.
	private JButton suggestionBtn = new JButton("Make suggestion");
	private JButton accusationBtn = new JButton("Make Accusation");
	private JButton rollBtn = new JButton("Roll Dice");
	private JButton endTurn = new JButton("End Turn");
	
	private boolean roll = false;
	private JLabel dice1 = null;
	private JLabel dice2 = null;
	private int[] rolledNums = null;
	
	private JPanel cardsPanel = new JPanel(); // This panel shows the player's cards.
	private JLabel playerName = null;
	private Insets insets = new Insets(0, 0, 0, 0); // Change later to a spacing more appropreite.
	private int playerNum;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Object> characters = new ArrayList<Object>();
	private ImageIcon boardImage = new ImageIcon("board.png");
	// lists to store player and dice images and the players current corrants
	private ArrayList<ImageIcon> playerIcons= new ArrayList<ImageIcon>();
	private ArrayList<ImageIcon> diceIcons = new ArrayList<ImageIcon>();
	private ArrayList<Point> playersCoor= new ArrayList<Point>();
	private Board board;
	//felilds to create solution for accuation and suggestion
	private ArrayList<String> roomNames = new ArrayList<String>();
	private ArrayList<String> weaponNames = new ArrayList<String>();
	private ArrayList<String> characterNames = new ArrayList<String>();


	/**
	 * The constructor for the Board panel that set up the GUI for the actual
	 * game.
	 * 
	 * @param num,
	 *            the number of players.
	 */
	public BoardPanel(int num) {
		intaliseArrays();
		characters.addAll(Arrays.asList("Miss Scarlet", "Professor Plum", "Colonel Mustard", "Mrs White",
				"Reverend Green", "Mrs Peacock"));
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
		this.setVisible(true);
		
		diceIcons.add(new ImageIcon("d1.png"));
		diceIcons.add(new ImageIcon("d2.png"));
		diceIcons.add(new ImageIcon("d3.png"));
		diceIcons.add(new ImageIcon("d4.png"));
		diceIcons.add(new ImageIcon("d5.png"));
		diceIcons.add(new ImageIcon("d6.png"));

		this.add(cardsPanel, gbc);
		gbc.gridx++;
		this.add(actionsPanel, gbc);

		playerNum = num;
        
		// Creates the players.
		for (int i = 0; i < playerNum; i++) {
			players.add(createNewPlayer(i + 1));
		}
		
		board = new Board(players);
		playerName = new JLabel("Current Player: " + board.getCurrentPlayer().toString());
		cardsPanel.setPreferredSize(new Dimension(500, 210));
		cardsPanel.setBackground(Color.GRAY);
		cardsPanel.add(playerName);
		actionsPanel.setPreferredSize(new Dimension(100, 210));
		actionsPanel.setBackground(Color.GRAY);
		actionsPanel.add(suggestionBtn);
		// Button listener for suggestion goes here.
		actionsPanel.add(accusationBtn);
		accusationBtn.addActionListener((ActionEvent e) -> {
			String roomA= getRoomValue("accusation");
			String weaponA= getWeaponValue("accusation");
			String characterA= getCharacterValue("accusation");
			

		});
		
		// Button listener for accusation goes here.
		actionsPanel.add(rollBtn);
		rollBtn.addActionListener((ActionEvent e) -> {
			rolledNums = board.getCurrentPlayer().rollDice();
			roll = true;
			this.repaint();
		});
		actionsPanel.add(endTurn);
		endTurn.addActionListener((ActionEvent e) -> {
			board.nextPlayer(); // Move to the next player.
			cardsPanel.remove(playerName);
			playerName = new JLabel("Current Player: " + board.getCurrentPlayer().toString());
			cardsPanel.add(playerName);
			this.repaint();
		});
		
		
		// mouselistener for moving the plaeyrs 
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// System.out.println("a");
			}

			public void mouseReleased(MouseEvent e) {
				// System.out.println("b");
				Player currentPlayer = board.getCurrentPlayer();
				System.out.println("outer mouse");
				if (currentPlayer != null) {
					int index = playerIcons.indexOf(currentPlayer.getPlayerImage());
					// check vaild move
					System.out.println("inner mouse");
					if (isVaildMove(e.getPoint())) {
						System.out.println("validmovwe");
						playersCoor.get(index).setLocation(e.getPoint());
						repaint();
					}
				}
			}

			private boolean isVaildMove(Point point) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}
	
	private String getCharacterValue(String string) {
		String var= null;
		JPanel panel = new JPanel();
	      JRadioButton scarrlett = new JRadioButton("Miss Scarlett");
	      JRadioButton CM = new JRadioButton("Colonel Mustard");
	      JRadioButton white = new JRadioButton("Mrs White");
	      JRadioButton green = new JRadioButton("Reverend Green");
	      JRadioButton peacock = new JRadioButton("Mrs Peacock");
	      JRadioButton plum = new JRadioButton("Professor Plum");
	        panel.add(scarrlett);
	        panel.add(CM);
	        panel.add(white);
	        panel.add(green);
	        panel.add(peacock);
	        panel.add(plum);
	        
	        JOptionPane.showMessageDialog(null, panel);
	        if(scarrlett.isSelected()){
	        	var="Miss Scarlett";
	        }
	        if(CM.isSelected()){
	        	var="Colonel Mustard";
	        }
	        if(white.isSelected()){
	        	var="Mrs White";
	        }
	        if(green.isSelected()){
	        	var="Reverend Green";
	        }
	        if(peacock.isSelected()){
	        	var="Mrs Peacock";
	        }
	        if(plum.isSelected()){
	        	var="Professor Plum";
	        }
	        return var;
	}
	private String getWeaponValue(String string) {
		String var= null;
		//weapon names
		weaponNames.add("");
		weaponNames.add("");
		weaponNames.add("");
		weaponNames.add("");
		weaponNames.add("");
		weaponNames.add("");
		
		JPanel panel = new JPanel();
	      JRadioButton candle = new JRadioButton("Candle Stick");
	      JRadioButton dagger = new JRadioButton("Dagger");
	      JRadioButton lead = new JRadioButton("Lead Pipe");
	      JRadioButton revolver = new JRadioButton("Revolver");
	      JRadioButton rope = new JRadioButton("Rope");
	      JRadioButton spanner = new JRadioButton("Spanner");

	      
	        panel.add(candle);
	        panel.add(dagger);
	        panel.add(lead);
	        panel.add(revolver);
	        panel.add(rope);
	        panel.add(spanner);

	        JOptionPane.showMessageDialog(null, panel);
	        if(candle.isSelected()){
	        	var="Candle Stick";
	        }
	        if(dagger.isSelected()){
	        	var="Dagger";
	        }
	        if(lead.isSelected()){
	        	var="Lead Pipe";
	        }
	        if(revolver.isSelected()){
	        	var="Revolver";
	        }
	        if(rope.isSelected()){
	        	var="Rope";
	        }
	        if(spanner.isSelected()){
	        	var="Spanner";
	        }
	                return var;
	}
	private String getRoomValue(String s) {
		String ans = null;
		JPanel panel = new JPanel();
		JRadioButton ballRoom = new JRadioButton("Ball Room");
		JRadioButton con = new JRadioButton("Conservatory");
		JRadioButton billard = new JRadioButton("Billiard Room");
		JRadioButton lib = new JRadioButton("Library");
		JRadioButton study = new JRadioButton("Study");
		JRadioButton hall = new JRadioButton("Hall");
		JRadioButton kitchen = new JRadioButton("Kitchen");
		JRadioButton dinning = new JRadioButton("Dinning Room");
		JRadioButton lounge = new JRadioButton("Lounge");
	
		panel.add(ballRoom);
		panel.add(billard);
		panel.add(lib);
		panel.add(study);
		panel.add(hall);
		panel.add(kitchen);
		panel.add(dinning);
		panel.add(lounge);
		JOptionPane.showMessageDialog(null, panel);

		if (ballRoom.isSelected()) {
			ans = "Ball Room";
		}
		if (billard.isSelected()) {
			ans = "Billard Room";
		}
		if (lib.isSelected()) {
			ans = "libaray";
		}
		if (hall.isSelected()) {
			ans = "hall";
		}
		if (dinning.isSelected()) {
			ans = "dinning hall";
		}
		if (lounge.isSelected()) {
			ans = "lounge";
		}
		if (study.isSelected()) {
			ans = "study";
		}
		if (kitchen.isSelected()) {
			ans = "kitchen";
		}
		return ans;

	}

    


	private Player createNewPlayer(int index) {
		try {
			String name = (String) JOptionPane.showInputDialog(null, "Please a name for Player " + (index));
			while(name.equals("")) {
				JOptionPane.showMessageDialog(null, "No name entered", "Invaid input", JOptionPane.ERROR_MESSAGE);
				name = (String) JOptionPane.showInputDialog(null, "Please a name for Player " + (index));
			}
			String character = (String) JOptionPane.showInputDialog(null,
					"Please chose a character for player " + index, "Character selection",
					JOptionPane.INFORMATION_MESSAGE, null, characters.toArray(), characters.get(0));
			characters.remove(character);
			return new Player(playerIcons.get(index - 1), playersCoor.get(index - 1), character, name);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Game will now crash you have not entered any information for a field",
					"Fatal error!", JOptionPane.ERROR_MESSAGE);
			throw new Error();
		}
	}
	/**
	 * p
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		actionsPanel.repaint();
		g.setColor(Color.GRAY);
		g.fillRect(0, 400, 650, 250);
		boardImage.paintIcon(this, g, 10, 10);
		paintPlayers(g);
		paintDice(g);
	}
	
	private void paintDice(Graphics g) {
		if (roll) {
			//System.out.println("F");
			if(dice1 != null) {actionsPanel.remove(dice1);}
			if(dice2 != null) {actionsPanel.remove(dice2);}
			dice1 = new JLabel(diceIcons.get(rolledNums[0]-1));
			dice2 = new JLabel(diceIcons.get(rolledNums[1]-1));
			actionsPanel.add(dice1);
			actionsPanel.add(dice2);
			this.revalidate();
			roll = false;
		}
	}
	
	private void paintPlayers(Graphics g) {
		
		for(int i=0; i<playerNum; i++){
			playerIcons.get(i).paintIcon(this, g, (int)playersCoor.get(i).getX(),(int)playersCoor.get(i).getY());
		}		
		
		this.revalidate();
	}
	
	public static void main(String[] args) {
		new BoardPanel(3);
	}
 //=================================================
 //============HELPER METHODS=======================
 //=================================================
 
/**
 * used to aid in code readiblity 
 * */	
  public void intaliseArrays(){
	  	
	    // player icons
		playerIcons.add(new ImageIcon("p1.png"));
		playerIcons.add(new ImageIcon("p2.jpg"));
		playerIcons.add(new ImageIcon("p3.jpg"));
		playerIcons.add(new ImageIcon("p4.jpg"));
		playerIcons.add(new ImageIcon("p5.jpg"));
		playerIcons.add(new ImageIcon("p6.jpg"));
		
		//player corrants
		playersCoor.add(new Point(225,5));
		playersCoor.add(new Point(365,5));
		playersCoor.add(new Point(575,95));
		playersCoor.add(new Point(575,305));
		playersCoor.add(new Point(175,380));
		playersCoor.add(new Point(11,270));
		
		
				
  }
}
