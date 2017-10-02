package game_logic;

import java.util.*;

import backend.Card;

public class Game {
	Player p1 = new Player(1);
	Player p2 = new Player(2);
	Player p3 = new Player(3);
	ArrayList deck = new ArrayList<Card>();
	public Game() {
		int val = 0;
		for(int i = 0; i < 52; i++) {
			deck.add(new Card(i));
		}
	}
	public void printHand(Player p) {
		p.printHand();
	}
	public int tieBreaker() {return 0;}
	//distributes the cards evenly among the players w/o duplicates and ignores last card remaining. 
	public void deal() {
		int num = deck.size() / 3;
		for(int i = 0; i < num; i++) {
			p1.addCardToHand((Card) deck.remove(0));
			p2.addCardToHand((Card) deck.remove(0));
			p3.addCardToHand((Card) deck.remove(0));
		}
	}
	
	//Shuffles the deck using the Fisher-Yates shuffle algorithm
	public void shuffle() {
		for(int i = 51; i > 0; i--) {
			int j = (int)(Math.random() * i);
			Card temp = (Card) deck.get(i);
			deck.set(i, deck.get(j));
			deck.set(j, temp);
		}
	}
	//Prints the deck into the console
	public void printDeck() {
		for(int i = 0; i < 52; i++) {
			System.out.println(i + ": " + ((Card) deck.get(i)).getValString());
		}
	}
}
class Player {
	ArrayList hand = new ArrayList();
	static int playerNum;
	public Player(int playerNum) {
		this.playerNum = playerNum;
	}
	public boolean isTurn(){return false;}
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	public void printHand() {
		for(int i = 0; i < hand.size(); i++) {
			System.out.println(i + ": " + ((Card) hand.get(i)).getValString());
		}
	}
}