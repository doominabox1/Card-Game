package backend;

import gui.ClientPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	public enum Players {
	    PLAYER_ONE, PLAYER_TWO, PLAYER_THREE
	}
	public enum ScoreType {
	    TRICKS, CARD_TOTAL
	}
	
	ClientPanel panel;
	public Player player;
	
	// Game data moved to player
	
	int port;
	BufferedReader in;
	PrintWriter out;
	
	public Client(int port) throws IOException{
		this.port = port;
		panel = new ClientPanel(this);
		listen();
	}
	
	public void sendCard(int pos){
		out.println(player.hand.get(pos).pos);
	}
	
	private synchronized void listen() throws IOException{
		
		// Make connection and initialize streams
		String serverAddress = getServerAddress();
		Socket socket = new Socket(serverAddress, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		String line = in.readLine();
		if(line.contains("NEW_PLAYER")){
			player = new Player(Integer.parseInt(line.split(" ")[1]), null);
			Driver.showClient(this);
		}else{
			System.out.println("Error reading new player number: " + line);
			System.exit(0);
		}
		
		new Thread(){

			public void run(){
				try{
					// Process all messages from server, according to the protocol.
					while (true) {
						String line = in.readLine();
						if(line == null){
							continue;
						}
						if(line.charAt(0) == '{'){
							player.updatePlayerData(line);
						}
						panel.repaint();
					}
				}catch(IOException e){
					
				}finally{
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private String getServerAddress() {
		String input = JOptionPane.showInputDialog(
						null,
						"Enter IP Address of the Server:",
						"Welcome to the card game",
						JOptionPane.QUESTION_MESSAGE);
		if(input == null){
			System.exit(0);
		}
		return input.length() == 0 ? "localhost" : input;
	}
	
	public ClientPanel getPanel(){
		return panel;
	}
}
