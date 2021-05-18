package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CriminalDAO {

	private Connection conn; 
	private PreparedStatement pstmt; 
	private ResultSet rs; 
	
	
	public CriminalDAO() {
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
	
	public int getCrimNum(String crimId) {
		String sql = "select cNum from criminal where cId = ?";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				return rs.getInt(1); 
			}
			System.out.println("조회내용 없음 ");
			return -1; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
}
