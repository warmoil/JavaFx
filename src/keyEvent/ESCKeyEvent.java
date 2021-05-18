package keyEvent;

import alert.ESCAlert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ESCKeyEvent {
	
	public void escKeyEvent(KeyEvent e) {
		KeyCode key = e.getCode();
		if(key.equals(KeyCode.ESCAPE)) {
			ESCAlert al = new ESCAlert();
			al.escAlertShow();
		}
	}
	
}

