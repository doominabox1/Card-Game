package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import backend.Card;
import backend.Client;
import backend.Player;

@SuppressWarnings("serial")
public class ClientPanel extends JComponent{
	Client client;
	public ClientPanel(Client client){
		setPreferredSize(new Dimension(	Card.CARD_WIDTH*17 + SMALL_PADDING*16 + SMALL_PADDING * 4,
										Card.CARD_HEIGHT*3 + LARGE_PADDING*2));	// Set the size of the client panel
		this.client = client;
		CPMouseListener cpml = new CPMouseListener();
		addMouseListener(cpml);
		addMouseMotionListener(cpml);
	}
	
	private final static int LARGE_PADDING = 30;
	private final static int SMALL_PADDING = 10;
	private static int glowPos = -1;
	
	private static final Font FONT_NAMES = new Font("Helvetica", Font.BOLD, 16);
	private static final Font FONT_SERVER_MESSAGE = new Font("Helvetica", Font.BOLD, 24);
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		if(client.player.serverStatus != 3){	// If there is not enough players, draw nothing
			g.setFont(FONT_SERVER_MESSAGE);
			String message = "Waiting for " + (3-client.player.serverStatus) + " more player" + (client.player.serverStatus == 2 ? "." : "s.");
			int stringSize = g.getFontMetrics().stringWidth(message);
			g.drawString(message, width/2 - stringSize/2, height/2);
		}else{	// Draw the board
			// Draw cards in the river and the score board
			int cardPos = (width / 2) - Card.CARD_WIDTH - Card.CARD_WIDTH/2 - LARGE_PADDING;
			g.setColor(new Color(0, 0, 0));
			g.setFont(FONT_NAMES);
			g.drawString("Score:", SMALL_PADDING, SMALL_PADDING*2);
			for(int i = 0; i < client.player.playedCards.length; i++){
				if(client.player.turn == i){
					if(i == client.player.playerNumber){
						g.setColor(new Color(0, 255, 0));
					}else{
						g.setColor(new Color(255, 0, 0));
					}
					g.fillRect(cardPos - SMALL_PADDING/2, height/2 - Card.CARD_HEIGHT - SMALL_PADDING/2, Card.CARD_WIDTH + SMALL_PADDING, Card.CARD_HEIGHT + SMALL_PADDING);
				}
				if(client.player.playedCards[i] == null){
					g.setColor(new Color(100, 100, 100));
					g.fillRect(cardPos, height/2 - Card.CARD_HEIGHT, Card.CARD_WIDTH, Card.CARD_HEIGHT);
				}else{
					g.drawImage(client.player.playedCards[i].getFace(), cardPos, height/2 - Card.CARD_HEIGHT, null);
				}
				if(i == client.player.playerNumber){
					g.setColor(new Color(50, 200, 50));
				}else{
					g.setColor(new Color(0, 0, 0));
				}
				g.setFont(FONT_NAMES);
				g.drawString("Player " + (i+1), cardPos, height/2 - Card.CARD_HEIGHT - LARGE_PADDING);
				g.drawString("Player " + (i+1) + ": " + client.player.score[i][Player.TRICKS] + " (" + client.player.score[i][Player.CARD_TOTAL] + ")", SMALL_PADDING, SMALL_PADDING*2 + 20*(i+1));
				
				cardPos += Card.CARD_WIDTH + LARGE_PADDING;
			}
			
			// Draw cards in hand 
			Card[] playedCards = client.player.playedCards;
			int leadingCard = -1;
			if(playedCards[0] != null && playedCards[2] == null){
				leadingCard = 0;
			}else if(playedCards[0] == null && playedCards[1] != null){
				leadingCard = 1;
			}else if(playedCards[1] == null && playedCards[2] != null){
				leadingCard = 2;
			}
			boolean isMyTurn = client.player.turn == client.player.playerNumber;
			boolean shouldDither = leadingCard >= 0 && client.player.hasSuit(client.player.playedCards[leadingCard].getSuit());
			cardPos = SMALL_PADDING*2;
			if(client.player.hand.size() > 0){
				for(int i = 0; i < client.player.hand.size(); i++){
					boolean cardInvalid = !isMyTurn || (shouldDither && client.player.hand.get(i).getSuit() != client.player.playedCards[leadingCard].getSuit());
					if(!cardInvalid && glowPos >= 0 && glowPos == i){
						g.setColor(new Color(255, 0, 0));
						g.fillRect(cardPos - SMALL_PADDING/2, height - Card.CARD_HEIGHT - SMALL_PADDING*2 - SMALL_PADDING/2, Card.CARD_WIDTH + SMALL_PADDING, Card.CARD_HEIGHT + SMALL_PADDING);
					}
					g.drawImage(client.player.hand.get(i).getFace(), cardPos, height - Card.CARD_HEIGHT - SMALL_PADDING*2, null);
					
					if(cardInvalid){
						g.setColor(new Color(255, 255, 255, 200));
						g.fillRect(cardPos, height - Card.CARD_HEIGHT - SMALL_PADDING*2, Card.CARD_WIDTH, Card.CARD_HEIGHT);
					}
					cardPos += Card.CARD_WIDTH + SMALL_PADDING;
				}
			}
		}
	}
	
	/**
	 * Mouse listener to listen for user's clicks on the panel. Also gets mouse position data to highlight what card is selected. 
	 */
	static class CPMouseListener extends MouseAdapter {
		@Override 
		public void mouseClicked(MouseEvent e) {
			ClientPanel cp = (ClientPanel)e.getSource();
			if(e.getButton() == MouseEvent.BUTTON1){
				int x = e.getX();
				int y = e.getY();
				if( y > cp.getHeight() - Card.CARD_HEIGHT - SMALL_PADDING*2 && y < cp.getHeight() - SMALL_PADDING*2){
					if( x > SMALL_PADDING*2 && x < cp.getWidth() - SMALL_PADDING*2){
						int clickPos = (x-(SMALL_PADDING*2)) / (Card.CARD_WIDTH + SMALL_PADDING);
						if(clickPos < cp.client.player.hand.size()){
							cp.client.sendCard(clickPos);
							cp.repaint();
						}
					}
				}
			}
		}
		@Override 
		public void mouseMoved(MouseEvent e) {
			ClientPanel cp = (ClientPanel)e.getSource();
			int x = e.getX();
			int y = e.getY();
			if( y > cp.getHeight() - Card.CARD_HEIGHT - SMALL_PADDING*2 && y < cp.getHeight() - SMALL_PADDING*2){
				if( x > SMALL_PADDING*2 && x < cp.getWidth() - SMALL_PADDING*2){
					int clickPos = (x-(SMALL_PADDING*2)) / (Card.CARD_WIDTH + SMALL_PADDING);
					if(clickPos < cp.client.player.hand.size()){
						glowPos = clickPos;
					}else{
						glowPos = -1;
					}
				}else glowPos = -1;
			}else glowPos = -1;
			cp.repaint();
		}
	}
}
