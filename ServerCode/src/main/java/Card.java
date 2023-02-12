

import java.io.Serializable;

public class Card implements Serializable{
	/**
	 implementing serializable so we can send the card through i/o streams
	 */
	private static final long serialVersionUID = 1L;
	String suite;
	int value;
	//card(string, int) 
	//constructor for card class
	Card(String theSuite, int theValue){
		this.suite = theSuite;
		this.value = theValue;
	}
}
