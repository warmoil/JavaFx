package db;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserData {

	private SimpleStringProperty userId , userPw ,nickName , ask , answer;
	private BooleanProperty check;
	public UserData(String userId,String userPw ,String nickName ,String ask ,String answer ,boolean check){
		this.userId = new SimpleStringProperty(userId);
		this.userPw = new SimpleStringProperty(userPw);
		this.nickName = new SimpleStringProperty(nickName);
		this.ask = new SimpleStringProperty(ask);
		this.answer =  new SimpleStringProperty(answer);
		this.check = new SimpleBooleanProperty(check);
	}
	
	public StringProperty userIdProperty() {return userId;}
	public StringProperty userPwProperty() {return userPw;}
	public StringProperty nickNameProperty() {return nickName;}
	public StringProperty askProperty() {return ask;}
	public StringProperty answerProperty() {return answer;}
	public BooleanProperty checkProperty() {return check;}
	
	
	
	public boolean getCheck() {
		return check.get();
	}
	public void setCheck(boolean check) {
		this.check  = new SimpleBooleanProperty(check);
	}
	
	public String getUserId() {
		return userId.get();
	}

	public void setUserId(String userId) {
		this.userId = new SimpleStringProperty(userId);
	}

	public String getUserPw() {
		return userPw.get();
	}

	public void setUserPw(String userPw) {
		this.userPw = new SimpleStringProperty(userPw);;
	}

	public String getNickName() {
		return nickName.get();
	}

	public void setNickName(String nickName) {
		this.nickName = new SimpleStringProperty(nickName);
	}

	public String getAsk() {
		return ask.get();
	}

	public void setAsk(String ask) {
		this.ask = new SimpleStringProperty(ask);;
	}

	public String getAnswer() {
		return answer.get();
	}

	public void setAnswer(String answer) {
		this.answer = new SimpleStringProperty(answer);
	}
	
	
	
}
