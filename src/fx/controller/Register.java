package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Register implements Initializable {

	@FXML Button btnJoin,btnCancel;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnCancel.setOnAction(e->btnCancelAction(e));
		
	}

	public void btnJoinAction(ActionEvent e) {
		
	}
	public void btnCancelAction(ActionEvent e) {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));				
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnCancel.getScene().getWindow();
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("시발");
		}
	
	}
}
