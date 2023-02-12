import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
//client
public class JavaFXTemplate extends Application {
	HashMap<String, Scene> sceneMap;
	HashMap<String, String> cardMap;
	Button bankerBet = new Button("Banker");
	Button playerBet = new Button("Player");
	Button tieBet = new Button("Tie");
	Button playAgain = new Button("Play Again");
	Button quitB = new Button("Quit");
	Button connect = new Button("Connect");
	Button playHandBtn = new Button("Play Hand");
	TextField ipT = new TextField("127.0.0.1");
	TextField portT = new TextField("Enter port number");
	TextField betField = new TextField("0.0");
	HBox bankerHand;
	HBox playerHand;
	Label winTot;
	ListView<String> list;
	ObservableList<String> strings;
	EventHandler<ActionEvent> connection;
	Client clientConnection;
	ListView<String> listItems2;
	EventHandler<ActionEvent> betPlayer;
	EventHandler<ActionEvent> betBanker;
	EventHandler<ActionEvent> betTie;
	EventHandler<ActionEvent> pAgain;
	EventHandler<ActionEvent> playHand;
	PauseTransition pause;
	static final int picHeight = 100;
	static final int picWidth = 100;
	boolean player = false;
	boolean banker = false;
	boolean tie = false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Baccarat");
		sceneMap = new HashMap<>();
		list = new ListView<>();
		strings = FXCollections.observableArrayList();
		list.setItems(strings);
		quitB.setOnAction(e->{System.exit(0);});
		listItems2 = new ListView<>();
		cardMap = new HashMap<>();
		playAgain.setDisable(true);
		playHandBtn.setDisable(true);
		connection = new EventHandler<ActionEvent>() { //event handler for the connect button on the login screen

			@Override
			public void handle(ActionEvent event) {
				int portNum = 0;
				String ip = null;
				try {
					portNum = Integer.parseInt(portT.getText()); //grabs the data from the textfield and creates a Client(Consumer<Serializable> call,int port, String ipAddress) object
					ip = ipT.getText();
					clientConnection = new Client(data->{
						Platform.runLater(()->{updateListView((BaccaratInfo) data);});
					}, portNum, ip);
					primaryStage.setScene(sceneMap.get("main"));
					clientConnection.start();
					
				}catch(Exception e) {
					portT.setText("Either the server you are trying to connect to is not running or the information you've entered is incorrect");
				}
				
				
			}
			
		};
		playHand = new EventHandler<ActionEvent>() { //event handler for the play hand button

			@Override
			public void handle(ActionEvent event) {
				String betString = "";
				if(player) { //check which boolean value is true
					betString = "Player";
				} else if(banker) {
					betString = "Banker";
				} else if(tie) {
					betString = "Tie";
				}
				try {
					double betAmount = Double.valueOf(betField.getText()); //grab the text from the text field and call the sendBet(string, double) method
					clientConnection.sendBet(betString, betAmount);
					playHandBtn.setDisable(true); //we then disable our buttons and textfield so they cannot be triggered while hand is playing
					tieBet.setDisable(true);
					bankerBet.setDisable(true);
					playerBet.setDisable(true);
					betField.setEditable(false);
				}catch(Exception e) {
					betField.setText("bet amount must be a number"); //if the bet amount isn't a number, double.valueOf will throw an error, we put it in the text field so user knows
				}
				
			}
			
		};
		betPlayer = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				player = true; //change the booleans accordingly as you can only bet on one thing per hand
				banker = false;
				tie = false;
				bankerBet.setDisable(false);
				tieBet.setDisable(false);
				playerBet.setDisable(true);
				betField.setEditable(false);
				playHandBtn.setDisable(false); //play hand is default disabled so user can't play hand without betting
			}
			
		};
		betBanker = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				player = false;
				banker = true;
				tie = false;
				bankerBet.setDisable(true);
				tieBet.setDisable(false);
				playerBet.setDisable(false);
				betField.setEditable(false);
				playHandBtn.setDisable(false);
				
				
			}
			
		};
		betTie = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				player = false;
				banker = false;
				tie = true;
				bankerBet.setDisable(false);
				tieBet.setDisable(true);
				playerBet.setDisable(false);
				betField.setEditable(false);
				playHandBtn.setDisable(false);
				
				
			}
			
		};
		pAgain = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				playAgain.setDisable(true);
				tieBet.setDisable(false);
				bankerBet.setDisable(false);
				playerBet.setDisable(false);
				betField.setEditable(true);
				
			}
			
		};
		createCardMap(); //we fill our card map
		playHandBtn.setOnAction(playHand);//set event handlers to required buttons
		playAgain.setOnAction(pAgain);
		tieBet.setOnAction(betTie);
		bankerBet.setOnAction(betBanker);
		playerBet.setOnAction(betPlayer);
		connect.setOnAction(connection);
		sceneMap.put("main", createMainScene());//create and place our scenes in our sceneMap
		sceneMap.put("login", createLoginScreen());
				
		//Scene scene = new Scene(new VBox(), 700,700);
		primaryStage.setScene(sceneMap.get("login"));
		primaryStage.show();
	}
	//updateListView(BaccaratInfo)
	//this method updates the list view with the required output 
	private void updateListView(BaccaratInfo info) {
		makeCardImages(info.bankerHand, info.playerHand); //this method is called which finds the cards in our cardMap and then puts them in the correct hboxes
		PauseTransition pause2 = new PauseTransition(Duration.seconds(4)); //we create a pause transition to emulate the game actually being played
		pause2.play();
		pause2.setOnFinished(finished->{ //when the pause is finished we display the rest of our data to the listview
			String bhand = "";
			
			for(Card c: info.bankerHand) {
				bhand += c.value + " " + c.suite + " ";
			}
			String phand = "";
			for(Card c: info.playerHand) {
				phand += c.value + " " + c.suite + " ";
			}
			listItems2.getItems().add("You bet on " + info.betOn);
			listItems2.getItems().add(bhand);
			
			System.out.println(info.totalWinnings);
			listItems2.getItems().add(phand);
			listItems2.getItems().add("banker hand total: "+ info.bankerHandTotal);
			listItems2.getItems().add("player hand total: " + info.playerHandTotal);
			listItems2.getItems().add(info.whoWon + " won!");
			listItems2.getItems().add("--------------------------------");
			updateWinTot(info.totalWinnings);
		});
		
	}
	//makeCardImages(arraylist, arraylist)
	//this method takes in two hands of card objects and then searches the cardMap to get the required images to display in the hboxes
	private void makeCardImages(ArrayList<Card> bhand, ArrayList<Card> phand) {
		bankerHand.getChildren().clear();
		playerHand.getChildren().clear();
		pause = new PauseTransition(Duration.seconds(2));
		listItems2.getItems().add("Dealing Initial Hand");//we create a pausetransition to emulate the hand actually being played
		//show banker hand
		String b1 = bhand.get(0).value + bhand.get(0).suite; //we concatonate the card.value with card.suite as a string to search our map
		Image pic1 = new Image(cardMap.get(b1));             //each card image is stored with the naming convention valuesuite;  example(1hearts = ace of hearts)
		ImageView view1 = new ImageView(pic1);
		view1.setFitHeight(picHeight);
		view1.setFitWidth(picWidth);
		bankerHand.getChildren().add(view1);
		
		String b2 = bhand.get(1).value + bhand.get(1).suite;
		Image pic2 = new Image(cardMap.get(b2));
		ImageView view2 = new ImageView(pic2);
		view2.setFitHeight(picHeight);
		view2.setFitWidth(picWidth);
		bankerHand.getChildren().add(view2);
		
		String p1 = phand.get(0).value + phand.get(0).suite;
		Image pic3 = new Image(cardMap.get(p1));
		ImageView view3 = new ImageView(pic3);
		view3.setFitHeight(picHeight);
		view3.setFitWidth(picWidth);
		playerHand.getChildren().add(view3);
		
		String p2 = phand.get(1).value + phand.get(1).suite;
		Image pic4 = new Image(cardMap.get(p2));
		ImageView view4 = new ImageView(pic4);
		view4.setFitHeight(picHeight);
		view4.setFitWidth(picWidth);
		playerHand.getChildren().add(view4);
		pause.play();
		pause.setOnFinished(finish->{ //after the initial hand is "dealt" we show the remainder of the cards if they're there and output to listview accordingly
			if(bhand.size() == 2 && phand.size() == 2) {
				listItems2.getItems().add("natural win");
			} else {
				listItems2.getItems().add("No Natural Win");
			}
			
			if(phand.size() == 3) {
				listItems2.getItems().add("Player Gets Another Card");
				String p3 = phand.get(2).value + phand.get(2).suite;
				Image pic5 = new Image(cardMap.get(p3));
				ImageView view5 = new ImageView(pic5);
				view5.setFitHeight(picHeight);
				view5.setFitWidth(picWidth);
				playerHand.getChildren().add(view5);
			}
			if(bhand.size() == 3) {
				listItems2.getItems().add("Banker Gets Another Card");
				String b3 = bhand.get(2).value + bhand.get(2).suite;
				Image pic6 = new Image(cardMap.get(b3));
				ImageView view6 = new ImageView(pic6);
				view6.setFitHeight(picHeight);
				view6.setFitWidth(picWidth);
				bankerHand.getChildren().add(view6);
			}
		});
	}
	//updateWinTot(double)
	//this method updates the winTot label 
	private void updateWinTot(double winnings) {
		try {
			winTot.setText(String.valueOf(winnings));
		}catch(Exception e) {e.printStackTrace();}
		playAgain.setDisable(false);
	}
	//createLoginScreen()
	//this method creates the login screen for when the application launches
	private Scene createLoginScreen() {
		Label l1 = new Label("Welcome to Baccarat!");
		Button q = new Button("Quit");
		q.setOnAction(e->{System.exit(0);});
		ipT.setMaxWidth(200);
		portT.setMaxWidth(200);
		Label l2 = new Label("Input IP Address");
		Label l3 = new Label("Input Port Number");
		VBox v = new VBox(l1, l2,ipT, l3,portT,connect,q);
		v.setSpacing(5);
		v.setStyle("-fx-background-color: #299e1e");
		BorderPane bp = new BorderPane(v);
		v.setAlignment(Pos.CENTER);
		Scene root = new Scene(bp,700,700);
		return root;
	}
	//createMainScene()
	//this method creates the main scene that is transitioned to after the connect button is clicked
	private Scene createMainScene() {
		Label banker = new Label("Banker");
		Label player = new Label("Player");
		Label betl = new Label("Place your bet on:");
		Label win = new Label("Total Winnings: ");
		winTot = new Label("0");
		bankerHand = new HBox();
		bankerHand.setPrefSize(150, 150);
		bankerHand.setSpacing(20);
		bankerHand.setStyle("-fx-background-color: lightGrey");
		playerHand = new HBox();
		playerHand.setPrefSize(150, 150);
		playerHand.setSpacing(20);
		playerHand.setStyle("-fx-background-color: lightGrey");
		HBox betBox = new HBox(win, winTot,bankerBet, playerBet, tieBet , playHandBtn, playAgain, quitB);
		betBox.setSpacing(20);
		betBox.setAlignment(Pos.CENTER);
		Label betLabel = new Label("Enter your bet amount and then press a button to place your bet");
		VBox mainBox = new VBox(banker, bankerHand, player, playerHand, betLabel,betField, betl, betBox, listItems2);
		betField.setMaxWidth(picHeight);
		mainBox.setSpacing(5);
		listItems2.setPrefHeight(200);
		listItems2.setPrefWidth(100);
		BorderPane bp = new BorderPane(mainBox);
		mainBox.setAlignment(Pos.CENTER);
		bp.setStyle("-fx-background-color: #299e1e");
		Scene root = new Scene(bp, 700,700);
		return root;
	}
	//createCardMap()
	//this method fills the cardmap with resource paths
	private void createCardMap() {
		cardMap.put("1clubs", "ace_of_clubs.png");
		cardMap.put("2clubs", "2_of_clubs.png");
		cardMap.put("3clubs", "3_of_clubs.png");
		cardMap.put("4clubs", "4_of_clubs.png");
		cardMap.put("5clubs", "5_of_clubs.png");
		cardMap.put("6clubs", "6_of_clubs.png");
		cardMap.put("7clubs", "7_of_clubs.png");
		cardMap.put("8clubs", "8_of_clubs.png");
		cardMap.put("9clubs", "9_of_clubs.png");
		cardMap.put("10clubs", "10_of_clubs.png");
		cardMap.put("11clubs", "jack_of_clubs.png");
		cardMap.put("12clubs", "queen_of_clubs.png");
		cardMap.put("13clubs", "king_of_clubs.png");
		
		cardMap.put("1spades", "ace_of_spades.png");
		cardMap.put("2spades", "2_of_spades.png");
		cardMap.put("3spades", "3_of_spades.png");
		cardMap.put("4spades", "4_of_spades.png");
		cardMap.put("5spades", "5_of_spades.png");
		cardMap.put("6spades", "6_of_spades.png");
		cardMap.put("7spades", "7_of_spades.png");
		cardMap.put("8spades", "8_of_spades.png");
		cardMap.put("9spades", "9_of_spades.png");
		cardMap.put("10spades", "10_of_spades.png");
		cardMap.put("11spades", "jack_of_spades.png");
		cardMap.put("12spades", "queen_of_spades.png");
		cardMap.put("13spades", "king_of_clubs.png");
		
		cardMap.put("1hearts", "ace_of_hearts.png");
		cardMap.put("2hearts", "2_of_hearts.png");
		cardMap.put("3hearts", "3_of_hearts.png");
		cardMap.put("4hearts", "4_of_hearts.png");
		cardMap.put("5hearts", "5_of_hearts.png");
		cardMap.put("6hearts", "6_of_hearts.png");
		cardMap.put("7hearts", "7_of_hearts.png");
		cardMap.put("8hearts", "8_of_hearts.png");
		cardMap.put("9hearts", "9_of_hearts.png");
		cardMap.put("10hearts", "10_of_hearts.png");
		cardMap.put("11hearts", "jack_of_hearts.png");
		cardMap.put("12hearts", "queen_of_hearts.png");
		cardMap.put("13hearts", "king_of_hearts.png");
		
		cardMap.put("1diamonds", "ace_of_diamonds.png");
		cardMap.put("2diamonds", "2_of_diamonds.png");
		cardMap.put("3diamonds", "3_of_diamonds.png");
		cardMap.put("4diamonds", "4_of_diamonds.png");
		cardMap.put("5diamonds", "5_of_diamonds.png");
		cardMap.put("6diamonds", "6_of_hearts.png");
		cardMap.put("7diamonds", "7_of_diamonds.png");
		cardMap.put("8diamonds", "8_of_diamonds.png");
		cardMap.put("9diamonds", "9_of_diamonds.png");
		cardMap.put("10diamonds", "10_of_diamonds.png");
		cardMap.put("11diamonds", "jack_of_diamonds.png");
		cardMap.put("12diamonds", "queen_of_diamonds.png");
		cardMap.put("13diamonds", "king_of_diamonds.png");
	}

}
