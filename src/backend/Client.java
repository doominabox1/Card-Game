package backend;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import gui.ClientPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
>>>>>>> 826807f565224e5ff82c2aae48b7a3f1ba89aaae

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
