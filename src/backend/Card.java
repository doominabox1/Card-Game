package backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	
	int pos;
	
	BufferedImage faces = null; 
	
	public void loadImage() {
		try {
			faces = ImageIO.read(new File("cards.jpg"));
		} catch (IOException e){
			
		}
	}
	
	public Card(int pos) {
		this.pos = pos;
	}
	
	public Card() {
		pos = 0;
	}
	
	public int getSuit() {
		return (pos/13);
	}
	public int getValue() {
		return (pos % 13 + 2);
	}
	
	public BufferedImage getCardImage() {
		return null;
	}
	
}
