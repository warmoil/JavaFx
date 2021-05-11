package fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main implements Initializable {

	@FXML private TextField txtfieldSearch;
	@FXML private Button btnSearch,btnLogin,btnGoMain;
	@FXML private BorderPane mainPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->txtfieldSearch.focusedProperty());
		btnLogin.setOnAction(e->goLogin());

	}

	public void goLogin() {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnLogin.getScene().getWindow();
			stage.setScene(mainScene);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("로그인창 진입실패 아나 ");
		}
	}
	
}
