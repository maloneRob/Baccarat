

import java.io.Serializable;

public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String suite;
	int value;
	Card(String theSuite, int theValue){
		this.suite = theSuite;
		this.value = theValue;
	}
}
