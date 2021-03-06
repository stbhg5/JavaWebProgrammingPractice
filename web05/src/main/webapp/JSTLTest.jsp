<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<%//<c:choose>태그 예제%>
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

<%//<c:forEach>태그 예제%>
<h2>c:forEach 태그</h2>

<h3>반복문 - 배열</h3>
<% pageContext.setAttribute("nameList", new String[] {"홍길동", "임꺽정", "일지매"}); %>
<ul>
<c:forEach var="name" items="${nameList}">
	<li>${name}</li>	
</c:forEach>
</ul>
<br/><br/><br/><br/><br/>

<h3>반복문 - 배열의 시작 인덱스와 종료 인덱스 지정</h3>
<% pageContext.setAttribute("nameList2", 
  new String[]{"홍길동", "임꺽정", "일지매", "주먹대장", "똘이장군"}); %>
<ul>
<c:forEach var="name" items="${nameList2}" begin="2" end="3">
	<li>${name}</li>	
</c:forEach>
</ul>
<br/><br/><br/><br/><br/>

<h3>반복문 - ArrayList 객체</h3>
<% 
ArrayList<String> nameList3 = new ArrayList<String>();
nameList3.add("홍길동");
nameList3.add("임꺽정");
nameList3.add("일지매");
nameList3.add("주먹대장");
nameList3.add("똘이장군");
pageContext.setAttribute("nameList3", nameList3); 
%>
<ul>
<c:forEach var="name" items="${nameList3}">
	<li>${name}</li>	
</c:forEach>
</ul>
<br/><br/><br/><br/><br/>

<h3>반복문 - 콤마로 구분된 문자열</h3>
<% pageContext.setAttribute("nameList4", "홍길동,임꺽정,일지매,주먹대장,똘이장군"); %>
<ul>
<c:forEach var="name" items="${nameList4}">
	<li>${name}</li>	
</c:forEach>
</ul>
<br/><br/><br/><br/><br/>

<h3>반복문 - 특정 횟수 만큼 반복</h3>
<ul>
<c:forEach var="no" begin="1" end="6">
	<li><a href="jstl0${no}.jsp">JSTL 예제 ${no}</a></li>	
</c:forEach>
</ul>
<br/><br/><br/><br/><br/>

<%//<c:forTokens>태그 예제%>
<h2>c:forTokens 태그</h2>

<% pageContext.setAttribute("tokens","v1=20&v2=30&op=+"); %>
<ul>
<c:forTokens var="item" items="${tokens}" delims="&">
	<li>${item}</li>	
</c:forTokens>
</ul>
<br/><br/><br/><br/><br/>

<%//<c:url>태그 예제%>
<h2>c:url 태그</h2>

<c:url var="calcUrl" value="http://localhost:9999/web05/calc/Calculator.jsp">
	<c:param name="v1" value="20"/>
	<c:param name="v2" value="30"/>
	<c:param name="op" value="+"/>
</c:url>
<a href="${calcUrl}">계산하기</a>
<br/><br/><br/><br/><br/>

<%//<c:import>태그 예제%>
<h2>c:import 태그</h2>

<h3>RSS 피드 가져오기</h3>
<textarea rows="10" cols="80">
	<%-- <c:import url="http://www.zdnet.co.kr/Include2/ZDNetKorea_News.xml"/> --%>
	<c:import url="https://zdnet.co.kr/view/?no=20220704163158.xml"/>
</textarea>

<h3>RSS 피드 가져오기 - 보관소에 저장</h3>
<%-- <c:import var="zdnetRSS" url="http://www.zdnet.co.kr/Include2/ZDNetKorea_News.xml"/> --%>
<c:import var="zdnetRSS" url="https://zdnet.co.kr/view/?no=20220704163158.xml"/>
<textarea rows="10" cols="80">${zdnetRSS}</textarea>
<br/><br/><br/><br/><br/>

<%//<c:redirect>태그 예제%>
<h2>c:redirect 태그</h2>
<a href="RedirectTagTest.jsp">리다이렉트 테스트</a>
<br/><br/><br/><br/><br/>

<%//<fmt:parseDate>태그 예제%>
<h2>날짜 다루기</h2>
<h3>fmt:parseDate 태그</h3>
<code>
<fmt:parseDate var="date1" value="2013-11-16" pattern="yyyy-MM-dd"/>
</code>
<fmt:parseDate var="date1" value="2013-11-16" pattern="yyyy-MM-dd"/>
${date1}
<br/><br/><br/><br/><br/>

<%//<fmt:formatDate>태그 예제%>
<h3>fmt:formatDate 태그</h3>
<fmt:formatDate value="${date1}" pattern="MM/dd/yy"/>
<br/><br/><br/><br/><br/>
</body>
</html>