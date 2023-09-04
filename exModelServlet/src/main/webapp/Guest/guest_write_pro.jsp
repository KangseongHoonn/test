<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int row = (int)request.getAttribute("row");
	
	if(row==1){
%>
	<script>
		alert("글이 등록되었습니다.");
		location.href="/Guest/guest_list";
	</script>
<%
	}else{
%>
	<script>
		alert("글 등록 실패");
		history.back();
	</script>
<%
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>