<%@ page import="hoon.jsp.d.RegisterDAO" %>
<%@ page import="hoon.jsp.d.RegisterDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enrollment</title>
</head>
<body>
<%
    String[] values = request.getParameterValues("enr");
    RegisterDAO dao = RegisterDAO.getInstance();
    String sid = (String) session.getAttribute("id");
    ArrayList<RegisterDTO> list = dao.RegisterList(sid);
    int result = 0;
    for(String value: values){
        RegisterDTO dto = list.get(Integer.parseInt(value));
        result = dao.insertRegister(dto);
    }
    if (result == 1){
%>
<script>
    alert('수강신청 성공!!');
    window.location='enrollmentshow.jsp'
</script>
<%
}
    else{
%>
<script>
    alert('수강신청 실패!!');
    window.location='enrollmentpage.jsp'
</script>
<%
    }
%>
</body>
</html>
