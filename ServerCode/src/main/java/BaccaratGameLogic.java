

import java.util.ArrayList;

public class BaccaratGameLogic {
	//public static boolean initialHand = true;
	//String whoWon(arraylist, arraylist)
	//takes in the banked hand as h1 and playhand as hand2
	//calls the handTotal(arraylist) method to calculate each hand total
	//if hands are equal then returns Tie; else checks which hand is closest to 9
	public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int target = 9;
		int bankerHand = handTotal(hand1);
		int playerHand = handTotal(hand2);
		if(bankerHand == playerHand) {
			return "Tie";
		}else if(target - bankerHand < target - playerHand) {
			return "Banker";
		} else {
			return "Player";
		}
	}
	//boolean checkForNatural(arraylist, arraylist)
	//checks if either hand has an 8 or 9 by calling handTotal(arraylist) method
	//outputs true if either does; else returns false
	public static boolean checkForNatural(ArrayList<Card> banker, ArrayList<Card> player) {
		int bankerHand = handTotal(banker);
		int playerHand = handTotal(player);
		if((bankerHand == 8 || bankerHand == 9) || (playerHand == 8 || playerHand == 9)) {
			return true;
		}
		return false;
	}
	//handTotal(arraylist)
	//takes in an arraylist of card objects and counts them per the rules of baccarat
	//cards 10 and above count as 0, ace counts as 1 and the rest are worth their face value
	//if the total is more than 9, mod 10 to chop off first digit according to rules of baccarat
	//returns total of hand
	public static int handTotal(ArrayList<Card> hand) {
		int total = 0;
		for(Card c : hand) {
			if(c.value <= 9) {
				total += c.value;
			}
		}
		if(total > 9)
			return total % 10;
		
		return total;
	}
	//evaluateBankerDraw(arraylist, card)
	//takes in an arraylist of card objects and the card that the player drew to evaluate if the banker should draw an additional card
	//if the player did not draw a card then playercard will be null
	//follows the rules as it pertains to table provided in project requirements
	//returns true if banker should draw; false otherwise
	public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		int handTotal = handTotal(hand);
		if(handTotal <= 2) {
			return true;
		} else if(handTotal <= 6) {
			if(handTotal == 6) {
				if(playerCard.value == 6 || playerCard.value == 7) {
					return true;
				} else {
					return false;
				}
			} else if(handTotal == 5) {
				if(playerCard == null) {
					return true;
				} else if((playerCard.value >= 4 && playerCard.value <= 7)){
					return true;
				} else {
					return false;
				}
			} else if(handTotal == 4) {
				if(playerCard == null) {
					return true;
				} else if(playerCard.value >= 2 && playerCard.value <= 7) {
					return true;
				} else {
					return false;
				}
			} else if(handTotal == 3) {
				if(playerCard == null || playerCard.value != 8) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	//evaluatePlayerDraw(arraylist)
	//takes in an arraylist of card objects and evaluates whether the player gets an additional card per the rules of baccarat
	public static boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		//initialHand = false;
		if(handTotal(hand) <= 5)
			return true;
		
		return false;
	}
}
