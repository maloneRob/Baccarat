

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	String ipAddress;
	int portNumber;
	private Consumer<Serializable> callback;
	public Client(Consumer<Serializable> call,int port, String ipAddress){
		callback = call;
		this.portNumber = port;
		this.ipAddress = ipAddress;
	}
	
	public void run() {
		try {
			socketClient = new Socket(ipAddress,portNumber);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		} catch(Exception e) {}
		
		while(true) {
			try {
			BaccaratInfo info = (BaccaratInfo)in.readObject();
			System.out.println("The client has recieved an object");
			callback.accept(info);
			//System.out.println(info.message);
			} catch(Exception e) {
			
			}
		}//while
	}
	public void sendBet(String betOn, double currentBet) {
		try {
			BaccaratInfo info = new BaccaratInfo();
			info.betOn = betOn;
			info.currentBet = currentBet;
			out.writeObject(info);
			System.out.println("I sent an object");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Server is not currently running");
			System.exit(0);
		}
	}
}
