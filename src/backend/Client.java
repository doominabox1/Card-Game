package backend;

import gui.ClientPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	
	ClientPanel panel;
	public Player player;
	
	int port;
	BufferedReader in;
	PrintWriter out;
	
	public Client(int port) throws IOException{
		this.port = port;
		panel = new ClientPanel(this);
		listen();
	}
	
	/**
	 * Sends the user's choice of card on their turn.
	 * @param What card number (0-51)
	 */
	public void sendCard(int pos){
		out.println(player.hand.get(pos).cardNumber);
	}
	
	/**
	 * Makes a new thread to handle server listening. 
	 * @throws IOException
	 */
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
						if(line.contains("winner:::")){
							int winner = Integer.parseInt(line.split(":::")[1]);
							int response = -1;
							if(winner == player.playerNumber){
								response = JOptionPane.showConfirmDialog(null, "Congratulations, you win! Play again?", "Game Over.", JOptionPane.YES_NO_OPTION);
							}else{
								response = JOptionPane.showConfirmDialog(null, "Player " + (winner+1) + " wins! Play again?", "Game Over.", JOptionPane.YES_NO_OPTION);
							}
							if(response != JOptionPane.YES_OPTION){
								System.exit(0);
							}
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
	
	/**
	 * Ask's the user what server address to connect to
	 * @return The address if the user entered one. If it is blank, the address defaults to 'localhost' 
	 */
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
	
	/**
	 * @return The GUI that the server handles 
	 */
	public ClientPanel getPanel(){
		return panel;
	}
}
