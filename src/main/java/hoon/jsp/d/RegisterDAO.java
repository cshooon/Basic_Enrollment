package hoon.jsp.d;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RegisterDAO {
    private RegisterDAO() {}
    private static RegisterDAO instance = new RegisterDAO();

    public static RegisterDAO getInstance() {
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
    public ArrayList<RegisterDTO> RegisterList(String id){
        ArrayList<RegisterDTO> list = new ArrayList<RegisterDTO>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select * from Course natural join Subject";
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
            conn = DriverManager.getConnection(url);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                String sid = id;
                String suid = rs.getString("Subject_ID");
                String cid = rs.getString("Course_ID");
                list.add(new RegisterDTO(sid, suid, cid));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(rs != null)rs.close();
                if(pstmt != null)pstmt.close();
                if(conn != null)conn.close();

            }
            catch(Exception e2) {}
        }
        return list;
    }
    public int insertRegister(RegisterDTO dto) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "insert into Enrollment values(?, ?, ?)";
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, dto.getSid());
            pstmt.setString(2, dto.getCid());
            pstmt.setString(3, dto.getSuid());
            result = pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }
    public int deleteEnroll(String sid, String suid) {
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "delete from Enrollment where Student_ID=? and Course_ID=?";
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
            conn = DriverManager.getConnection(url);
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,  sid);
            pstmt.setString(2,  suid);
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
