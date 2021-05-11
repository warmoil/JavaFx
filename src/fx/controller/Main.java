package fx.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import db.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main implements Initializable {

	@FXML private TextField txtfieldSearch;
	@FXML private Button btnSearch,btnGoLogin,btnGoMain;
	@FXML private BorderPane mainPane;
	@FXML private Label lblUserId,lblChk;
	private User user;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->txtfieldSearch.focusedProperty());
		btnGoLogin.setOnAction(e->goLoginAlert());
		Platform.runLater(()->lblUserId.setText(user.getUserId()));
		Platform.runLater(()->lblChk.setText(user.getUserId()+"chk"));
	}

	public void sendUserId(User user) {
		this.user  = user;
	}
	public void goLoginAlert() {
		Alert alert =  new Alert(AlertType.CONFIRMATION);
		alert.setTitle("로그인창으로?");
		alert.setHeaderText("가즈아?");
		Optional<ButtonType> result = alert.showAndWait();
	
		if(result.get() == ButtonType.OK) {
			goLogin();
		}else if(result.get() == ButtonType.NO){
			alert.close();
		}
		
		
	}
	
	public void goLogin() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnGoLogin.getScene().getWindow();
			stage.setScene(mainScene);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("로그인창 진입실패 아나 ");
		}
	}
	
}
