package gui;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import backend.Card;
import backend.Player;

public class ClientGUITester {
	static Random rand = new Random();
	public static void main(String[] args){
		Player p1 = new Player(1, null);
		p1.hand = generateHand(10);
		p1.turn = 0;
		p1.playedCards = new Card[]{new Card(11), new Card(22), new Card(33)};
		p1.score = new int[][]{{3,15},{6,17},{4,18}};
		p1.serverStatus = 2;
		
		JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setSize(1280, 720);
		
		mainFrame.add(new ClientPanel(p1));
		
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public static ArrayList<Card> generateHand(int cards){
		ArrayList<Card> hand = new ArrayList<Card>();
		for(int i = 0; i < cards; i++){
			hand.add(new Card(rand.nextInt(51)));
		}
		return hand;
	}
}
