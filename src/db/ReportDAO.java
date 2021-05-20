package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {


	private Connection conn; 
	private PreparedStatement pstmt; 
	private ResultSet rs,rs2; 
	
	
	public ReportDAO() {
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
	public int doReporting(String crimId, String reporterId , String reason , String title) {
		String sql = "insert into report value(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, crimId); 
			pstmt.setString(2, reporterId); 
			pstmt.setString(3, reason); 
			pstmt.setString(4, title); 
		    return pstmt.executeUpdate(); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
