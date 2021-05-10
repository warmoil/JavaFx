package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class UserDAO {

	private Connection conn; 
	private PreparedStatement pstmt; 
	private ResultSet rs; 
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mariadb://localhost:3306/theCheat";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch (Exception e) {
			e.printStackTrace();
		}
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
}
