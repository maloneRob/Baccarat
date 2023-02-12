import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//server
public class JavaFXTemplate extends Application {
	Button startS;
	Button close;
	Button quit;
	Server serverConnection;
	ListView<String> listItems;
	ListView<String> infoList;
	TextField tf = new TextField("Enter port to listen to");
	EventHandler<ActionEvent> startServer;
	HashMap<String, Scene> sceneMap;
	int portNum;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Baccarat Server");
		//this event handler is attached to the start server button
		startServer = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				//grabs the port number from the textfield
				portNum = Integer.parseInt(tf.getText());
				primaryStage.setScene(sceneMap.get("main"));
				serverConnection = new Server(data -> {//creates a new server object by calling the Server(Consumer, portNum) constructor
					Platform.runLater(()->{
						listItems.getItems().add(data.toString());
					}); updateInfoList();

				}, portNum);};
			};
		
		startS = new Button("Start Server");
		close = new Button("Close Server");
		close.setOnAction(e->{System.exit(0);});//attach handlers to the close and quit buttons
		quit = new Button("Quit");
		quit.setOnAction(e->{System.exit(0);});
		startS.setOnAction(startServer);
		listItems = new ListView<>();
		infoList = new ListView<>();
		sceneMap = new HashMap<String, Scene>(); //create sceneMap and our scenes and store them
		sceneMap.put("login", createStart());
		sceneMap.put("main", createMain());
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { //event for closing the window to make sure that socket gets freed

			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
				
			}});
		
		primaryStage.setScene(sceneMap.get("login"));
		primaryStage.show();
	}
	private Scene createStart() { //method for creating the intro scene
		Label introLabel = new Label("BACCARAT SERVER START SCREEN");
		VBox vbox = new VBox(introLabel,tf,startS, quit);
		tf.setMaxWidth(200);
		vbox.setStyle("-fx-background-color: #299e1e");
		Scene scene = new Scene(vbox,700,700);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		return scene;
	}
	private Scene createMain() { //method for creating the main scene
		Text infoLabel = new Text("INFO");
		infoLabel.setStyle("-fx-font-size: 20");
		infoLabel.setFill(Color.WHITE);
		infoLabel.setStrokeWidth(0.5);
		infoLabel.setStroke(Color.BLACK);
		//VBox infoBox = new VBox(close,infoLabel, infoList);
		close.setPrefSize(100, 50);
		close.setStyle("-fx-background-color: red");
		VBox newbox = new VBox(close, infoLabel, infoList, listItems);
		infoList.setMaxSize(150, 50);
		newbox.setAlignment(Pos.CENTER);
		newbox.setSpacing(5);
		
		Scene scene = new Scene(newbox, 700,700);
		newbox.setStyle("-fx-background-color: #299e1e");
		newbox.setAlignment(Pos.CENTER);
		return scene;
	}
	//updateInfoList()
	//this method gets called every time data is sent to the server, it updates the info to ensure that the number of clients are displayed
	private void updateInfoList() { 
			infoList.getItems().clear();
			infoList.getItems().add("Port : " + portNum);
			infoList.getItems().add("Number of Users : " + serverConnection.clients.size());
		
	}

}
