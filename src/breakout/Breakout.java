package breakout;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Breakout extends Application{
	
	private static Stage stage;
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage s) throws Exception {
		
		stage = s; 
		
		stage.setTitle("Breakout");
		
		showTitle(); 
		
		stage.show();
	}
	
	public static void showTitle() {
		
		BorderPane root = new BorderPane();
		root.setPrefSize(600, 400);

		Image img = new Image("/breakoutresources/breakouttitle.png");
		ImageView title = new ImageView(img);
		title.setPreserveRatio(true);
		title.setFitWidth(400);
		
		root.setTop(title);
		root.setAlignment(title, Pos.TOP_CENTER);
		root.setPadding(new Insets(50, 20, 20, 20));
		
		Button playBtn = new Button("PLAY");
		playBtn.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
		playBtn.setPrefWidth(200);
		playBtn.setPrefHeight(75);
		playBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		        startGame();
		    }
		});
		
		root.setCenter(playBtn);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
	
	}
	
	public static void startGame() {
		
		BorderPane root = new BorderPane();
		root.setPrefSize(600, 400);
		
		BallWorld ballWorld = new BallWorld();
		root.setCenter(ballWorld);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		ballWorld.loadLevel(ballWorld.getLevel());
		ballWorld.start();
	}


}
