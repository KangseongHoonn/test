<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.member.*" %>
<%
	int row = (int)request.getAttribute("row");
	MemberDTO dto = (MemberDTO)request.getAttribute("dto");
	
	if(row==1){
		session = request.getSession();
		session.setAttribute("user", dto);
		session.setMaxInactiveInterval(1800);
%>
<script>
	alert("수정성공");
	location.href="/Member/userinfo_list.do";
</script>
<%}else{ %>
<script>
	alert("오류");
	history.back();
</script>
<%} %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>