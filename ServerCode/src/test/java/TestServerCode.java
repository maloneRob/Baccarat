

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ServerCodeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void CardConstructtest() {
		Card c = new Card("clubs", 4);
		assertEquals("clubs", c.suite);
		assertEquals(4,c.value);
	}
	@Test
	void BaccaratDealerGenerateDeck() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		assertEquals(52,d.deckSize());
		int i, j = 1;
		for(i = 0; i < 13; i++) {
			Card c = d.deck.get(i);
			assertEquals("clubs",c.suite);
			assertEquals(j,c.value);
			j++;
		}
		j = 1;
		for(i = 13; i < 26; i++) {
			Card c = d.deck.get(i);
			assertEquals("spades",c.suite);
			assertEquals(j,c.value);
			j++;
		}
		j = 1;
		for(i = 26; i < 39; i++) {
			Card c = d.deck.get(i);
			assertEquals("hearts",c.suite);
			assertEquals(j,c.value);
			j++;
		}
		j = 1;
		for(i = 39; i < 52; i++) {
			Card c = d.deck.get(i);
			assertEquals("diamonds",c.suite);
			assertEquals(j,c.value);
			j++;
		}
	}
	@Test
	void BaccaratDealerGenerateDeck2() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		assertEquals(52,d.deckSize());
		int i, j = 1;
		for(i = 0; i < 13; i++) {
			Card c = d.deck.get(i);
			assertEquals(j,c.value);
			j++;
		}
		j = 1;
		for(i = 13; i < 26; i++) {
			Card c = d.deck.get(i);
			assertEquals(j,c.value);
			j++;
		}
		j = 1;
		for(i = 26; i < 39; i++) {
			Card c = d.deck.get(i);
			assertEquals(j,c.value);
			j++;
		}
		j = 1;
		for(i = 39; i < 52; i++) {
			Card c = d.deck.get(i);
			assertEquals(j,c.value);
			j++;
		}
	}
	@Test
	void deckSizeTest() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		assertEquals(52,d.deckSize());
	}
	@Test
	void deckSizeTest2() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		assertNotEquals(0,d.deckSize());
	}
	@Test
	void drawOneTest1() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		Card c = d.drawOne();
		assertEquals("clubs", c.suite);
		assertEquals(1, c.value);
		assertEquals(51, d.deckSize());
	}
	@Test
	void drawOneTest2() {
		ArrayList<String> suites = new ArrayList<>();
		suites.add("clubs");
		suites.add("spades");
		suites.add("hearts");
		suites.add("diamonds");
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		int i = 1;
		int j = 0;
		while(!d.deck.isEmpty()) {
			Card c = d.drawOne();
			assertEquals(i,c.value);
			assertEquals(suites.get(j), c.suite);
			i++;
			if(i > 13) {i = 1; j++;}
		}
		assertEquals(0, d.deckSize());
	}
	@Test
	void dealHandTest1() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		ArrayList<Card> hand = d.dealHand();
		assertEquals(2,hand.size());
		Card c = hand.get(0);
		assertEquals("clubs", c.suite);
		assertEquals(1, c.value);
		Card c2 = hand.get(1);
		assertEquals("clubs", c2.suite);
		assertEquals(2, c2.value);
		assertEquals(50,d.deckSize());
	}
	@Test
	void dealHandTest2() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		ArrayList<Card> hand = d.dealHand();
		ArrayList<Card> hand2 = d.dealHand();
		assertEquals(2,hand2.size());
		Card c = hand2.get(0);
		assertEquals("clubs", c.suite);
		assertEquals(3, c.value);
		Card c2 = hand2.get(1);
		assertEquals("clubs", c2.suite);
		assertEquals(4, c2.value);
		assertEquals(48,d.deckSize());	
	}
	@Test
	void shuffleDeckTest1() { //this is a visual test to ensure that the deck has been shuffled
		BaccaratDealer d = new BaccaratDealer();
		d.shuffleDeck();
		assertEquals(52, d.deckSize());
		for(Card c : d.deck) {
			System.out.println(c.suite + " " + c.value);
		}
	}
	@Test
	void shuffleDeckTest2() {
		BaccaratDealer d = new BaccaratDealer();
		d.generateDeck();
		ArrayList<Card> hand = d.dealHand();
		ArrayList<Card> hand2 = d.dealHand();
		assertEquals(2,hand2.size());
		Card c = hand2.get(0);
		assertEquals("clubs", c.suite);
		assertEquals(3, c.value);
		Card c2 = hand2.get(1);
		assertEquals("clubs", c2.suite);
		assertEquals(4, c2.value);
		assertEquals(48,d.deckSize());
		
		d.shuffleDeck();
		assertEquals(52, d.deckSize());
		for(Card n : d.deck) {
			System.out.println(n.suite + " " + n.value);
		}
	}
	@Test
	void whoWonTest1() {
		Card c1 = new Card("clubs", 9);
		Card c2 = new Card("diamonds", 8);
		ArrayList<Card> h1 = new ArrayList<>();
		ArrayList<Card> h2 = new ArrayList<>();
		h1.add(c1);
		h1.add(c2);
		h2.add(c2);
		h2.add(c1);
		assertEquals("Tie", BaccaratGameLogic.whoWon(h1, h2));
	}
	@Test
	void whoWonTest2() {
		Card c1 = new Card("clubs", 9);
		Card c2 = new Card("diamonds", 8);
		Card c3 = new Card("clubs", 12);
		Card c4 = new Card("diamonds", 9);
		ArrayList<Card> h1 = new ArrayList<>();
		ArrayList<Card> h2 = new ArrayList<>();
		h1.add(c1);
		h1.add(c2);
		h2.add(c3);
		h2.add(c4);
		assertEquals("Player", BaccaratGameLogic.whoWon(h1, h2));
	}
	void checkForNat1() {
		Card c1 = new Card("clubs", 9);
		Card c2 = new Card("diamonds", 8);
		Card c3 = new Card("clubs", 12);
		Card c4 = new Card("diamonds", 9);
		ArrayList<Card> h1 = new ArrayList<>();
		ArrayList<Card> h2 = new ArrayList<>();
		h1.add(c1);
		h1.add(c2);
		h2.add(c3);
		h2.add(c4);
		assertTrue(BaccaratGameLogic.checkForNatural(h1, h2));
	}
	void checkForNat2() {
		Card c1 = new Card("clubs",2);
		Card c2 = new Card("diamonds", 8);
		Card c3 = new Card("clubs", 12);
		Card c4 = new Card("diamonds", 3);
		ArrayList<Card> h1 = new ArrayList<>();
		ArrayList<Card> h2 = new ArrayList<>();
		h1.add(c1);
		h1.add(c2);
		h2.add(c3);
		h2.add(c4);
		assertFalse(BaccaratGameLogic.checkForNatural(h1, h2));
	}
	@Test
	void handTotaltest1(){
		Card c3 = new Card("clubs", 12);
		Card c4 = new Card("diamonds", 3);
		ArrayList<Card> h1 = new ArrayList<>();
		h1.add(c3);
		h1.add(c4);
		assertEquals(3, BaccaratGameLogic.handTotal(h1));
	}
	@Test
	void handTotaltest2(){
		Card c3 = new Card("clubs", 2);
		Card c4 = new Card("diamonds", 3);
		ArrayList<Card> h1 = new ArrayList<>();
		h1.add(c3);
		h1.add(c4);
		assertEquals(5, BaccaratGameLogic.handTotal(h1));
	}
	@Test
	void evaluateBankerDraw() {
		Card c3 = new Card("clubs", 2);
		Card c4 = new Card("diamonds", 3);
		Card p = new Card("club", 4);
		ArrayList<Card> h1 = new ArrayList<>();
		h1.add(c3);
		h1.add(c4);
		assertTrue(BaccaratGameLogic.evaluateBankerDraw(h1, p));
	}
	@Test
	void evaluateBankerDraw2() {
		Card c3 = new Card("clubs", 2);
		Card c4 = new Card("diamonds", 3);
		
		ArrayList<Card> h1 = new ArrayList<>();
		h1.add(c3);
		h1.add(c4);
		assertTrue(BaccaratGameLogic.evaluateBankerDraw(h1, null));
	}
	@Test
	void evaluatePlayerDrawtest() {
		Card c3 = new Card("clubs", 2);
		Card c4 = new Card("diamonds", 3);
		
		ArrayList<Card> h1 = new ArrayList<>();
		h1.add(c3);
		h1.add(c4);
		assertTrue(BaccaratGameLogic.evaluatePlayerDraw(h1));
	}
	@Test
	void evaluatePlayerDrawtest2() {
		Card c3 = new Card("clubs", 7);
		Card c4 = new Card("diamonds", 3);
		
		ArrayList<Card> h1 = new ArrayList<>();
		h1.add(c3);
		h1.add(c4);
		assertTrue(BaccaratGameLogic.evaluatePlayerDraw(h1));
	}
	@Test
	void BaccaratGameConstructorTest() {
		BaccaratGame game = new BaccaratGame("Banker", 5, 0);
		assertEquals("Banker", game.betOn);
		assertEquals(5, game.currentBet);
	}
	@Test
	void evaluateWinningsTest() {
		BaccaratGame game = new BaccaratGame("Banker", 5, 0);
		Card c1 = new Card("clubs", 1);
		Card c2 = new Card("clubs", 2);
		ArrayList<Card> h = new ArrayList<>();
		h.add(c2);
		h.add(c1);
		game.bankerHand = h;
		ArrayList<Card> h2 = new ArrayList<>();
		Card c3 = new Card("clubs", 1);
		Card c4 = new Card("clubs", 3);
		h2.add(c3);
		h2.add(c4);
		game.playerHand = h2;
		
		assertEquals("Banker", game.betOn);
		assertEquals(5, game.currentBet);
		assertEquals(-5, game.evaluateWinnings());
	}
	@Test
	void evaluateWinningsTest2() {
		BaccaratGame game = new BaccaratGame("Tie", 5, 0);
		Card c1 = new Card("clubs", 1);
		Card c2 = new Card("clubs", 2);
		ArrayList<Card> h = new ArrayList<>();
		h.add(c2);
		h.add(c1);
		game.bankerHand = h;
		ArrayList<Card> h2 = new ArrayList<>();
		Card c3 = new Card("clubs", 1);
		Card c4 = new Card("clubs", 2);
		h2.add(c3);
		h2.add(c4);
		game.playerHand = h2;
		
		assertEquals("Tie", game.betOn);
		assertEquals(5, game.currentBet);
		assertEquals(40, game.evaluateWinnings());
	}
	
}
