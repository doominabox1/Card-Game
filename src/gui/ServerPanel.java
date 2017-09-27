package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ServerPanel extends JPanel{
	JLabel ipLabel = new JLabel("Default");
	JLabel usersLabel = new JLabel("Default");
	
	public ServerPanel(){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	// Set the panel to a vertical layout
		setPreferredSize(new Dimension(640, 360));	// Set the size of the server panel
		
		ipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// Center align the labels 
		usersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		String ip = getIP();	// Get external IP
		if(ip != null){	// If it is null then the there is a network problem
			ipLabel.setText("Please connect to " + getIP());	// Tell the user what IP to connect to
		}else{
			ipLabel.setText("Please check network connection.");	// Give error
		}
		
		usersLabel.setText("2/3 connected.");	// TODO make this actually display the number of users
		
		ipLabel.setFont(new Font("Serif", Font.PLAIN, 30));	// Set font size
		usersLabel.setFont(new Font("Serif", Font.PLAIN, 26));
		
		add( Box.createVerticalGlue() );	// Add the labels to the panel and align them horizontally (The glue objects take up space)
		add(ipLabel);
		add(usersLabel);
		add( Box.createVerticalGlue() );
	}
	
	
	/**
	 * @return The external IP of the computer or null if there was an error.
	 */
	private String getIP(){
		try{
			URL url = new URL("http://checkip.amazonaws.com/");	// Amazon's checkip server
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream())); // Connect to the url and make a buffered reader to read the string
			return br.readLine();	// Read the ip from the website and return it
		} catch(IOException e){
			return null;	// If there is an error then return null
		}
	}
}

