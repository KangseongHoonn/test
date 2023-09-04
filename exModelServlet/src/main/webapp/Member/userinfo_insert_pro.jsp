<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.member.*" %>
<%
	int row = (int)request.getAttribute("row");

	if(row==1){
%>
<script>
	alert("등록성공");
	location.href="/Member/userinfo_list.do";
</script>
<%	}else{ %>
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