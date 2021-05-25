package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO extends DAOBase{


	private Connection conn =DAOBase.conn; 
	private PreparedStatement pstmt ,pstmt2; 
	private ResultSet rs,rs2; 
	private CriminalDAO cDao = new CriminalDAO();
	
	public ReportDAO() {
		
	}
	public String getContent(int index) {
		String sql = "select content from report where  num = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			String content = rs.getString("content");
			return content;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public MyReportData[] getMyReportInfo(String userId) {
		String sql = "select * from report where reporter = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			rs.last();
			int getRowNum = rs.getRow();
			if(getRowNum>0) {
				rs.beforeFirst();
				MyReportData[] datas = new MyReportData[getRowNum];
				int i=0;
				while(rs.next()) {
					datas[i] = new MyReportData(rs.getString("cId"), rs.getString("title"), rs.getInt("num"),false);
					i++;
				}
				return datas;
			}
		}catch(Exception e) {
			System.out.println("getReporterInfo실패");
			e.printStackTrace();
		}
		return null;
	}
	public Report[] getReportTitle(String crimId) {
		String sql = "select title,num from report where cId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, crimId);
			rs = pstmt.executeQuery();
			rs.last();
			int getRowNum = rs.getRow();
			if(getRowNum>0) {
				rs.beforeFirst();
			Report[] titles = new Report[getRowNum];
			System.out.println(getRowNum);
			int i =0;
			Report report;
			while(rs.next()) {
				report = new Report();
				report.setReport(rs.getString("title"), rs.getInt("num"));
				titles[i] = report;
				i++;
			}
			return titles;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public int getReportedNum(String cId) {
		String sql = "select title from report where cId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,cId);
			rs = pstmt.executeQuery();
			rs.last();
			int reportedNum = rs.getRow();
			return reportedNum;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	//위아래는 완전히 같은 코드지만 위에는 신고당한 횟수 아래는 신고한 횟수임 
	//
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
	public int deleteReporting(int[] cNum) {
		String sql = "delete from report where num =?";
		int[] nums = cNum;
		for(int i = 0 ; i<nums.length; i++) {
			System.out.print(nums[i]+",");
		}
		if(nums.length <=0) {
			return -1 ;
		}
		try {
			pstmt = conn.prepareStatement(sql); 
			if(nums.length > 1) {
			for(int i=0; i <nums.length; i++) {
				pstmt.setInt(1, nums[i]);
				pstmt.addBatch();
				}
			}else if(nums.length == 1) {
				pstmt.setInt(1, nums[0]);
				int  result = pstmt.executeUpdate();
				return result;
			}
			pstmt.executeBatch();
			conn.commit();
			
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	
	}
	public int doReporting(String crimId, String reporterId , String reason , String title) {
		String sql =  "insert into report values(?, ?, ?, ?,?)";
		try {
			pstmt = conn.prepareStatement(sql); 
			System.out.println(crimId+"\n"+reporterId+"\n"+reason+"\n"+title);		
			pstmt.setString(1, crimId); 
			pstmt.setString(2, reporterId); 
			pstmt.setString(3, reason); 
			pstmt.setString(4, title); 
			pstmt.setString(5, null);
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
	//이건 잘못 만든 코드 crim쪽으로 옮겨야함 
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
