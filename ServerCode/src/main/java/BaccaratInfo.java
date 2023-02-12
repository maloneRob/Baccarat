

import java.io.Serializable;
import java.util.ArrayList;
//server
class BaccaratInfo implements Serializable { 

	/**
	 serializable so we can send back and forth to client//identical to BaccaratInfo in client program
	 */
	private static final long serialVersionUID = 1L;
	String betOn;
	String whoWon;
	double currentBet;
	double totalWinnings;
	double amountWonOnHand;
	int bankerHandTotal;
	int playerHandTotal;
	ArrayList<Card> bankerHand;
	ArrayList<Card> playerHand;

}
