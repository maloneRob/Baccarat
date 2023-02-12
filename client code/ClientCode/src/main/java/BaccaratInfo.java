

import java.io.Serializable;
import java.util.ArrayList;


//client
public class BaccaratInfo implements Serializable {

	/**
	 * 
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
