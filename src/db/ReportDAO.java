package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {


	private Connection conn; 
	private PreparedStatement pstmt ,pstmt2; 
	private ResultSet rs,rs2; 
	private CriminalDAO cDao = new CriminalDAO();
	
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
	
	public int getReportingNum(String userId) {
		
		String sql = "select title from report where reporter = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userId);
			rs = pstmt.executeQuery();
			rs.last();
			int userReportNum = rs.getRow();
			return userReportNum;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int doReporting(String crimId, String reporterId , String reason , String title) {
		String sql =  "insert into report values(?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql); 
			System.out.println(crimId+"\n"+reporterId+"\n"+reason+"\n"+title);		
			pstmt.setString(1, crimId); 
			pstmt.setString(2, reporterId); 
			pstmt.setString(3, reason); 
			pstmt.setString(4, title); 
			pstmt.executeUpdate();
			int reported = isreported(crimId);
			if(reported == -1) {
				
				String sql2 = "insert into criminal value(?,?)";
				try {
					System.out.println("실행?");
					pstmt.executeUpdate();
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, crimId);
					pstmt2.setInt(2, 1);
					pstmt.executeUpdate();
					pstmt2.executeUpdate();
					pstmt2.close();
				}catch(Exception e2) {
					System.out.println("isReported실패 ");
					e2.printStackTrace();
				}
			}else {
				System.out.println("있던아이디이니까 추가할게 ");
			}
			pstmt = conn.prepareStatement(sql);
		    return 1; 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int isreported(String  cId) {
		String sql = "select cId from  criminal where cId = ?";
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, cId); 
			rs = pstmt.executeQuery(); 
			if(rs.next()) {
				if(rs.getString(1).equals(cId)) {
					return 1; 
				}else
					return -1; 
			}
			return -1; 
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
