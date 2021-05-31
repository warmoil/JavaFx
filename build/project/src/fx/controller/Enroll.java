package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import db.ReportDAO;
import db.User;
import db.UserDAO;
import fx.AppMainTheCheat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Enroll implements Initializable {

	@FXML private BorderPane enrollPane;
	@FXML private TextField txtCId,txtTitle;
	@FXML private TextArea txtContent;
	@FXML private Button btnEnter,btnCancel;
	private UserDAO dao = new UserDAO();
	private ReportDAO rDao = new ReportDAO();
	private User user;
	private String userId;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		btnEnter.setOnAction(e->enterAction());
		btnCancel.setOnAction(e->cancelAlert());
	}

	public void setReporter(String userId) {
		System.out.println("setReporter에 세팅값:"+userId);
		this.userId = userId;
	}
	
	public void doReporting() {
		String cId = txtCId.getText().toString();
		String reason = txtContent.getText().toString();
		String title = txtTitle.getText().toString();
		rDao.doReporting(cId, userId, reason, title);
	}
	
	public void enterAction(){
		String id = txtCId.getText().toString();
		String content = txtContent.getText().toString();
		String title = txtTitle.getText().toString();
		if(id.length()>0 && content.length() > 0 && title.length() > 0) {
			int reported = rDao.doReporting(id, userId, content, title);
			if(reported != -1) {
			successAlert();
			}
		}else {
		failAlertShow();
		}
	}
	public void failAlertShow() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("글쓰기 실패");
		alert.setHeaderText("실패했음");
		alert.setContentText("유감.. ㅎ");
		alert.showAndWait();

	}
	public void successAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("글쓰기 성공");
		alert.setHeaderText("메인 보내드림 ");
	
	
		Optional<ButtonType> result = alert.showAndWait();
		
	
		if(result.get() == ButtonType.OK) {
			goMain();
		}
	}
	
	public void cancelAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("메인갈게요");
		alert.setHeaderText("취소함");
	
	
		Optional<ButtonType> result = alert.showAndWait();
		
	
		if(result.get() == ButtonType.OK) {
			goMain();
		}
	}
	
	public void goMain() {
		try {
			 //FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/Main.fxml")); 
			Parent main =loader.load();
				
			Main mainCon = loader.getController();
			
			mainCon.setUser(userId);
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnEnter.getScene().getWindow();
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("시발");
		}
	}
}
