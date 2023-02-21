<%@ page import="hoon.jsp.d.MembersDAO" %>
<%@ page import="hoon.jsp.d.MembersDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	  <%
	 	request.setCharacterEncoding("UTF-8");
		 String ID = request.getParameter("id");
		 String PW = request.getParameter("pw");
		 String Name = request.getParameter("name");
	  %>
	  <%
	  //regDate까지 DTO 객체에 세팅해준다.
	    MembersDAO dao = MembersDAO.getInstance();
		MembersDTO dto = new MembersDTO(ID, PW, Name);
	  	if(dao.confirmId(dto.getId()) == 1){//같은 아이디가 있을 때
	  %>
	  	<script type="text/javascript">
	  		alert('아이디가 이미 존재합니다.');
	  		history.back(); // 이전페이지로 이동
	  	</script>
	  <%
	  	}else{//같은 아이디가 없다면 회원가입 OK
	  		int result = dao.insertMember(dto);
			System.out.println(result);
	  		if (result == 1){

	  %>

	  	<script>
	  		alert('회원가입을 축하합니다');
	  		window.location='login.jsp'
	  	</script>
	  <%
	  		}
	  		else{
	  %>
	  		<script>
	  			alert('회원가입에 실패했습니다');
	  			window.location = 'join.jsp';
	  		</script>
	  <%
	  		}
	  	}
	  %>
</body>
</html>