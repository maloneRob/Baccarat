

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {
	int count = 1;
	TheServer server;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private Consumer<Serializable> callback;
	int portNum;
	
	public Server(Consumer<Serializable>call, int portNum){ //server constructor, creates a new TheServer() object;
		callback = call;
		server = new TheServer();
		server.start();
		this.portNum = portNum;
				
	}
	
	public class TheServer extends Thread{
		public void run() { //extends thread so we implement run
			System.out.println("I am here");
			try(ServerSocket mySocket = new ServerSocket(portNum);){
				callback.accept("server is waiting for clients");
				System.out.println("server is waiting for clients");
				
				while(true) {//we sit here and wait for a connection
					ClientThread c = new ClientThread(mySocket.accept(), count);//we make a new client thread when a client connects	
					System.out.println("Client has connected to server: " + "client #" + count);
					clients.add(c); //add clientThread to clients arraylist
					callback.accept("Client has connected to server: " + "client #" + count);
					c.start(); //start new client thread
					count++;
				}
				
			}//try
			catch(Exception e) { //catch the exception
				System.out.println(e);
				callback.accept("Server socket did not launch");
			}
		}//endrun
	}//endclass
	
	class ClientThread extends Thread{
		Socket connection;
		int count;
		ObjectInputStream in;
		ObjectOutputStream out;
		double winnings;
		ClientThread(Socket s, int count){ //client thread constructor
			this.connection = s;
			this.count = count;
		}
		public void run() { //we extend thread so we must implement run
//			System.out.println("inside run method");
			try { //create input and output streams
				out = new ObjectOutputStream(connection.getOutputStream());
				in = new ObjectInputStream(connection.getInputStream());	
			} catch(Exception e) {
				System.out.println("Streams not open!");
				
			}
			
			while(true) { //we wait and listen for a baccaratinfo object from the server
				try {
					BaccaratInfo info = (BaccaratInfo)in.readObject();
//					System.out.println("I recieved and object"); debugging print statement
					
					//when we revieve an object we create a new BaccaratGame; BaccaratGame's constructor will play through a hand automatically
					BaccaratGame game = new BaccaratGame(info.betOn, info.currentBet, this.winnings);
					
					info.whoWon = game.winner; //we then update the info with the results from the game to send back
					info.totalWinnings = game.totalWinnings;
					info.bankerHandTotal = game.bankerHandTot;
					info.playerHandTotal = game.playerHandTot;
					info.bankerHand = game.bankerHand;
					info.playerHand = game.playerHand;
					info.amountWonOnHand = game.wonOnHand;
					this.winnings = game.totalWinnings; //we update the winnings data memeber to keep track of the total won for this specific client
					if(info.whoWon.equals(info.betOn)) { //we output the data to be displayed on the server itself
						callback.accept("client#" + count + " played a game. Banker total: " + game.bankerHandTot + " Player total: " + game.playerHandTot
								+ ". They bet on "+ info.betOn +" and " + info.whoWon +" won. Player won $" + info.amountWonOnHand);
					} else {
						callback.accept("client#" + count + " played a game. Banker total: " + game.bankerHandTot + " Player total: " + game.playerHandTot
								+ ". They bet on "+ info.betOn +" and " + info.whoWon +" won. Player lost $" + info.currentBet);
					}
					
					
					out.writeObject(info); //we then send the BaccaratInfo object back to the client to be displayed on their gui
				} catch(Exception e) {
					//e.printStackTrace();
					
					clients.remove(this); //if the client disconnects, we remove them from the arraylist of clients and output that they're disconnecting to server
					callback.accept("oops..something went wrong with the socket from client " + count + "...closing down now");
					break; //we then break out of the while loop
				}
			}//endwhile
		}//endrun
		
		
	}//endClientThread
}
