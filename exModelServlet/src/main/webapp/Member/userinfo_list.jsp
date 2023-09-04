<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.member.*, java.util.*" %>
<%
	List<MemberDTO> list =(List)request.getAttribute("member");
	int cnt = list.size();
%>
<html>
<head>
<title>회원목록 보여주기</title>

<STYLE TYPE="text/css">
<!--
body { font-family: 돋움, Verdana; font-size: 9pt}
td   { font-family: 돋움, Verdana; font-size: 9pt; text-decoration: none; color: #000000} 
--->
</STYLE>
</head>
<body>
<table width="550" border="1" cellspacing="0" cellpadding="2" bordercolorlight="#173E7C" bordercolordark="white">
  <tr>
    <td width=50 align=center>번호</td>
    <td width=50 align=center>ID</td>
    <td width=80 align=center>이름</td>
    <td width=100 align=center>전화번호</td>
    <td width=100 align=center>등록일자</td>
    <td width=100 align=center>최근접속일</td>
    
  </tr>
  <%for(MemberDTO m : list){ %>
   <tr>
      <td align=center><%=cnt-- %></td>
      <td align=center><%=m.getUserid() %></td>
      <td align=center><%=m.getName() %></td>
      <td align=center><%=m.getTel() %></td>
      <td align=center><%=m.getFirsttime().substring(0,10) %></td>
      <td align=center><%=m.getLasttime() %></td>
   </tr>
	<%} %>
</table>
<table width=550>
  <tr>
    <td>
      <select name="search_gubun">
        <option >이름 </option>
        <option >주소 </option>
        
    </td>
    <td>  찾는이름:
          <input type="text" name="searc" size=10> &nbsp;
          [조회]
     </td>
   </tr>
  <tr>
    <td>
    </td>
    <td></td>
   </tr>
  <tr>
   	<%
    	if(session.getAttribute("user") != null){
    %>
    	<td>
    		<a href="/Member/userlogin_out.do">로그 아웃</a>		
    	</td>
    <%
   		}else{
   	%>
   		<td>
   			<a href="/Member/userinfo_login.do">로그인 페이지 이동</a>
   		</td>
   	<%
    	}
   	%>
    <%
    	if(session.getAttribute("user") != null){
    %>
    	<td><a href="/Member/userinfo_modify.do">내 정보 수정</a></td>
    <%
   		}else{
   	%>
    	<td><a href="/Member/userinfo_insert.do">회원가입페이지 이동</a></td>
 	<%
    	}
   	%>
   </tr>
</table>    
</body>
</html>
