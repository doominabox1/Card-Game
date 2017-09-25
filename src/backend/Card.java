package backend;

import java.awt.image.BufferedImage;

public class Card {
	int cardNumber;
	public Card(int cardNumber){
		this.cardNumber = cardNumber;
	}
	
	public String getSuit(){	// Diamond, club, heart, spade
		// TODO
		return "";
	}
	
	public int getSuitNumber(){	// Number of suit
		// TODO
		return 0;
	}
	
	public String getFaceNumber(){	// 2-10, J, Q, K, A
		// TODO
		return "";
	}
	
	public int getValue(){ // 2 to 14
		// TODO
		return 0;
	}
	
	public BufferedImage getCardFace(){
		// TODO
		return null;
	}
}
