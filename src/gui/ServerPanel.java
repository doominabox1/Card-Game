package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ServerPanel extends JPanel{
	JLabel externalIPLabel = new JLabel("Default");
	JLabel internalIPLabel = new JLabel("Default");
	JLabel usersLabel = new JLabel("Default");
	
	public ServerPanel() throws UnknownHostException{
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	// Set the panel to a vertical layout
		setPreferredSize(new Dimension(640, 360));	// Set the size of the server panel
		
		externalIPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// Center align the labels
		internalIPLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		usersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		String externalIP = getIP();	// Get external IP
		if(externalIP != null){	// If it is null then the there is a network problem
			externalIPLabel.setText("For external players connect to " + getIP());	// Tell the user what IP to connect to
		}else{
			externalIPLabel.setText("Please check network connection.");	// Give error
		}
		internalIPLabel.setText("For internal players connect to " + (getLocalHostLANAddress()+"").replace("/",""));	// Tell the user what IP to connect to
		
		usersLabel.setText("0/3 connected.");
		
		externalIPLabel.setFont(new Font("Serif", Font.PLAIN, 30));	// Set font size
		internalIPLabel.setFont(new Font("Serif", Font.PLAIN, 30));	// Set font size
		usersLabel.setFont(new Font("Serif", Font.PLAIN, 26));
		
		add( Box.createVerticalGlue() );	// Add the labels to the panel and align them horizontally (The glue objects take up space)
		add(externalIPLabel);
		add(internalIPLabel);
		add(usersLabel);
		add( Box.createVerticalGlue() );
	}
	
	/**
	 * Sets the number of connected players to display to the user.
	 * 
	 * @param players Number of players. 
	 */
	public void setPlayersConnected(int players){
		usersLabel.setText(players + "/3 connected.");
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
	
	@SuppressWarnings("rawtypes")
	private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
	    try {
	        InetAddress candidateAddress = null;
	        for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
	            NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
	            for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
	                InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
	                if (!inetAddr.isLoopbackAddress()) {
	                    if (inetAddr.isSiteLocalAddress()) {
	                        return inetAddr;
	                    }
	                    else if (candidateAddress == null) {
	                        candidateAddress = inetAddr;
	                    }
	                }
	            }
	        }
	        if (candidateAddress != null) {
	            return candidateAddress;
	        }
	        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
	        if (jdkSuppliedAddress == null) {
	            throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
	        }
	        return jdkSuppliedAddress;
	    }
	    catch (Exception e) {
	        UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
	        unknownHostException.initCause(e);
	        throw unknownHostException;
	    }
	}
}

