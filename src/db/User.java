package db;

public class User {

	private String userId,userPw,userNickName,userAsk,userAnswer;

	public void setUserInfo(String userId,String userPw,String userNickName,String userAsk,String userAnswer) {
		setUserId(userId);
		setUserPw(userPw);
		setUserAsk(userAsk);
		setUserAnswer(userAnswer);
		setUserNickName(userNickName);
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserAsk() {
		return userAsk;
	}

	public void setUserAsk(String userAsk) {
		this.userAsk = userAsk;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	
}
