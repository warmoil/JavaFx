package alert;

import java.io.IOException;
import java.util.Optional;

import fx.controller.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ESCAlert {
	public ESCAlert() {
		
	}
	public void basicAlertShow(String title , String header) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.showAndWait();
	}
	public void escAlertShow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("프로그램을 종료합니다");
		alert.setHeaderText("정말 종료할까요?");
		alert.setContentText("ㅠㅠ");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Platform.exit();
		}else if(result.get() == ButtonType.NO){
			alert.close();
		}
		
	}
	public boolean costomAlert(String userId,String title , String header , String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
	
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		}else if(result.get() == ButtonType.NO){
			alert.close();
			return false;
		}
		return false;
	}
	
}
