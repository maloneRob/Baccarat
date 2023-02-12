

import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
	ArrayList<Card> deck;
	//generateDeck()
	//this method creates a standard deck of 52 card objects and puts them into an arraylist of card objects
	public void generateDeck() {
		deck = new ArrayList<Card>();
		ArrayList<String> suites = new ArrayList<>();
		suites.add("clubs");
		suites.add("spades");
		suites.add("hearts");
		suites.add("diamonds");
		for(String suite: suites) {
			for(int i = 1; i < 14; i++ ) {
				Card c = new Card(suite, i);
				deck.add(c);
			}
		}
		
	}
	//deal hand()
	//this method draws two card objects off the top of the deck and puts them into an arraylist of card objects which it then returns
	public ArrayList<Card> dealHand(){
		ArrayList<Card> hand = new ArrayList<>();
		Card c = drawOne();
		hand.add(c);
		c = drawOne();
		hand.add(c);
		return hand;
	}
	//drawOne()
	//this method draws a card from the top of the deck if the deck is not empty
	public Card drawOne() {
		if(!deck.isEmpty()) {
			Card c = deck.remove(0);
			
			return c;
		}
		return null;
	}
	//shuffleDeck()
	//this method creates a new deck with the generateDeck() method and then randomizes it
	public void shuffleDeck() {
		generateDeck();
		Collections.shuffle(deck);
	}
	//this method returns the number of cards left in the deck
	public int deckSize() {
		return this.deck.size();
	}
}
