<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: Hoon
  Date: 2022-12-09
  Time: 오전 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>수강신청한 목록</title>
</head>
<body>
<%
    Class.forName("org.sqlite.JDBC");
    String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int num = 0;
    try {
        String query = "select * from Enrollment natural join Course natural join " +
                "Subject where Student_ID=?";
        // create a connection to the database
        conn = DriverManager.getConnection(url);
        pstmt = conn.prepareStatement(query);
        String ssid = (String) session.getAttribute("id");
        pstmt.setString(1, ssid);
        rs = pstmt.executeQuery();
%>
<form action="enrollmentdelete.jsp" method="post">
    <table border="1">
        <tr>
            <td>number</td>
            <td>Student_ID</td>
            <td>CourseID</td>
            <td>Subject_ID</td>
            <td>Credit</td>
            <td>Instructor_Name</td>
            <td>Subject_Name</td>
            <td>delete</td>
        </tr>
            <%
			while (rs.next()) {
                String sid = rs.getString("Student_ID");
                String cid = rs.getString("Course_ID");
                String suid = rs.getString("Subject_ID");
                int credit = rs.getInt("Credit");
                String iname = rs.getString("Instructor_NAME");
                String cname = rs.getString("Subject_NAME");
		%>
        <tr>
            <td><%=num%></td>
            <td><%=sid%></td>
            <td><%=cid%></td>
            <td><%=suid%></td>
            <td><%=credit%></td>
            <td><%=iname%></td>
            <td><%=cname%></td>
            <td><input type="checkbox" name="del" value="<%=num%>"/></td>
        </tr>
            <%
            num++;
            }
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		try {
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (conn != null) {
			conn.close();
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		%>
        <input type="submit" value="delete"/>
</form>
<br><br>
<a href="main.jsp">홈으로</a>
</body>
</html>
