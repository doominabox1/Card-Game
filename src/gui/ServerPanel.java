package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ServerPanel extends JPanel{
	JLabel ipLabel = new JLabel("Default");
	JLabel usersLabel = new JLabel("Default");
	
	public ServerPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(640, 360));
		ipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		usersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		ipLabel.setText("Please connect to 192.168.1.1");
		usersLabel.setText("2/3 connected.");
		ipLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		usersLabel.setFont(new Font("Serif", Font.PLAIN, 26));
		
		add( Box.createVerticalGlue() );
		add(ipLabel);
		add(usersLabel);
		add( Box.createVerticalGlue() );
		
	}
}

