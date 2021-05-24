package fx.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

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
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class MyReport implements Initializable {

	@FXML private BorderPane myReportPane;
	@FXML private TableView<MyReportData> tViewContent;
	private ReportDAO rDao = new ReportDAO();
	private String userId;
    private Vector<Integer> checkedNum = new Vector<Integer>();
	@FXML private TableColumn<MyReportData , String> tColcName,tColcTitle;
	@FXML private TableColumn<MyReportData, Integer> tColcnum;
	@FXML private TableColumn<CheckBox, Integer> tColChk;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Platform.runLater(()->getDatas());
	}

	public void getDatas() {
		
		ObservableList<MyReportData> datas = FXCollections.observableArrayList();
		MyReportData[] reports = rDao.getMyReportInfo("test");
		
		if(reports != null) {
			for(MyReportData my : reports) {
				//MyReportData de = new MyReportData(my.getcId(),my.getTitle(),my.getNum(),false);
				//datas.add(my);
				//datas.add(de);
				System.out.println(my.getcId());
			}
			datas.setAll(reports);
			tViewContent.setItems(datas);
			
			//여기는 테이블에 데이터 삽입하는곳 (테이블뷰가 보여주게 하는곳 칼럼 데이터 삽입)
			tColcName.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("cId"));
			tColcnum.setCellValueFactory(new PropertyValueFactory<MyReportData, Integer>("num"));
			tColcTitle.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("title"));
			TableColumn<MyReportData,Boolean> checkBox = new TableColumn<MyReportData,Boolean>("check");    
	        // 체크박스를 Cell에 표시
	      checkBox.setCellValueFactory(new PropertyValueFactory<MyReportData,Boolean>("check"));
	
	      	checkBox.setCellFactory(column -> new TableCell<MyReportData, Boolean>(){
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
		            	  }
		            	  System.out.println(my.getNum()+"번");
		            	  System.out.println(my.getcId()+" is Checked!");
		            
		              } else {
		            	  if(checkedNum.contains(my.getNum())) {
		            		  for(int i =0; i<checkedNum.size();i++) {
			            		  if(checkedNum.get(i) == my.getNum()) {
				            		  checkedNum.remove(i);
				            	  }
			            	  }
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
           tViewContent.getColumns().add(checkBox);
           
           tViewContent.setEditable(true);
    }

}	
	public void setUser(String userId) {
		this.userId =  userId;
	}
}
