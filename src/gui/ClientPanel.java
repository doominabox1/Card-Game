package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import backend.Card;
import backend.Client;

@SuppressWarnings("serial")
public class ClientPanel extends JComponent{
	Client client;
	public ClientPanel(Client client){
		setPreferredSize(new Dimension(Card.CARD_WIDTH * 17 + PADDING * 16 + PADDING * 6, 500));	// Set the size of the client panel
		this.client = client;
		addMouseListener(new CPMouseListener());
	}
	
	private final static int PADDING = 10;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		
		int cardPos = (width / 2) - Card.CARD_WIDTH - Card.CARD_WIDTH/2 - PADDING*2;
		for(Card c : client.player.playedCards){
			if(c == null){
				g.setColor(new Color(100, 100, 100));
				g.fillRect(cardPos, height/2 - Card.CARD_HEIGHT, Card.CARD_WIDTH, Card.CARD_HEIGHT);
			}else{
				g.drawImage(c.getFace(), cardPos, height/2 - Card.CARD_HEIGHT, null);
			}
			cardPos += Card.CARD_WIDTH + PADDING;
		}
		
		cardPos = PADDING*3;
		if(client.player.hand.size() > 0){
			for(Card c : client.player.hand){
				g.drawImage(c.getFace(), cardPos, height - Card.CARD_HEIGHT - PADDING*3, null);
				cardPos += Card.CARD_WIDTH + PADDING;
			}
		}
	}
	public void updatePanel() {
		repaint();
	}
	
	static class CPMouseListener extends MouseAdapter {
		@Override 
		public void mouseReleased(MouseEvent e) {
			ClientPanel cp = (ClientPanel)e.getSource();
			System.out.println("Clicked");
			if(e.getButton() == MouseEvent.BUTTON1){
				System.out.println("Clicked2");
				int x = e.getX();
				int y = e.getY();
				if( y > cp.getHeight() - Card.CARD_HEIGHT - PADDING*3 && y < cp.getHeight() - PADDING*3){
					System.out.println("Clicked3");
					if( x > PADDING*3 && x < cp.getWidth() - PADDING*3){
						System.out.println("Clicked4");
						int clickPos = (x-(PADDING*3)) / (Card.CARD_WIDTH + PADDING);
						cp.client.sendCard(clickPos);
					}
				}
				
				cp.repaint();
			}
		}
	}
}
