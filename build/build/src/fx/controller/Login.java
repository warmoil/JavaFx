package fx.controller;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.jar.JarFile;

import alert.ESCAlert;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login implements Initializable {

	@FXML Button btnLogin,btnRegister,btnCancel;
	@FXML TextField txtId;
	@FXML PasswordField txtPw;
	@FXML AnchorPane loginPane;
	private Stage pop;
	private ESCAlert alert = new ESCAlert();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->txtId.requestFocus());//txtId에 포커스 주기 실행후에 로드가다되면 실행하라는 뜻 
		loginPane.setOnKeyPressed(e->escKeyEvent(e));
		btnCancel.setOnAction(e->btnCancelAction(e));
		btnLogin.setOnAction(e->btnLoginAction(e));
		btnRegister.setOnAction(e->btnRegisteerAction(e));
		txtPw.setOnKeyPressed(e->enterKeyEvent(e));	
		Platform.runLater(()->test());

	}
	public void test() {
		Hyperlink link = new Hyperlink("plz");
		link.setLayoutX(10);
		link.setLayoutY(10);
		loginPane.getChildren().add(link);
	}
	

	public void loginAction()  {
		User user = new User();
		user.setUserId(txtId.getText().toString());
		user.setUserPw(txtPw.getText().toString());
		String id = user.getUserId();
		String pw = user.getUserPw();
		if(id.length() == 0) {
			alert.basicAlertShow("아이디 안치셨네요 ", "아이디 입력하세요");
		}else if(id.length()>=1 && pw.length() == 0) {
			alert.basicAlertShow("비번을 안치셨네요", "비번 입력하세요 ");
		}else {
		UserDAO userDao = new UserDAO();
		int isLog = userDao.login(id, pw);
		System.out.println("아이디:"+id+"\n pw:"+pw);
		if(isLog == 1) {
			try {
				/*
				//Class.forName("fx.fxml.Main.fxml");
				//URL fxmlPath = new File("src/fx/fxml/Main.fxml").toURL();
				FXMLLoader loader = new FXMLLoader(Class.forName("fx.controller.Main").getResource("../fxml/Main.fxml"));
				//System.out.println(rootPath);
				//FXMLLoader loader = FXMLLoader.load(getClass().getResource("../fxml/Main.fxml"));
				//FXMLLoader loader = new FXMLLoader(Class.forName("fx.controller.Main").getResource(rootPath+"../fxml/Main.fxml"));
				
				BorderPane main  = (BorderPane)loader.load();		
				Main mainCon  = loader.getController();
				mainCon.setUser(user);
				Scene mainScene = new Scene(main);
				
				Stage stage = (Stage)btnLogin.getScene().getWindow();
				
				stage.setScene(mainScene);
				*/
				
			
				//FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(AppMainTheCheat.class.getResource("fxml/Main.fxml"));
				Parent main =loader.load();
				Main mainCon = loader.getController();
				
				mainCon.setUser(user);
				Scene mainScene = new Scene(main);
				
				Stage stage = (Stage)btnLogin.getScene().getWindow();
				
				stage.setScene(mainScene);
				
			} catch(IOException e2) {
				e2.printStackTrace();
				System.out.println("로그인실패");
			}
		}else{
				Stage mainStage = (Stage)btnLogin.getScene().getWindow(); //이거 
				
				pop = new Stage(StageStyle.DECORATED);
				pop.initModality(Modality.WINDOW_MODAL);
				pop.initOwner(mainStage);
				  try {
					   FXMLLoader loader = new FXMLLoader();
			          
			       //    loader.setLocation(getClass().getResource("../fxml/LoginFail.fxml"));
					   loader.setLocation(AppMainTheCheat.class.getResource("fxml/LoginFail.fxml"));
			           Parent root = (Parent)loader.load();
			           LoginFail loginFail = loader.getController();
			           
			           loginFail.failReason(isLog);
			            // 씬에 추가
			            Scene sc = new Scene(root);
			            pop.setScene(sc);
			            pop.setTitle("this is popUp이다 쩔지?");
			            pop.setResizable(false); // 창 사이즈 조절 차단
			             
			            // 보여주기
			            pop.show();
			             
			        } catch (IOException e2) {
			            e2.printStackTrace();
			        }
			}
		}
	}
	
	public void enterKeyEvent(KeyEvent e) {
		KeyCode key = e.getCode();
		if(key.equals(KeyCode.ENTER)) {
			loginAction();
		}
	}
	public void escKeyEvent(KeyEvent e) {
		KeyCode key = e.getCode();
		if(key.equals(KeyCode.ESCAPE)) {
			
			escAlertShow();
		}
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
	public void btnLoginAction(ActionEvent e)  {
		loginAction();

	}
	public void btnRegisteerAction(ActionEvent e) {
		try {
			Parent main = FXMLLoader.load(getClass().getResource("../fxml/Register.fxml"));				
			Scene mainScene = new Scene(main);
			Stage stage = (Stage)btnRegister.getScene().getWindow();
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("회원가입 이동실패");
		}
	}
	public void btnCancelAction(ActionEvent e) {
		Platform.exit();
	}
	
	@FXML
	private void sendData(int reason) throws IOException {
	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("popup.fxml"));
	  Parent root = (Parent) loader.load();
	  Scene scene = new Scene(root);
	  
	  LoginFail popCon = loader.getController();
	  popCon.failReason(reason);
	  
	  Stage stage = new Stage();
	  stage.setScene(scene);
	  stage.show();
	}
	
}
