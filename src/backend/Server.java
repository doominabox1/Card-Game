package backend;

import gui.ServerPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
	ServerPanel panel;
	
	int port;
	private static HashSet<Player> players = new HashSet<Player>();
	
	public Server(int port) throws IOException{
		this.port = port;
		panel = new ServerPanel();
		
		new Thread(){
			public void run(){
				try{
					ServerSocket listener = new ServerSocket(port);
					try {
						while (true) {
							new Handler(listener.accept()).start();
						}
					} finally {
						listener.close();
					}
				}catch(IOException e){
					
				}
			}
		}.start();
	}
	
	private static class Handler extends Thread {
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		/**
		 * Constructs a handler thread, squirreling away the socket.
		 * All the interesting work is done in the run method.
		 */
		public Handler(Socket socket) {
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
				for (Player player : players) {
					player.writer.println(newPlayer + " has connected.");
				}

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
			}
		}
	}
	
	public ServerPanel getPanel(){
		return panel;
	}
}
