package backend;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	
	public static final int CARD_WIDTH = 72;
	public static final int CARD_HEIGHT = 96;
	int cardNumber;
	BufferedImage face = null; 
	
	public Card(int pos) {
		this.cardNumber = pos;
		
		try {
			BufferedImage sheet = ImageIO.read(new File("res\\cards.png"));
			face = sheet.getSubimage((getValue()-2) * CARD_WIDTH, getSuit() * CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 *	@return The numerical representation of the card's suit, 0-3 
	 */
	public int getSuit() {
		return (cardNumber/13);
	}
	
	/**
	 *	@return The numerical value of the card for scorekeeping 
	 */
	public int getValue() {
		return (cardNumber % 13 + 2);
	}
	
	@Override
	public boolean equals(Object o) {
	    if(o.getClass() != getClass()){
	    	return false;
	    }
	    return cardNumber == ((Card)o).cardNumber; 
	}

	/**
	 *	@return The picture of the face of the card, created when 
	 */
	public Image getFace() {
		return face;
	}
}
