package backend;

import gui.ClientPanel;
import gui.ServerPanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Driver {
	
	static JFrame mainFrame;
	
	public static void main(String[] args){
		
		mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setSize(1280, 720);
		
		
		Object[] options = { "Server", "Client", "Quit" };
		JPanel panel = new JPanel();
		panel.add(new JLabel("Would you like this to be a server or a client?"));

		int result = JOptionPane.showOptionDialog(null, panel, "Choose a type.", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
		if(result == JOptionPane.YES_OPTION){
			server();
		}else if(result == JOptionPane.NO_OPTION){
			client();
		}else{
			System.exit(0);
		}
	}
	public static void server(){
		System.out.println("Server");
		
		ServerPanel serverPanel = new ServerPanel();
		mainFrame.add(serverPanel);
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	public static void client(){
		System.out.println("Client");
		
		ClientPanel clientPanel = new ClientPanel();
		mainFrame.add(clientPanel);
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
}