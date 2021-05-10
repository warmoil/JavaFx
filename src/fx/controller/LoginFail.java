package fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginFail implements Initializable {

	@FXML Label lblMsg;
	@FXML Button btnOk;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnOk.setOnAction(e->btnOkAction(e));
	}
	public void btnOkAction(ActionEvent e) {
		Stage root = (Stage)btnOk.getScene().getWindow();
		root.close();
	}
	public void failReason(int reason) {
		if(reason == 0) {
			lblMsg.setText("비번이 틀렸다 아이디는 맞습니다 힘내세요!\n저한테 문의하면 싼가격에 비번을 팔겠습니다 ");
		}else if(reason == -1){
			lblMsg.setText("그런아이디 없습니다 .");
		}
		
	}
}
