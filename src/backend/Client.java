package backend;

import java.util.ArrayList;

import gui.ClientPanel;

public class Client {
	public enum Players {
	    PLAYER_ONE, PLAYER_TWO, PLAYER_THREE
	}
	public enum ScoreType {
	    TRICKS, CARD_TOTAL
	}
	
	ClientPanel panel;
	
	//	Game Data
	ArrayList<Card> hand = new ArrayList<Card>();
	int turn;	// Who's turn it is
	Card[] playedCards;	// Cards on table
	int[][] score; // score[PLAYER_ONE][TRICKS] score[PLAYER_TWO][CARD_TOTAL] for example
	int serverStatus = 0; // Players connected, 3 means the game can start
	int playerNumber;	// What player they are
	//	Game Data
	
	public Client(String port){
		panel = new ClientPanel(port);
	}
	public ClientPanel getPanel(){
		return panel;
	}
}
