package alert;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ESCAlert {
	public ESCAlert() {
		
	}
	public void escAlertShow() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("간다고?");
		alert.setHeaderText("진짜?");
		alert.setContentText("님 나갈꺼?? 종료??");
	
		Optional<ButtonType> result = alert.showAndWait();
		
	
		if(result.get() == ButtonType.OK) {
			Platform.exit();
		}else if(result.get() == ButtonType.NO){
			alert.close();
		}
	}
	
}
