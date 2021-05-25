package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO extends DAOBase{

	private Connection conn =DAOBase.conn; 
	private PreparedStatement pstmt; 
	private ResultSet rs; 
	private User user = new User();
	public UserDAO() {
	
	}
	
	public int login(String userId, String userPw) {
		String sql = "select userPw from user where userID = ?";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				if(rs.getString(1).equals(userPw)) {
					return 1; 
				}else
					return 0; 
			}
			return -1; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -2; 
	}
	
	public int join(User user) {
		  String sql = "insert into user values(?, ?, ?, ?, ?)";
		  try {
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, user.getUserId());
		    pstmt.setString(2, user.getUserPw());
		    pstmt.setString(3, user.getUserNickName());
		    pstmt.setString(4, user.getUserAsk());
		    pstmt.setString(5, user.getUserAnswer());
		    return pstmt.executeUpdate();
		  }catch (Exception e) {
		 	e.printStackTrace();
		  }
		  return -1;
		}
	
	public String getUserNickName(String userId) {
		String sql = "select nickname from user where userId = ?";
		
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {		
				String nick = rs.getString("nickname");				
					return nick;
				}
			System.out.println("틀림");
			return null;
		}catch(Exception e) {
			System.out.println("실패");
			e.printStackTrace();
			return null;
		}
	}

	public User getUserInfo(String userId) {
		String sql = "select * from user where userId = ?";
		User user = new User();
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {		
				System.out.println("rs.next()는 됐음");
				String nick = rs.getString("nickname");				
				String id = rs.getString("userId");
				String pw = rs.getString("userPw");
				String ask = rs.getString("ask");
				String answer = rs.getString("answer");
				user.setUserInfo(id, pw, nick, ask, answer);
				return user;
				}
			System.out.println("getUserInfo실패");
			
		}catch(Exception e) {
			System.out.println("실패");
			e.printStackTrace();
			
		}
		return null;
		
	}
	
	public String getUserNickName(User user) {
		String sql = "select nickname from user where userId = ?";
		System.out.println("겟닉네임 메소드 실행");
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, user.getUserId()); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {		
				String nick = rs.getString("nickname");				
					return nick;
				}
			System.out.println("틀림");
			return null;
		}catch(Exception e) {
			System.out.println("실패");
			e.printStackTrace();
			return null;
		}
	}
	public boolean isUserUpdate(User user) {
		String sql  = "update user set userPw = ? , nickname = ? , ask = ? , answer = ? where userId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserPw());
			pstmt.setString(2, user.getUserNickName());
			pstmt.setString(3, user.getUserAsk());
			pstmt.setString(4, user.getUserAnswer());
			pstmt.setString(5, user.getUserId());
			int rsNum = pstmt.executeUpdate();
			if(rsNum ==1) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean isJoined(String  userId) {
		String sql = "select userId from user where userId = ?";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				if(rs.getString(1).equals(userId)) {
					return true; 
				}else
					return false; 
			}
			return false; 
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
