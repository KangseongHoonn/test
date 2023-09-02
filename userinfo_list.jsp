<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.member.*, java.util.*" %>
<%
	List<MemberDTO> list = (List)request.getAttribute("list");
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
    <td width=5% align=center>번호</td>
    <td width=10% align=center>ID</td>
    <td width=10% align=center>이름</td>
    <td width=15% align=center>전화번호</td>
    <td width=15% align=center>이메일</td>
    <td width=15% align=center>등록일자</td>
    <td width=15% align=center>최근접속일</td> 
  </tr>
<%
	//for(int x=0; x<list.size(); x++){
	for(MemberDTO mem : list){
%>  
   <tr>
      <td align=center><%= 1 %></td>
      <td align=center><%= mem.getUserid() %></td>
      <td align=center><%= mem.getName() %></td>
      <td align=center><%= mem.getTel() %></td>
      <td align=center><%= mem.getEmail() %></td>
      <td align=center><%= mem.getFirst_time() %></td>
      <td align=center><%= mem.getLast_time() %></td>
   </tr>
<%
	}
%>
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
    <td>
    	<%
    		if(session.getAttribute("user") == null){
    	%>
    		<a href="userlogin_form.do">로그인 페이지 이동</a>
    	<%
    		}else{
    	%>
    		<a href="userlogin_out.do">(${user.name})로그인 아웃</a>
    	<%
    		}
    	%>
    </td>
    <td>
       	<%
    		if(session.getAttribute("user") == null){
    	%>
        	<a href="userinfo_insert.do">회원가입페이지 이동</a>
        <%
    		}else{
        %>
        	<a href="userinfo_modify.do">(${user.name})님 정보 수정</a>
        <%
    		}
        %>
    </td>
   </tr>
</table>    
</body>
</html>
