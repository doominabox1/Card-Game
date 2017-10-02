package game_logic;

public class tester {
	public static void main(String[] args) {
		Game g = new Game();
		g.shuffle();
		System.out.println("Print Deck");
		g.printDeck();
		g.deal();
		//System.out.println(g.p1.hand.get(0));
		g.printHand(g.p1);
		g.printHand(g.p2);
		g.printHand(g.p3);
	}
}
