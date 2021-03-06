package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import alert.ESCAlert;
import db.User;
import db.UserDAO;
import fx.AppMainTheCheat;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Register implements Initializable {

	@FXML Button btnJoin,btnCancel,btnIdCheck;
	@FXML Label lblId,lblPw,lblPwCheck;
	@FXML BorderPane registerPane;
	@FXML TextField txtId,txtAsk,txtAnswer,txtNickName;
	@FXML PasswordField txtPw,txtPwCheck;
	UserDAO userDao = new UserDAO();
	private String chkId = "";
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		btnCancel.setOnAction(e->cancelAction());
		registerPane.setOnKeyPressed(e->escEvent(e));
		btnIdCheck.setOnAction(e->idCheckEvent());
		pwCheckAction(txtPw, lblPw);
		comparePw(txtPw , txtPwCheck , lblPwCheck);
		btnJoin.setOnAction(e->joinAction());
		btnJoin.setDisable(true);
	}
	
	public void joinAction() {
		if(!userDao.isJoined(txtId.getText().toString()) && txtPw.getText().toString().length() >=4 &&txtPw.getText().toString().equals(txtPwCheck.getText().toString())){
			if(txtAsk.getText().toString().length()>0 && txtNickName.getText().toString().length()>0 &&txtAnswer.getText().toString().length()>0) {
				User user = new User();
				user.setUserId(txtId.getText().toString());
				user.setUserPw(txtPw.getText().toString());
				user.setUserAnswer(txtAnswer.getText().toString());
				user.setUserAsk(txtAsk.getText().toString());
				user.setUserNickName(txtNickName.getText().toString());
				int result = userDao.join(user);
				if(result == 1) {
					joinSuccessAlert();
				}else {
					joinFailAlert();
				}
			}else {
				joinFailAlert();
			}		
		}else {
			joinFailAlert();
		}
	}
	
	public void comparePw(PasswordField pwField , PasswordField pwCheckField , Label lblPwCheck) {
		pwCheckField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.equals(pwField.getText().toString())) {
					lblPwCheck.setText("????????????");
					lblPwCheck.setTextFill(Color.BLUE);
				}
				else {
					lblPwCheck.setText("??????????????????");
					lblPwCheck.setTextFill(Color.RED);
				}
				
			}
		});
	}
	
	public void checkId(TextField txtId , String id) {
		txtId.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!txtId.getText().toString().equals(id)) {
					btnJoin.setDisable(true);
				}
				
			}
		});
	}
	
	public void pwCheckAction(PasswordField pwField , Label lbl) {
			pwField.textProperty().addListener(new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.length() >0) {
					if(pwCheckEvent(newValue)) {				
						lbl.setText("4????????? ????????? ??????");
						lbl.setTextFill(Color.BLUE);
						
					}else {
						lbl.setText("4????????? ????????????");
						lbl.setTextFill(Color.RED);
					}
				}else {
					lbl.setText("??????????????????");
					lbl.setTextFill(Color.BLACK);
				}
			}
		});
	}
	
	public boolean pwCheckEvent(String pw) {
		if(pw.length()>=4) {
			return true;
		}
		return false;
	}
	
	public void idCheckEvent() {
		
		String id = txtId.getText().toString();
		if(id.length()>=2) {
			System.out.println(txtId.getText().toString());
			boolean able =  userDao.isJoined(id);
			System.out.println(able);
			if(able) {
				lblId.setText(id+"??? ???????????????");
			}else {
				lblId.setText(id+"????????????");
				chkId = txtId.getText().toString();
				checkId(txtId ,chkId);
				btnJoin.setDisable(false);
				
			}
		}else {
			ESCAlert al = new ESCAlert();
			al.basicAlertShow("???????????? 2????????????", "2?????? ???????????????????????? ");
		}
	}
	
	
	
	public void escEvent(KeyEvent e) {
		KeyCode key  =  e.getCode();
		if(key.equals(KeyCode.ESCAPE)) {
			alertShow();
		}
	}

	public void cancelAction() {
		try {
			//Parent main = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));				
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/Login.fxml"));
			Parent main = loader.load();
			
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnCancel.getScene().getWindow();
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
		
		}
	}
	public void joinSuccessAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("???????????? ?????????????????? ");
		alert.setHeaderText("????????? ??? ????????????");
	
	
		Optional<ButtonType> result = alert.showAndWait();
		
	
		if(result.get() == ButtonType.OK) {
			goLogin();
		}
	}
	public void joinFailAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("???????????? ?????? ");
		alert.setHeaderText("????????? ???");
	
	
		alert.showAndWait();
		
	
		
	}
	public void alertShow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("??????!");
		alert.setHeaderText("?????? ?????? ?????????????");
		alert.setContentText("??? ?????? ?????? ??????? ?????? ???????");
	
		Optional<ButtonType> result = alert.showAndWait();
		
	
		if(result.get() == ButtonType.OK) {
			goLogin();
		}else if(result.get() == ButtonType.NO){
			alert.close();
		}
	}
	public void goLogin() {
		try {
			//Parent main = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/Login.fxml"));
			Parent main = loader.load();
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnCancel.getScene().getWindow();
			stage.setScene(mainScene);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("???????????? ???????????? ?????? ");
		}
	}
	
}
