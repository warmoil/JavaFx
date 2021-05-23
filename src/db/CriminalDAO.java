package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CriminalDAO {

	private Connection conn; 
	private PreparedStatement pstmt; 
	private ResultSet rs,rs2; 
	
	
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
	public void insertCriminal(String crimId) {
		String sql = "insert into  criminal  values(?, 1)";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			int insertNum = pstmt.executeUpdate(); 
			if(insertNum >0) {
				System.out.println("criminal에 "+crimId+":입력 성공");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setCrimNum(String crimId) {
		String sql = "select cId from report where cId =?";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			rs = pstmt.executeQuery(); 
			
			rs.last();
			int rowCount = rs.getRow();
			System.out.println(rowCount+"로우count");
				
					sql = "update criminal set cNum =? where cId =?";
					try {
						pstmt = conn.prepareStatement(sql); 
						pstmt.setInt(1, rowCount);
						pstmt.setString(2, crimId);
						int count = pstmt.executeUpdate(); 
						System.out.println(count+":카운트");
					}catch(Exception e2) {
						e2.printStackTrace();
					}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
