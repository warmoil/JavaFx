package db;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyReportData {
	private BooleanProperty check;
	private SimpleIntegerProperty num = new SimpleIntegerProperty();
	private SimpleStringProperty cId = new SimpleStringProperty("");
	private SimpleStringProperty title = new SimpleStringProperty("");
	private SimpleStringProperty content = new SimpleStringProperty("");
	public MyReportData(String cId , String title ,int num ,String content , boolean check) {
		this.cId = new SimpleStringProperty(cId) ;
		this.title = new SimpleStringProperty(title);
		this.num = new SimpleIntegerProperty(num);
		this.check = new SimpleBooleanProperty(check);
		this.content = new SimpleStringProperty(content);
		
	}
	// 여기 프로 퍼티가 가장중요함!!!!!!!!!! 이거 이름을 맞춰서 테이블을 만들어야
	// 테이블에 삽입됨 ...;;; 
	//ex) tColcName.setCellValueFactory(new PropertyValueFactory<MyReportData, String>("cId"));
	 public StringProperty cIdProperty() {return cId;}
	 public StringProperty titleProperty() {return title;}
	 public IntegerProperty numProperty() {return num;}
	 public BooleanProperty checkProperty() { return check; }
	 public BooleanProperty getCheck() {return check; }
	 public StringProperty contentProprty() {return content;}
	 public void setContent(String content) {
		 this.content = new SimpleStringProperty(content);
	 }
	 public String getContent() {
		 return content.get();
	 }
	 public void setCheck(boolean check) {
		  this.check = new SimpleBooleanProperty(check);
	 }
	public int getNum() {
		return num.get();
	}

	public void setNum(SimpleIntegerProperty num) {
		this.num = num;
	}

	public String getcId() {
		return cId.get();
	}

	public void setcId(SimpleStringProperty cId) {
		this.cId = cId;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	
	 
}
