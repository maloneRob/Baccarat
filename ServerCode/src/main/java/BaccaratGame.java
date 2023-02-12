

import java.util.ArrayList;

public class BaccaratGame {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;  //data members included according to project requirements
	String betOn;
	String winner;
	int bankerHandTot;
	int playerHandTot;
	double wonOnHand;
	double currentBet;
	double totalWinnings;
	//BaccaratGame(string, double, double)
	//constructor, takes in a string on who the player bet on, their bet amount and how much they've won so far and sets the data members accordingly
	//after construction a hand is automatically played
	public BaccaratGame(String betOn, double currentBet, double totalWinnings) {
		this.betOn = betOn;
		this.currentBet = currentBet;
		this.totalWinnings = totalWinnings;
		playHand();
	}
	//playHand()
	//plays a hand according to the baccarat game rules provided in the project requirements
	void playHand() {
		System.out.println("playing a hand now");
		theDealer = new BaccaratDealer(); //a new dealer is created
		theDealer.shuffleDeck(); //the suffleDeck() method is called which creates a new deck and randomizes it
		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();
		if(BaccaratGameLogic.checkForNatural(bankerHand, playerHand)) { //we check for a natural win first
			wonOnHand = evaluateWinnings(); //if a natural is detected then we evaluate the winnings according to the rules
			this.totalWinnings += wonOnHand;
		} else {
			if(BaccaratGameLogic.evaluatePlayerDraw(playerHand)) { //if natural is not found then we check if the player should get another card
				Card playerDraw = theDealer.drawOne(); //if true we draw one from the deck
				playerHand.add(playerDraw); //we add card to playerhand
				if(BaccaratGameLogic.evaluateBankerDraw(bankerHand, playerDraw)) { //we then check if the dealer should draw and pass it the player's draw per the rules
					Card bankerDraw = theDealer.drawOne();
					bankerHand.add(bankerDraw);
				}
				
			} else {
				if(BaccaratGameLogic.evaluateBankerDraw(bankerHand, null)) { //even if the player doesn't draw we must check if the banker should still draw
					Card bankerDraw = theDealer.drawOne();                   //we pass null indicating the player did not draw
					bankerHand.add(bankerDraw);
				}
			}
			wonOnHand = evaluateWinnings(); //we check who won and update variables accordingly
			this.totalWinnings += wonOnHand;
		}
		bankerHandTot = BaccaratGameLogic.handTotal(bankerHand); //we save the hand totals so we can pass them through the BaccaratInfo class
		playerHandTot = BaccaratGameLogic.handTotal(playerHand);
		//System.out.println("winnings this hand = " + evaluateWinnings());
	}
	//evaluateWinnings()
	//calls the whowon(arraylist, arraylist) method from BaccaratGameLogic to determine who won the hand
	//returns amount won accoriding the rules of baccarat
	public double evaluateWinnings() {
		winner = BaccaratGameLogic.whoWon(bankerHand, playerHand);
//		System.out.println("winner is :*" + winner +"*");
//		System.out.println("betOn is: *" + betOn + "*");
		if(betOn.equals("Tie") && betOn.equals(winner)) {
			return (8.0 * currentBet);
		} else if (betOn.equals(winner)) {
			return currentBet;
		}
		return -currentBet;
	}

}
