package gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class ClientPanel extends JComponent{
	public ClientPanel(){
		setPreferredSize(new Dimension(640, 360));	// Set the size of the client panel
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(0, 0, 100, 100);
		
	}
}
