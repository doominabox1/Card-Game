package backend;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	
	public static final int CARD_WIDTH = 72;
	public static final int CARD_HEIGHT = 96;
	int pos;
	String[] suits = {"Club", "Spade", "Heart", "Diamond"};
	BufferedImage face = null; 
	
	public Card(int pos) {
		this.pos = pos;
		
		try {
			BufferedImage sheet = ImageIO.read(new File("res\\cards.png"));
			face = sheet.getSubimage((getValue()-2) * CARD_WIDTH, getSuit() * CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public int getSuit() {
		return (pos/13);
	}
	public String getSuitString() {
		return suits[getSuit()];
	}
	public int getValue() {
		return (pos % 13 + 2);
	}
	public String getValString() {
		switch(pos % 13 + 2) {
		case 11: return "J";
		case 12: return "Q";
		case 13: return "K";
		case 14: return "A";
		default: return Integer.toString(pos % 13 + 2);
		}
	}
	@Override
	public boolean equals(Object o) {
	    if(o.getClass() != getClass()){
	    	return false;
	    }
	    return pos == ((Card)o).pos; 
	}


	public Image getFace() {
		return face;
	}
}
