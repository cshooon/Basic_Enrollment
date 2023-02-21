import hoon.jsp.d.MembersDTO;
import hoon.jsp.d.RegisterDAO;
import hoon.jsp.d.RegisterDTO;

import java.sql.*;
import java.util.ArrayList;


public class Connect {
    public ArrayList<RegisterDTO> RegisterList(String id){
        ArrayList<RegisterDTO> list = new ArrayList<RegisterDTO>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "select * from Course natural join Subjects";
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
    public static void main(String[] args) {
        RegisterDAO dao = RegisterDAO.getInstance();
        ArrayList<RegisterDTO> list = dao.RegisterList("1234");
        RegisterDTO dto = list.get(1);
        int result = dao.insertRegister(dto);
        System.out.println(result);
    }
}