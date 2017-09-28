package backend;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Player {
	public PrintWriter writer;

	//	Game Data
	public ArrayList<Card> hand = new ArrayList<Card>();
	public int turn;	// Who's turn it is
	public Card[] playedCards;	// Cards on table
	public int[][] score; // score[PLAYER_ONE][TRICKS] score[PLAYER_TWO][CARD_TOTAL] for example
	public int serverStatus = 0; // Players connected, 3 means the game can start
	public int playerNumber;	// What player they are
	//	Game Data
	
	public Player(int playerNumber, PrintWriter writer){
		this.playerNumber = playerNumber;
		this.writer = writer;
	}
	
	@SuppressWarnings("unchecked")
	public String getPlayerPacket(){	// Used by the server to send user data to client
		JSONObject container = new JSONObject();
		container.put("turn", turn);	// Who's turn it is
		container.put("playerNumber", playerNumber);	// Player number (for verification)
		container.put("serverStatus", serverStatus);	// Server status (0, 1, or 2)
		container.put("hand", getHandString());	// Player's hand
		
		container.put("playedCards", playedCards);	// Cards on the table
		container.put("score", score);	// 3x2 array of scores
		
		return container.toJSONString();
	}
	private String getHandString(){
		String output = "";
		for(Card c : hand){
			output += c.pos + " ";
		}
		return output.trim();
	}
	
	public void updatePlayerData(String data) throws ParseException{	// Used by the client to update player info
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(data);
        JSONObject jsonData = (JSONObject)obj;
        
        turn = (int) jsonData.get("turn");
        playerNumber = (int) jsonData.get("playerNumber");
        serverStatus = (int) jsonData.get("serverStatus");
        
        parseHandString((String)jsonData.get("hand"));
        
		// TODO container.put("playedCards", playedCards);	// Cards on the table
		// TODO container.put("score", score);	// 3x2 array of scores
	}
	private void parseHandString(String inputHand){
		String[] input = inputHand.split(" ");
		hand = new ArrayList<Card>();
		for(String s : input){
			hand.add(new Card(Integer.parseInt(s)));
		}
	}
	
	
	public String toString(){
		return "Player " + playerNumber;
	}
}
