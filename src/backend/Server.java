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
						turn = 0;// Set the initial turn state away from -1, which tells the client that game has begun
						
						updateClients();	// The game has started and player 1 can make their move
						
						while(true){
							// main loop
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
					
					System.out.println("Broadcasting: " + "Ping: " + message);
					// Broadcast to all players
					for (Player player : players) {
						player.writer.println("Ping : " + message);
					}
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
