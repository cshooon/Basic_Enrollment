package hoon.jsp.d;

import java.sql.*;

public class MembersDAO {
	//싱글톤 패턴
	private MembersDAO() {}
	private static MembersDAO instance = new MembersDAO();
	
	public static MembersDAO getInstance() {
		return instance;
	}
	
	private void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) {
				pstmt.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 회원가입 후 데이터를 DB에 넣는 메소드
	public int insertMember(MembersDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "insert into Student values(?, ?, ?, ?)";
		try {
			// db parameters
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setInt(4, dto.getCredit());
			result = pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		return result;
	}
	// members 테이블에 같은 아이디가 있는지 확인하는 메소드
	public int confirmId(String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select Student_ID from Student where Student_ID = ?";
		try {
			
			// 같은 아이디가 있다면 멤버가 존재할 때 result값을 1로 지정
			// 같은 아이디가 없다면 멤버가 존재하지 않는다면 result값을 0으로 지정
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				result = 1;
				//멤버가 존재할 때
			}else {
				result = 0;
				//멤버가 존재하지 않을 때
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return result;
	}
	// id와 pw의 데이터를 받아서 DB에 연결 후 id로 pw 데이터 얻어와 (id, pw) 확인하는 메소드
	// ID => x ID -> o ==> pw o pw x
	// 아이디가 존재하지 않는다면 -1 존재하고 비밀번호가 맞는 경우 1, 비밀번호가 틀린경우 0을 리턴
	public int userCheck(String id, String pw) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query="select Student_PW from Student where Student_ID=?";
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				String dbPw = rs.getString("Student_PW");
				if(dbPw.equals(pw))
					result = 1;//아이디, 비밀번호 맞는 경우 로그인 OK
				else
					result = 0;//아이디, 비밀번호 틀린 경우 로그인 X
			}
			else {
				result = -1;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		return result;
	}
	//DB에서 id로 해당하는 모든 컬럼의 값을 얻은 후 DTO 객체에 담고 DTO 객체 반환하기
	public MembersDTO getMember(String id) {
		MembersDTO dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "select * from Student where Student_ID=?";
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String dbId = rs.getString("Student_ID");
				String pw = rs.getString("Student_PW");
				String name = rs.getString("Student_NAME");
				int credit = rs.getInt("Total_Credit");
				dto = new MembersDTO(dbId, pw, name);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
			close(conn);
		}
		
		return dto;
	}
	// 회원의 정보를 수정해주는 메소드
	// id 수정 불가 비밀번호 o 이름 x
	public int updateMember(MembersDTO dto)
	{
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "update Student set Student_PW = ?" + "where Student_ID = ?";
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			// create a connection to the database
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  dto.getPw());
			pstmt.setString(2, dto.getId());
			result = pstmt.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		return result;
	}
	//id를 받아 해당하는 멤버 삭제
	public int deleteMember(String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "delete from Student where Student_ID=?";
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  id);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
			close(conn);
		}
		return result;
	}
}
