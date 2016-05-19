package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Gui;


public class Main extends Application{	
	private Gui gui;
	
	private Stage primaryStage;
	
	public Main(){
		gui = new Gui(this);
	}
	
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		gui.start(primaryStage);
		new Controller(gui);
	}
}
