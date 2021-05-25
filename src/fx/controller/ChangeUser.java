package fx.controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeUser implements Initializable {
	@FXML private AnchorPane changePane;
	@FXML private Label lblId,lblNick,lblAsk,lblAnswer;
	@FXML private Button btnNick , btnPassword, btnAsk , btnAnswer ,btnGoMain;
	@FXML private TextField txtNick , txtAsk , txtAnswer;
	@FXML private PasswordField txtPw , txtPwChk;
	private String userId ;
	private User user;
	private UserDAO dao = new UserDAO();
	private ESCAlert alert = new ESCAlert();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->refresh(userId));
		btnNick.setOnAction(e->nickChange());
		btnAnswer.setOnAction(e->answerChange());
		btnAsk.setOnAction(e->askChange());
		btnPassword.setOnAction(e->pwChange());
		btnGoMain.setOnAction(e->goMain(userId));
		changePane.setOnKeyPressed(e->escKeyEvent(e));
	}
	public void escKeyEvent(KeyEvent e) {
		KeyCode key = e.getCode();
		if(key.equals(KeyCode.ESCAPE)) {
			
			if(alert.costomAlert(userId, "메인으로?", "갈까요?", "쓸거없음")) {
				goMain(userId);
			}
		}
	}
	public void refresh(String userId) {
		setUser(userId);
		lblId.setText(user.getUserId());
		lblNick.setText(user.getUserNickName());
		lblAsk.setText(user.getUserAsk());
		lblAnswer.setText(user.getUserAnswer());
		
	}
	public void pwChange() {
		int oldPwNum = user.getUserPw().length();
		String pw = txtPw.getText().toString();
		String pwChk = txtPwChk.getText().toString();
		if((pw.length() >=4 && pwChk.length()>=4)) {
			if(pw.equals(pwChk)) {
				user.setUserPw(pw);
				if(dao.isUserUpdate(user)) {
					alert.basicAlertShow("비밀번호 변경", oldPwNum+"자리 에서 -> "+pw.length()+"자리수로 변경됨");
				}else {
					alert.basicAlertShow("비번 변경실패", "알수없는 오류 ");
				}
			}else {
				alert.basicAlertShow("비번이 서로다릅니다", "잘 확인해 주세요 ");
			}
		}else {
			alert.basicAlertShow("비번은", "최소4글자이상 입력해주세요");
		}
		txtPw.setText("");
		txtPwChk.setText("");
	}
	public void nickChange() {
		if(txtNick.getText().toString().length()>=2) {
			String header = user.getUserNickName() +" -> ";
			user.setUserNickName(txtNick.getText().toString());
			if(dao.isUserUpdate(user)) {
			refresh(userId);
			header += user.getUserNickName();
			alert.basicAlertShow("변경됨", header);
			}else {
				alert.basicAlertShow("실패", "실패 ㅠ");
			}
		}
		txtNick.setText("");
	}
	public void answerChange() {
		if(txtAnswer.getText().toString().length() >=1) {
			String header = user.getUserAnswer() +"  -> ";
			user.setUserAnswer(txtAnswer.getText().toString());
			if(dao.isUserUpdate(user)) {
				refresh(userId);
				header += user.getUserAnswer();
				alert.basicAlertShow("변경됨", header);
			}else {
				alert.basicAlertShow("실패", "글자 제한때문일듯 ");
			}
		}else {
			alert.basicAlertShow("답변은", "1글자이상 적으세요 ");
		}
		txtAnswer.setText("");
	}
	
	public void askChange() {
		if(txtAsk.getText().toString().length() >=1) {
			String header = user.getUserAsk() +"  -> ";
			user.setUserAsk(txtAsk.getText().toString());
			if(dao.isUserUpdate(user)) {
				refresh(userId);
				header += user.getUserAsk();
				alert.basicAlertShow("변경됨", header);
			}else {
				alert.basicAlertShow("실패", "글자 제한때문일듯 ");
			}
		}else {
			alert.basicAlertShow("질문은", "최소 1글자 이상 ");
		}
		txtAsk.setText("");
	}
	public void goMain(String userId) {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
			 Parent main =loader.load();
				
			Main mainCon = loader.getController();
			
			mainCon.setUser(userId);
			Scene mainScene = new Scene(main);
			
			Stage stage = (Stage)btnGoMain.getScene().getWindow();
			
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("로그인실패");
		}
	}
	public void setUser(String userId) {
		this.userId = userId;
		System.out.println("유저 id:"+userId);
		this.user = dao.getUserInfo(userId);
	}
}
