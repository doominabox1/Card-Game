package backend;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Driver {
	
	public static final int PORT = 4321;
	static JFrame mainFrame;
	
	public static void main(String[] args) throws IOException{
		
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setSize(1280, 720);
		
		
		Object[] options = { "Server", "Client", "Quit" };
		JPanel panel = new JPanel();
		panel.add(new JLabel("Would you like this to be a server or a client?"));

		int result = JOptionPane.showOptionDialog(null, panel, "Choose a type.", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
		if(result == JOptionPane.YES_OPTION){
			server();
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setVisible(true);
		}else if(result == JOptionPane.NO_OPTION){
			client();
		}else{
			System.exit(0);
		}
	}
	
	/**
	 * If the user chose server, make a new server object and add it's panel to the the main panel
	 */
	public static void server() throws IOException{
		Server server = new Server(PORT);
		mainFrame.add(server.getPanel());
	}
	
	/**
	 * If the user chose client, make a new server object and add it's panel to the the main panel when it has contacted the server
	 */
	public static void client() throws IOException{
		new Client(PORT);
	}
	
	/**
	 * Once the client has contacted the server, show the client panel.
	 * 
	 * @param client The client object to show
	 */
	public static void showClient(Client client){
		mainFrame.add(client.getPanel());
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}
