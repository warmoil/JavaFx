package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import alert.ESCAlert;
import db.ReportData;
import db.UserDAO;
import db.UserData;
import fx.AppTest;
import db.ReportDAO;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ItsMine implements Initializable {
	
	@FXML private  BorderPane ItsMine;
	@FXML private TextField txtSearch;
	@FXML private Button btnSearch , btnDelete;
	@FXML private Label lblSearchType , lblNum;
	@FXML private ToggleGroup type;
	@FXML private RadioButton rReport , rUser;
	//@FXML private TableView tView;
	//private TableView tView;
	private TableView<UserData> userView = new TableView<UserData>();
	private TableView<ReportData> reportView = new TableView<ReportData>();
	private ReportDAO rDao = new ReportDAO();
	private UserDAO uDao = new UserDAO();
	private Vector<String> userVec = new Vector<String>();
	private Vector<Integer> reportVec = new Vector<Integer>();
	private String chkstr;
	private String onOpenType = "";
	ESCAlert al = new ESCAlert();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		radioChange();

		if(onOpenType.equals("report")||onOpenType.length() == 0) {
			showReport();
			ItsMine.setCenter(reportView);
		}else {
			showUser();
			ItsMine.setCenter(userView);
			
		}
		btnDelete.setOnAction(e->deleteAction());
	}

	public void doSearch() {
		if(rReport.isSelected()) {
			String rSearch = txtSearch.getText().toString();
			
		}else {
			
		}
	}

	public void deleteAction() {
		if(userVec.size() != 0) {
			String[] users = new String[userVec.size()];
			String userIds = "";
			int num = users.length;
			for(int i =0; i < users.length; i++) {
				users[i] = userVec.get(i);
				userIds += userVec.get(i) +"  ,";
			}
			
			boolean del = al.costomAlert(userVec.size()+"개를 삭제", userVec.size()+"의 항목을 삭제합니다 ", userIds, "확인시 삭제 합니다 ");
			if(del) {
				int row = uDao.deleteUser(users);
				al.basicAlertShow(num+"개의 유저가 삭제되었습니다 ", num+"명의 유저를 잃었습니다 .");
				refresh("user");
			}else {
				al.basicAlertShow("취소했습니다 ", "다행");
			}
		}else if(reportVec.size() != 0) {
			int[] reNums = new int[reportVec.size()];
			int reNum = reportVec.size();
			String nums  = "";
			for(int i =0; i<reNums.length;i++) {
				reNums[i] = reportVec.get(i);
				nums += reportVec.get(i) + " ,";
			}
			
			boolean del = al.costomAlert("총:"+reNum+"개의 게시글을 삭제합니다", "삭제 하겠습니까?", "목록은"+nums, "확인시 삭제합니다 ");
			if(del) {
				int row = rDao.deleteReporting(reNums);
				if(row>0) {
					al.basicAlertShow(reNum+"개의 개시물을 삭제했습니다", "삭제완료 ");
					refresh("report");
				}else {
					al.basicAlertShow("삭제실패 ", "삭제 실패하였습니다 ");
				}
			}
		}else {
			al.basicAlertShow("선택없음", "1개이상은 선택해야합니다 ");
		}
	}
	
	public void refresh(String type) {
		try {
			 //FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ItsMine.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AppTest.class.getResource("fxml/ItsMine.fxml"));
			Parent main =loader.load();
				
			ItsMine mineCon = loader.getController();
			
			mineCon.seleted(type);
			Scene mainScene = new Scene(main);
			
			Stage stage = (Stage)btnDelete.getScene().getWindow();
			
			stage.setScene(mainScene);
			
		} catch(IOException e2) {
			e2.printStackTrace();
			System.out.println("관리자 리프레쉬 실패 ");
		}
	}
	//Toggle 라디오 리스너 
	//여기에서 얻는 값으로 user 또는 
	public void seleted(String type) {
		if(type.equals("user")) {
			rUser.setSelected(true);
			ItsMine.setCenter(userView);
		}else if(type.equals("report")) {
			rReport.setSelected(true);
			showReport();
			ItsMine.setCenter(reportView);
		}else {
			System.out.println("오류");
		}
	}
	public void radioChange() {
		type.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				String searchType = newValue.getUserData().toString();
				if(searchType.equals("user")) {
					lblNum.setText("");
					showUser();
					ItsMine.setCenter(userView);
					System.out.println("user");
					System.out.println("reportVecSize:"+reportVec.size());
					if(reportVec.size() !=0) {
						reportVec.clear();
					}
					lblSearchType.setText("user 삭제");
				}else if(searchType.equals("report")){
					lblNum.setText("");
					showReport();
					ItsMine.setCenter(reportView);
					if(userVec.size() !=0) {
						userVec.clear();
					}
					System.out.println("report");
					System.out.println("userVecSize:"+userVec.size());
					lblSearchType.setText("신고 삭제");
				}
			}
		});
	}
	
	
	public void showUser() {
		ObservableList<UserData> datas = FXCollections.observableArrayList();
		UserData[] users = uDao.getAllUserData();
		
		
		TableColumn<UserData, Boolean> colChk = new TableColumn<UserData, Boolean>("체크");
		colChk.setCellValueFactory(new PropertyValueFactory<UserData, Boolean>("check"));
		
		TableColumn<UserData, String> colId = new TableColumn<UserData, String>("유저 id");
		colId.setCellValueFactory(new PropertyValueFactory<UserData, String>("userId"));
		
		TableColumn<UserData, String> colPw = new TableColumn<UserData, String>("유저 Pw");
		colPw.setCellValueFactory(new PropertyValueFactory<UserData, String>("userPw"));
		
		TableColumn<UserData, String> colNick = new TableColumn<UserData, String>("닉네임");
		colNick.setCellValueFactory(new PropertyValueFactory<UserData, String>("nickName"));
				
		TableColumn<UserData, String> colAsk = new TableColumn<UserData, String>("질문");
		colAsk.setCellValueFactory(new PropertyValueFactory<UserData, String>("ask"));
		
		TableColumn<UserData, String> colAnswer = new TableColumn<UserData, String>("답");
		colAnswer.setCellValueFactory(new PropertyValueFactory<UserData, String>("answer"));
		colAnswer.setPrefWidth(700);
		
		//체크 박스 만들즈 아 
		colChk.setCellFactory(col -> new TableCell<UserData, Boolean>(){
			public void updateItem(Boolean check , boolean empty) {
				super.updateItem(check, empty);
				if(check == null || empty) {
					setGraphic(null);
				}else {
					CheckBox box = new CheckBox();
					BooleanProperty checked = (BooleanProperty)col.getCellObservableValue(getIndex());
					UserData my = (UserData)col.getTableView().getItems().get(getIndex());
					if(checked.get()) {
						if(!userVec.contains(my.getUserId())) {
							userVec.add(my.getUserId());
						}
						chkstr = "";
						for(int i = 0; i < userVec.size(); i++) {
							chkstr  += userVec.get(i)+" ,";
							System.out.print(i+"  ,, ");
						}
						lblNum.setText(chkstr);
					}else {
						if(userVec.contains(my.getUserId())) {
							for(int i=0; i < userVec.size(); i++) {
								if(userVec.get(i).equals(my.getUserId())) {
									userVec.remove(i);
									System.out.print("두번쨰 i"+i);
								}
							}
							chkstr = "";
							for(int i =0; i< userVec.size(); i++) {
								chkstr += userVec.get(i)+" ,";
							}
							lblNum.setText(chkstr);
						}
					}
					box.setSelected(checked.get());
					box.selectedProperty().bindBidirectional(checked);
					setGraphic(box);
				}
			}
		}); // 여기 까지 
		System.out.println("user테이블뷰 호출됨");
		userView.setItems(datas);
		datas.setAll(users);
		userView.getColumns().addAll(colChk,colId,colNick,colAsk,colAnswer); //컬럼추가 
		userView.setEditable(true);
	}

	public void showReport() {
		
		ObservableList<ReportData> datas = FXCollections.observableArrayList();
		ReportData[] reports = rDao.getAllReportData();
		
		
		if(reports != null) {
			for(ReportData my : reports) {
				System.out.println(my.getcId());
			}
			TableColumn<ReportData, Boolean> colChk = new TableColumn<ReportData, Boolean>("체크");
			colChk.setCellValueFactory(new PropertyValueFactory<ReportData, Boolean>("check"));
			TableColumn<ReportData, String> colReporter = new TableColumn<ReportData, String>("신고자");
			colReporter.setCellValueFactory(new PropertyValueFactory<ReportData, String>("reporter"));
			TableColumn<ReportData, String> colCid = new TableColumn<ReportData, String>("사기꾼 닉네임"); //컬럼 새로 만들기 
			colCid.setCellValueFactory(new PropertyValueFactory<ReportData, String>("cId"));       //컬럼에 셀값 넣기 
			TableColumn<ReportData, Integer> colNum = new TableColumn<ReportData, Integer>("사건 번호 ");
			colNum.setCellValueFactory(new PropertyValueFactory<ReportData, Integer>("num"));
			TableColumn<ReportData, String> colTitle = new TableColumn<ReportData, String>("죄목");
			colTitle.setCellValueFactory(new PropertyValueFactory<ReportData, String>("title"));
			TableColumn<ReportData, String> colContent = new TableColumn<ReportData, String>("내용");
			colContent.setCellValueFactory(new PropertyValueFactory<ReportData, String>("content"));
			colContent.setPrefWidth(700);  //내용은 마지막이니까 그냥 끝까지 
			
			
			//체크박스랑 리스너 를 위한 코드 아직도 잘모름
			colChk.setCellFactory(col -> new TableCell<ReportData, Boolean>(){
				public void updateItem(Boolean check , boolean empty) {
					super.updateItem(check, empty);
					if(check == null || empty) {
						setGraphic(null);
					}else {
						CheckBox box = new CheckBox();
						BooleanProperty checked = (BooleanProperty)col.getCellObservableValue(getIndex());
						ReportData my = (ReportData)col.getTableView().getItems().get(getIndex());
						if(checked.get()) {
							//System.out.println(my.getcId()+"체크됨");
							chkstr = "";
							if(!reportVec.contains(my.getNum())) {
								reportVec.add(my.getNum());	
							}
							for(int i =0; i <reportVec.size();i++) {
								chkstr += reportVec.get(i) + " ,";
							}
							lblNum.setText(chkstr);
						}else {
							chkstr = "";
							if(reportVec.contains(my.getNum())) {
								for(int i =0; i<reportVec.size();i++) {
									if(reportVec.get(i) == my.getNum()) {
										reportVec.remove(i);
									}
								}
								for(int i =0; i <reportVec.size();i++) {
									chkstr += reportVec.get(i) + " ,";
								}
								lblNum.setText(chkstr);
							}
						}
						box.setSelected(checked.get());
						box.selectedProperty().bindBidirectional(checked);
						setGraphic(box);
					}
				}
			});
			reportView.setItems(datas);
			System.out.println("리포트 테이블뷰 호출됨");
			datas.setAll(reports);
			reportView.getColumns().addAll(colChk,colNum,colReporter,colCid,colTitle,colContent); //컬럼추가 
			reportView.setEditable(true);
		}	
		
	}
	
}
