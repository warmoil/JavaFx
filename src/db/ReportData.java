package db;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReportData {
	private BooleanProperty check;
	private SimpleIntegerProperty num = new SimpleIntegerProperty();
/*	private SimpleStringProperty cId = new SimpleStringProperty("");
	private SimpleStringProperty title = new SimpleStringProperty("");
	private SimpleStringProperty content = new SimpleStringProperty("");
	private SimpleStringProperty reporter = new SimpleStringProperty(""); */  //이짓 안해도 되는거같음 
	private SimpleStringProperty cId, title, content , reporter ;
	
	public ReportData(String reporter , String cId , String title , int num , String content , boolean check){
		this.reporter = new SimpleStringProperty(reporter);
		this.cId = new SimpleStringProperty(cId);
		this.title = new SimpleStringProperty(title);
		this.content = new SimpleStringProperty(content);
		this.num = new SimpleIntegerProperty(num);
		this.check = new SimpleBooleanProperty(check);
	}
	 public StringProperty cIdProperty() {return cId;}
	 public StringProperty titleProperty() {return title;}
	 public IntegerProperty numProperty() {return num;}
	 public BooleanProperty checkProperty() { return check; }
	 public BooleanProperty getCheck() {return check; }
	 public StringProperty contentProprty() {return content;}
	 public StringProperty reporterProperty() {return reporter;}
	 
	 public String getReporter() {
		 return reporter.get();
	 }
	 public void setReporter(String reporter) {
		 this.reporter = new SimpleStringProperty(reporter);
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
