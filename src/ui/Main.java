package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.BillboardList;

public class Main extends Application{
	
	private InfraestructureDepartmentGUI infdeptGUI;
	private BillboardList billboardList; 
	
	public Main() {
		billboardList= new BillboardList();
		infdeptGUI= new InfraestructureDepartmentGUI(billboardList);
		
		boolean loadBillboards;
		try {
			loadBillboards=billboardList.loadBillboards();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			loadBillboards=false;
			
		} 
		
		if(!loadBillboards) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Billboard");
			alert.setHeaderText(null);
			alert.setContentText("Error loading data from file ");

			alert.showAndWait();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainPane.fxml"));

		fxmlLoader.setController(infdeptGUI);
		Parent root= fxmlLoader.load();
		
		Scene scene= new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Billboard");
		primaryStage.show();
	}

	

}
