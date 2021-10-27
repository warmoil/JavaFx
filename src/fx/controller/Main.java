package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import alert.ESCAlert;
import db.ReportDAO;
import db.User;
import db.UserDAO;
import fx.AppMainTheCheat;
import fx.StageStore;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main implements Initializable {

	@FXML private TextField txtfieldSearch;
	@FXML private Button btnSearch,btnGoLogin,btnRefesh,btnChangeUser;
	@FXML private BorderPane mainPane;
	@FXML private Label lblUserId,lblChk;
	@FXML private Label lblReportCnt;
	private User user;
	private UserDAO dao = new UserDAO();
	private  ReportDAO rDao = new ReportDAO();
	private String userId;
	
	String nickName;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//StageStore.stage.setResizable(true);
		txtfieldSearch.setOnKeyPressed(e->enterKeyEvent(e));
		Platform.runLater(()->txtfieldSearch.focusedProperty());
		btnGoLogin.setOnAction(e->goLoginAlert());
		Platform.runLater(()->setReportingNumHyperLink(userId));
		Platform.runLater(()->lblChk.setText(nickName));
		mainPane.setOnKeyPressed(e->escKeyEvent(e));
		btnSearch.setOnAction(e->searchingAction());
		Platform.runLater(()->test(userId));
		btnRefesh.setOnAction(e->refresh());
		btnChangeUser.setOnAction(e->goChangeUser());
	}
	
	public void goChangeUser() {
		try {
			 //FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ChangeUser.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/ChangeUser.fxml"));
			 Parent main =loader.load();
				
			ChangeUser changeCon = loader.getController();
			
			changeCon.setUser(userId);
			Scene mainScene = new Scene(main);
			
			Stage stage = (Stage)btnChangeUser.getScene().getWindow();
			
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("로그인실패");
		}
	}
	
	public void setReportingNumHyperLink(String userId) {
		int num = rDao.getReportingNum(userId);
		System.out.println("num은"+num);
		if(num >0) {
			Hyperlink link = new Hyperlink("hello");
			link.setLayoutX(10);
			link.setLayoutY(10);
			lblReportCnt.setText("");
			link.setText(Integer.toString(num)+"회");
			link.setTextFill(Color.RED);
			link.setLayoutX(lblReportCnt.getLayoutX());
			link.setLayoutY(lblReportCnt.getLayoutY()-3);
			link.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					viewMyReport(userId);
				}
			});
			AnchorPane an = (AnchorPane) mainPane.getTop();
			an.getChildren().add(link);
			
		}else {
			lblReportCnt.setText(Integer.toString(num));
		}
		
	}
	public void viewMyReport(String userId) {
		try {
			 //FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MyReport.fxml"));
			FXMLLoader loader =new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/MyReport.fxml"));
			Parent center =loader.load();
			
			MyReport reportCon = loader.getController();
			System.out.println(userId);
			reportCon.setUser(userId);
			
			mainPane.setCenter(center);
			
		} catch(IOException e2) {
			e2.printStackTrace();
		}
	
}
	public void test(String userId) {
		System.out.println(userId);
		user = dao.getUserInfo(userId);
		System.out.println(user.getUserAsk()+"\n"+user.getUserAnswer());
	}
	
	public void setUser(String userId) {
		nickName = dao.getUserNickName(userId);
		this.userId = userId;
		this.user = dao.getUserInfo(userId);
		setReportingNumHyperLink(userId);
	}
	
	public void setUser(User user) {
		
		nickName = dao.getUserNickName(user);
		this.userId = user.getUserId();
		this.user  = user;
		setReportingNumHyperLink(userId);
		
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
			// FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Search.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/Search.fxml"));
			 Parent center =loader.load();
			
			Search searchCon = loader.getController();
			searchCon.setUserId(userId);
			System.out.println(userId);
			searchCon.doSearching(txtfieldSearch.getText().toString(),userId);
		
			mainPane.setCenter(center);
			
		} catch(IOException e2) {
			e2.printStackTrace();
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
	
	public void refresh() {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
			 Parent main =loader.load();
				
			Main mainCon = loader.getController();
			
			mainCon.setUser(userId);
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnRefesh.getScene().getWindow();
			stage.setScene(mainScene);
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("실패");
		}
	}
	public void goLogin() {
		try {
			//Parent main = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppMainTheCheat.class.getResource("fxml/Login.fxml"));
			Parent main = loader.load();
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnGoLogin.getScene().getWindow();
			stage.setScene(mainScene);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("로그인창 진입 실패 ");
		}
	}
	
}
