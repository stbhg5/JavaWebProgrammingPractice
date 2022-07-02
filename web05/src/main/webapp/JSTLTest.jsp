<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL Test</title>
</head>
<body>
<%//<c:out>태그 예제%>
1) <c:out value="안녕하세요!"/><br>
2) <c:out value="${null}">반갑습니다.</c:out><br>
3) <c:out value="안녕하세요!">반갑습니다.</c:out><br>
4) <c:out value="${null}"></c:out><br>
<br/><br/><br/><br/><br/>

<%//<c:set>태그 예제%>
<h3>값 설정 방식</h3>
<c:set var="username1" value="홍길동"/>
<c:set var="username2">임꺽정</c:set>
1) ${username1}<br>
2) ${username2}<br>

<h3>기본 보관소 - page</h3>
3) ${pageScope.username1}<br>
4) ${requestScope.username1}<br>

<h3>보관소 지정 - scope 속성</h3>
<c:set var="username3" scope="request">일지매</c:set>
5) ${pageScope.username3}<br>
6) ${requestScope.username3}<br>

<h3>기존의 값 덮어씀</h3>
<%pageContext.setAttribute("username4", "똘이장군");%>
7) 기존 값 = ${username4}<br>
<c:set var="username4" value="주먹대장"/>
8) 덮어쓴 값 = ${username4}<br>
<br/><br/><br/><br/><br/>

<%//<c:set>태그를 이용한 객체의 프로퍼티 값 설정 예제%>
<h3>객체의 프로퍼티 값 변경</h3>
<%!
public static class MyMember {
	int no;
	String name;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
%>
<%
MyMember member = new MyMember();
member.setNo(100);
member.setName("홍길동");
pageContext.setAttribute("member", member); 
%>
9) ${member.name}<br>
<c:set target="${member}" property="name" value="임꺽정"/>
10) ${member.name}<br>
<br/><br/><br/><br/><br/>

<%//<c:remove>태그 예제%>
<h2>c:remove 태그</h2>
<h3>보관소에 저장된 값 제거</h3>
<% pageContext.setAttribute("username1", "홍길동"); %>
1) ${username1}<br>
<c:remove var="username1"/>
2) ${username1}<br>
<br/><br/><br/><br/><br/>

<%//<c:if>태그 예제%>
<h2>c:if 태그</h2>
<c:if test="${10 > 20}" var="result1">
1) 10은 20보다 크다.<br>
</c:if>
2) ${result1}<br>

<c:if test="${10 < 20}" var="result2">
3) 10은 20보다 크다.<br>
</c:if>
4) ${result2}<br>
<br/><br/><br/><br/><br/>

<%//<c:c:choose>태그 예제%>
<h2>c:choose 태그</h2>
<c:set var="userid" value="admin"/>
<c:choose>
  <c:when test="${userid == 'hong'}">
    홍길동님 반갑습니다.
  </c:when>
  <c:when test="${userid == 'leem'}">
    임꺽정님 반갑습니다.
  </c:when>
  <c:when test="${userid == 'admin'}">
    관리자 전용 페이지입니다.
  </c:when>
  <c:otherwise>
    등록되지 않은 사용자입니다.
  </c:otherwise>
</c:choose>
<br/><br/><br/><br/><br/>

<%//<c:c:forEach>태그 예제%>
<h2>c:forEach 태그</h2>

<h3>반복문 - 배열</h3>
<% pageContext.setAttribute("nameList", 
  new String[]{"홍길동", "임꺽정", "일지매"}); %>
<ul>
<c:forEach var="name" items="${nameList}">
	<li>${name}</li>	
</c:forEach>
</ul>
</body>
</html>