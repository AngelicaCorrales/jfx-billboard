package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import model.Billboard;
import model.BillboardList;

public class InfraestructureDepartmentGUI{
	

    @FXML
    private BorderPane mainPane;
    
    @FXML
    private TextField txtWidth;

    @FXML
    private TextField txtHeight;

    @FXML
    private CheckBox cbInUse;

    @FXML
    private TextField txtBrand;
    
    @FXML
    private TableView<Billboard> tableView;

    @FXML
    private TableColumn<Billboard, String> colWidth;

    @FXML
    private TableColumn<Billboard, String> colHeight;

    @FXML
    private TableColumn<Billboard, String> colInUse;

    @FXML
    private TableColumn<Billboard, String> colBrand;

	
	private BillboardList billboardList;
	
	public InfraestructureDepartmentGUI(BillboardList billboardList) {
		this.billboardList=billboardList;
	}
	
	public void initializeTableView() {
    	ObservableList<Billboard> list= FXCollections.observableArrayList(billboardList.getBillboards());
    	
    	tableView.setItems(list);
    	colWidth.setCellValueFactory(new PropertyValueFactory<Billboard,String>("width"));
    	colHeight.setCellValueFactory(new PropertyValueFactory<Billboard,String>("height"));
    	colInUse.setCellValueFactory(new PropertyValueFactory<Billboard,String>("inUse"));
    	colBrand.setCellValueFactory(new PropertyValueFactory<Billboard,String>("brand"));
   }
	
	 @FXML
    public void showAddBillboard(ActionEvent event) throws IOException {
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBillboard.fxml"));
		fxmlLoader.setController(this); 
		
		Parent addB= fxmlLoader.load();
		
		mainPane.setCenter(addB);	
    }

    @FXML
    public void exportDangerousBillboardReport(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File f=fileChooser.showSaveDialog(mainPane.getScene().getWindow());
    	if(f!=null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Export Dangerous Billboard Report");
    		
    		try {
    			billboardList.exportDangerousBillboardReport(f.getAbsolutePath());
        		alert.setContentText("The billboard data was exported succesfully");
        		alert.showAndWait();
    		}catch(FileNotFoundException e){
        		alert.setContentText("The billboard data was not exported. An error occurred");
        		alert.showAndWait();
    		}
    	}
    }

    @FXML
    public void importData(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	File f=fileChooser.showOpenDialog(mainPane.getScene().getWindow());
    	if(f!=null) {
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Import Contacts");
    		
    		try {
    			
    			billboardList.importData(f.getAbsolutePath());
        		alert.setContentText("The billboard data was imported succesfully");
        		alert.showAndWait();
    		}catch(IOException e){
        		alert.setContentText("The billboard data was not imported. An error occurred");
        		alert.showAndWait();
    		}
    	}
    }
    

    @FXML
    public void showBillboards(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("billboardList.fxml"));

		fxmlLoader.setController(this);
    	Parent bList= fxmlLoader.load();
		mainPane.setCenter(bList);
		initializeTableView();
    }
    
    @FXML
    public void addBillboard(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Billboard");
		alert.setHeaderText(null);
    	if(!txtWidth.getText().equals("") && !txtHeight.getText().equals("") && !txtBrand.getText().equals("")) {
	    	double width =Double.parseDouble(txtWidth.getText());
	    	double height =Double.parseDouble(txtHeight.getText());
    		try {
				billboardList.addBillboard(width, height, cbInUse.isSelected(),txtBrand.getText() );
				txtWidth.clear();
				txtHeight.clear();
				txtBrand.clear();
				cbInUse.setSelected(false);
		    	alert.setContentText("The billboard has been saved!");

	    		alert.showAndWait();
		    	
    		} catch (IOException e) {
				e.printStackTrace();
				alert.setContentText("The billboard was not added");

	    		alert.showAndWait();
			}
    		
    		

	    	
    	}
    	else {
    		alert.setContentText("You must fill each field in the form");

    		alert.showAndWait();
    	}
    }

}
