package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import alert.ESCAlert;
import db.MyReportData;
import db.ReportDAO;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MyReport implements Initializable {

	@FXML private BorderPane myReportPane;
	@FXML private TableView<MyReportData> tViewContent;
	@FXML private Button btnDelete;
	@FXML private Label lblChk;
	@FXML private TableColumn<MyReportData , String> tColcName,tColcTitle;
	@FXML private TableColumn<MyReportData, Integer> tColcnum;
	//@FXML private TableColumn<MyReportData, Boolean> tColChk= new TableColumn<MyReportData,Boolean>("check"); 
	@FXML private TableColumn<MyReportData, Boolean> tColChk;
	private ReportDAO rDao = new ReportDAO();
	private String userId;
    private Vector<Integer> checkedNum = new Vector<Integer>();
    private Vector<String> checkedName = new Vector<String>();
    private String lblText = new String();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Platform.runLater(()->getDatas());
		btnDelete.setOnAction(e->deleteAction());
	}

	public int deleteAction() {
		if(checkedNum.size()>0) {
			ESCAlert alert = new ESCAlert();
			String alTitle = Integer.toString(checkedNum.size())+"의 항목을  삭제합니다 ";
			String alHeader = "";
			for(String name:checkedName) {
				alHeader += name+",";
			}
			alHeader += "가 삭제됩니다 ";
			String alContent = "정말로 삭제됩니다 ";
			
			boolean isDelete = alert.costomAlert(userId, alTitle, alHeader, alContent);
			if(isDelete) {
				int[] del = new int[checkedNum.size()];
				int i = 0;
				for(Integer check : checkedNum) {
					del[i] = check;
					i++;
				}
				int result = rDao.deleteReporting(del);
				if(result == 1) {
					alert.basicAlertShow("삭제완료" , checkedNum.size()+"개를 삭제했습니다 ");
					try {
						//삭제 하고 새로고침인것처럼 보이기위해 				
						 FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
						 Parent main =loader.load();
							
						Main mainCon = loader.getController();
						mainCon.setUser(userId);
						mainCon.viewMyReport(userId);
						//유저 새팅 + 이 컨트롤러 다시열게 메소드 사용 
						Scene mainScene = new Scene(main);
						Stage stage = (Stage)btnDelete.getScene().getWindow();
						stage.setScene(mainScene);
						
					} catch(IOException e2) {
						e2.printStackTrace();
						System.out.println("실패");
					}
				}
				else {
					alert.basicAlertShow("삭제실패", "실패했어요 ㅈㅅ ㅎ..");
				}
			}
		
		}
		else {
			ESCAlert al = new ESCAlert();
			String alTitle = "최소 한개 이상이 선택되어야합니다";
			String alHeader = "삭제 불가 ";
			al.basicAlertShow(alTitle, alHeader);
		}
		
		return 0;
	}
	
	public void getDatas() {
		
		ObservableList<MyReportData> datas = FXCollections.observableArrayList();
		MyReportData[] reports = rDao.getMyReportInfo(userId);
		
		if(reports != null) {
			for(MyReportData my : reports) {
				System.out.println(my.getcId());
			}
		
			
			//여기는 테이블에 데이터 삽입하는곳 (테이블뷰가 보여주게 하는곳 칼럼 데이터 삽입)
			tColcName.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("cId"));
			tColcnum.setCellValueFactory(new PropertyValueFactory<MyReportData, Integer>("num"));
			tColcTitle.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("title")); 
	        // 체크박스를 Cell에 표시
	      
		    tColChk.setCellValueFactory(new PropertyValueFactory<MyReportData,Boolean>("check"));
		    tColChk.setCellFactory(column -> new TableCell<MyReportData, Boolean>(){
	        
		    public void updateItem(Boolean check, boolean empty) {
	             super.updateItem(check, empty);
	             if (check == null || empty) {
	              setGraphic(null);
	             } else {
	              CheckBox box = new CheckBox();
	              BooleanProperty checked = (BooleanProperty)column.getCellObservableValue(getIndex());
	              
	              MyReportData my = (MyReportData)column.getTableView().getItems().get(getIndex());
	       
	              if (checked.get()){
	            	  if(!checkedNum.contains(my.getNum())) {
	            		  checkedNum.add(my.getNum());
	            		  checkedName.add(my.getcId());
	            		  lblText = "";
	            		  for(int i=0; i < checkedName.size();i++) {
	            			  lblText += checkedNum.get(i).toString()+"번 :"+checkedName.get(i)+"    ";
	            		  }
	            		  lblChk.setText(lblText);
	            	  }
	            	  
	            	  
	            
	              } else {
	            	  if(checkedNum.contains(my.getNum())) {
	            		  for(int i =0; i<checkedNum.size();i++) {
		            		  if(checkedNum.get(i) == my.getNum()) {
			            		  checkedNum.remove(i);
			            		  checkedName.remove(i);
			            		  
			            	  }
		            	  }
	            		  lblText = "";
	            		  for(int i=0; i < checkedName.size();i++) {
	            			  lblText += checkedNum.get(i).toString()+"번 :"+checkedName.get(i)+"    ";
	            		  }
	            		  lblChk.setText(lblText);
	            	  }
	            	  System.out.println(my.getNum()+"번");
	            	  System.out.println(my.getcId()+" is Unchecked!");
	              }
	              box.setSelected(checked.get());
	              System.out.println(checkedNum.size()+"사이즈");
	              for(Integer ch : checkedNum) {
	            	  System.out.println(ch.intValue()+"번");
	              }
	              box.selectedProperty().bindBidirectional(checked);
	             setGraphic(box);
	             }
	            }
	           }
       );
      	
	      	     
           tViewContent.setEditable(true);
          
           datas.setAll(reports);
		   tViewContent.setItems(datas);
	    }
	
	}	
	
	public void setUser(String userId) {
		this.userId =  userId;
	}
	
}
