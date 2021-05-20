package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import alert.ESCAlert;
import db.User;
import db.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private UserDAO dao = new UserDAO();
	private String userId;
	String nickName;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		txtfieldSearch.setOnKeyPressed(e->enterKeyEvent(e));
		Platform.runLater(()->txtfieldSearch.focusedProperty());
		btnGoLogin.setOnAction(e->goLoginAlert());
		Platform.runLater(()->lblUserId.setText(userId));
		Platform.runLater(()->lblChk.setText("너의 닉:"+nickName));
		mainPane.setOnKeyPressed(e->escKeyEvent(e));
		btnSearch.setOnAction(e->searchingAction());
		Platform.runLater(()->test(userId));
	}

	public void test(String userId) {
		System.out.println(userId);
		user = dao.getUserInfo(userId);
		System.out.println(user.getUserAsk()+"\n"+user.getUserAnswer());
	}
	public void setUser(String userId) {
		nickName = dao.getUserNickName(userId);
		this.userId = userId;
	}
	
	public void setUser(User user) {
		
		nickName = dao.getUserNickName(user);
		this.userId = user.getUserId();
		this.user  = user;
		
	}
	public void escKeyEvent(KeyEvent e) {
		KeyCode key = e.getCode();
		if(key.equals(KeyCode.ESCAPE)) {
			ESCAlert al = new ESCAlert();
			al.escAlertShow();
		}
	}
	public void enterKeyEvent(KeyEvent e) {
		KeyCode key = e.getCode();
		if(key.equals(KeyCode.ENTER)) {
			searchingAction();
		}
	}
	public void searchingAction() {
		if(txtfieldSearch.getText().toString().length()>0) {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Search.fxml"));
			 Parent center =loader.load();
			
			Search searchCon = loader.getController();
			searchCon.setUserId(lblUserId.getText().toString());
			System.out.println(lblUserId.getText().toString());
			searchCon.doSearching(txtfieldSearch.getText().toString(),lblUserId.getText().toString());
		
			mainPane.setCenter(center);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("시발");
		}
	}
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
