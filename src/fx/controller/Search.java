package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.ldap.Rdn;

import alert.ESCAlert;
import db.CriminalDAO;
import db.Report;
import db.ReportDAO;
import db.User;
import db.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Search implements Initializable {
	@FXML private BorderPane searchPane;
	@FXML private Label lblSearchText , lblSearchNum ;
	@FXML private Button btnReport;
	@FXML private TextArea resultArea;
	private CriminalDAO cDao = new CriminalDAO();
	private String crimId , cNum;
	private String userId;
	private UserDAO dao = new UserDAO();
	private ReportDAO rDao = new ReportDAO();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->setLableText());
		btnReport.setOnAction(e->goReport());
		
	}


	public void setLableText() {
		lblSearchText.setText(crimId+":의 사기 조회 결과");
		lblSearchNum.setText(cNum+"회 조회됨");
		
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void goReport() {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Enroll.fxml"));
			 Parent reportPene =loader.load();
			
			Enroll enrollCon = loader.getController();
			enrollCon.setReporter(userId);
			Scene centerScene = new Scene(reportPene);
			Stage stage = (Stage)btnReport.getScene().getWindow();
			stage.setScene(centerScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("시발");
		}
	}
	
	public void doSearching(String crimId,String userId) {
		 setUserId(userId);
		 this.crimId = crimId;
		 cDao.setCrimNum(crimId);
		 int reportedNum = rDao.getReportedNum(crimId);
		 int isReportedNum = rDao.isreported(crimId);
		 int getCNum = cDao.getCrimNum(crimId);
		 if(reportedNum>0) {
			 Report[] titles = rDao.getReportTitle(crimId);
			// Hyperlink[] links = new Hyperlink[titles.length];
			 if(titles.length>0) {
				 for(int i =0; i<titles.length;i++) {
					
					 resultArea.appendText(titles[i].getTitle()+"\n");
				 }
				
			 }
		 }
		 resultArea.setEditable(false);
		 if(isReportedNum != -1 && getCNum == -1) {
			 cDao.insertCriminal(crimId);
		 }
		 if(cDao.getCrimNum(crimId) == -1) {
			 cNum = "0";
		 }else {
		 cNum = Integer.toString(cDao.getCrimNum(crimId));
		 }
	}
	
}
