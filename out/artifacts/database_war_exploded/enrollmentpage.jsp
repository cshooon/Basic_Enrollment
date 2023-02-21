<%@ page import="java.sql.*" %>
<%@ page import="hoon.jsp.d.RegisterDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="hoon.jsp.d.RegisterDAO" %><%--
  Created by IntelliJ IDEA.
  User: Hoon
  Date: 2022-12-09
  Time: 오전 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>수강신청 하는 페이지</title>
</head>
<body>
<%
    Class.forName("org.sqlite.JDBC");
    String url = "jdbc:sqlite:C:/sqlite/DB/Enrollment.db";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    int num = 0;
    try {
        String query = "select * from Course natural join Subject;";
        // create a connection to the database
        conn = DriverManager.getConnection(url);
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
%>
<form action="enrollment.jsp" method="post">
<table border="1">
    <tr>
        <td>number</td>
        <td>CourseID</td>
        <td>Subject_ID</td>
        <td>Credit</td>
        <td>Instructor_Name</td>
        <td>Subject_NAME</td>
        <td>enroll</td>
    </tr>
        <%
			while (rs.next()) {
                String cid = rs.getString("Course_ID");
                String cname = rs.getString("Subject_ID");
                int credit = rs.getInt("Credit");
                String iname = rs.getString("Instructor_NAME");
                String suid = rs.getString("Subject_NAME");
		%>
    <tr>
        <td><%=num%></td>
        <td><%=cid%></td>
        <td><%=cname%></td>
        <td><%=credit%></td>
        <td><%=iname%></td>
        <td><%=suid%></td>
        <td><input type="checkbox" name="enr" value="<%=num%>"/></td>
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
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		%>
    <input type="submit" value="enroll"/>
    <br><br>
    <a href="main.jsp">홈으로</a>
</form>
</body>
</html>
