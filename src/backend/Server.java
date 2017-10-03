package backend;

import gui.ServerPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
	ServerPanel panel;
	int port;
	Server thisServer;
	int turn = -1;
	String curMessage = null;
	public int[][] score = new int[][] {{0,0},{0,0},{0,0}}; // score[PLAYER_ONE][TRICKS] score[PLAYER_TWO][CARD_TOTAL] for example
	public Card[] playedCards = new Card[3];	// Cards on table
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	public Server(int port) throws IOException{
		this.port = port;
		panel = new ServerPanel();
		thisServer = this;
		Random rand = new Random();
		
		new Thread(){
			public void run(){
				try{
					ServerSocket listener = new ServerSocket(port);
					try {
						while (players.size() < 3) {
							listener.setSoTimeout(1000);
							try{
								new Handler(listener.accept(), thisServer).start();
							}catch(IOException e){}
						}
						broadcastMessage("All connected");
						System.out.println("All connected");
						
						ArrayList<Card> deck = new ArrayList<Card>();	// New deck
						for(int i = 0; i < 52; i++){	// Init the deck with all possible cards
							deck.add(new Card(i));
						}
						
						while(deck.size() > 1){	// This simulates passing random cards to each of the players in order 0>1>2>0>1>2>0>1>2 etc
							for(Player player : players){
								int cardPos = rand.nextInt(deck.size());	// Get the pos of a random card in the deck
								player.hand.add(deck.get(cardPos));	// Add that card to the player hand
								deck.remove(cardPos);	// Remove that card from the deck
							}
						}
						int leader = 0;
						int leadingSuit = 0;
						turn = 0;// Set the initial turn state away from -1, which tells the client that game has begun
						
						updateClients();	// The game has started and player 1 can make their move
						
						while(true){	// If there is ever an inconsistency or error, we just wait for a new message
							// Busy wait for a message from client
							// The message should look like "0:::34" or in other words "player:::cardNum"
							while(curMessage == null){try {Thread.sleep(100);} catch (InterruptedException e) {}}
							String inputMessage = curMessage; // Copy the message and set the client message to null
							curMessage = null;
							
							String[] parts = inputMessage.split(":::");
							
							if(!parts[0].equals(turn+"")){continue;}	// If the message was from the wrong player, error
							
							Card chosenCard = new Card(Integer.parseInt(parts[1]));	// Card number sent in the message
							
							if(!players.get(turn).hasCard(chosenCard.pos)){continue;}	// If the player does not have the card they chose, error
							
							if(turn == leader){
								leadingSuit = chosenCard.getSuit();
							}else if(chosenCard.getSuit() != leadingSuit && players.get(turn).hasSuit(leadingSuit)){
								// If the player is not the leader, did not play a leading suit, and has a leading suit, error
								continue;
							}
							
							
							
						}
						
					} finally {
						listener.close();
					}
				}catch(IOException e){
					
				}
			}
		}.start();
	}
	
	private void broadcastMessage(String message){
		for(Player player : players){
			player.writer.println(message);
		}
	}
	private void updateClients(){
		for(int i = 0; i < players.size(); i++){
			players.get(i).serverStatus = players.size();
			players.get(i).turn = turn;
			players.get(i).playerNumber = i;
			players.get(i).score = score;
			players.get(i).playedCards = playedCards;
		}
		for(Player player : players){
			player.writer.println(player.getPlayerPacket());
		}
	}
	
	private static class Handler extends Thread {
		private Server server;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		/**
		 * Constructs a handler thread, squirreling away the socket.
		 * All the interesting work is done in the run method.
		 */
		public Handler(Socket socket, Server server) {
			this.server = server;
			this.socket = socket;
		}

		public void run() {
			try {

				// Create character streams for the socket.
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				// Add new player
				Player newPlayer; 
				if(players.size() < 3){
					newPlayer = new Player(players.size(), out);
					out.println("NEW_PLAYER " + newPlayer.playerNumber);
				}else{
					out.println("ERROR_PLAYER " + players.size());
					return;
				}

				
				// Add new player to list
				players.add(newPlayer);
				server.panel.setPlayersConnected(players.size());
				
				server.updateClients();

				//	Listen loop
				while (true) {
					String message = in.readLine();
					if (message == null) {
						return;
					}
					
					server.curMessage = newPlayer.playerNumber + ":::" + message;
				}
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				// When the client exits
				if (out != null) {
					Player playerToRemove = null;
					for(Player player : players){
						if(player.writer == out){
							playerToRemove = player;
							break;
						}
					}
					players.remove(playerToRemove);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
				server.panel.setPlayersConnected(players.size());
				server.updateClients();
			}
		}
	}
	
	public ServerPanel getPanel(){
		return panel;
	}
}
