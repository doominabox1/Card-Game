package gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import backend.Player;

@SuppressWarnings("serial")
public class ClientPanel extends JComponent{
	Player p;
	public ClientPanel(Player p){
		setPreferredSize(new Dimension(640, 360));	// Set the size of the client panel
		this.p = p;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(0, 0, 100, 100);
		
	}
}
