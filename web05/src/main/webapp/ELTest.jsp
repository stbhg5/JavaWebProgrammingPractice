<%@page import="el.resourcebundle.MyResourceBundle_ko_KR"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="spms.vo.Member"%>
<%@page import="java.util.ResourceBundle"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%//EL표현식 - 리터럴 표현식%>
문자열     : ${"test"}<br/>
문자열     : ${'test'}<br/>
정수       : ${20}<br/>
부동소수점 : ${3.14}<br/>
참거짓     : ${true}<br/>
null       : ${null}<br/>
<br/><br/><br/><br/><br/>

<%
//EL표현식 - 배열
//배열에 값 준비
pageContext.setAttribute("scores", new int[] {90, 80, 70, 100}); //pageContext는 JSP 내장 객체 중 하나다.
%>
<!-- 배열에서 인덱스 2의 값 꺼내기 -->
${'배열에서 인덱스 2의 값 꺼내기 :'} ${scores[2]}
<br/><br/><br/><br/><br/>

<%
//EL표현식 - List
//List에 값 준비
List<String> nameList = new LinkedList<String>();
nameList.add("홍길동");
nameList.add("임꺽정");
nameList.add("일지매");
pageContext.setAttribute("nameList", nameList);
%>
<!-- 리스트 객체에서 인덱스 1의 값 꺼내기 -->
${'리스트 객체에서 인덱스 1의 값 꺼내기 :'} ${nameList[1]}
<br/><br/><br/><br/><br/>

<%
//EL표현식 - Map
//Map에 값 준비
Map<String,String> map = new HashMap<String,String>();
map.put("s01", "홍길동");
map.put("s02", "임꺽정");
map.put("s03", "일지매");
pageContext.setAttribute("map", map);
%>
<!-- 맵 객체에서 키 s02로 저장된 값 꺼내기 -->
${'맵 객체에서 키 s02로 저장된 값 꺼내기 :'} ${map.s02}
<br/><br/><br/><br/><br/>

<%
//EL표현식 - 자바빈
//자바 객체에 값 준비
pageContext.setAttribute("member", new Member().setNo(100)
											   .setName("홍길동")
											   .setEmail("hong@test.com"));
%>
<!-- 자바빈에서 프로퍼티 email의 값 꺼내기 -->
${'자바빈에서 프로퍼티 email의 값 꺼내기 :'} ${member.email}
<br/><br/><br/><br/><br/>

<%
//EL표현식 - ResourceBundle
//ResourceBundle 객체 준비
//pageContext.setAttribute("myRB", ResourceBundle.getBundle("MyResourceBundle_ko_KR"));
MyResourceBundle_ko_KR myRB = new MyResourceBundle_ko_KR();
pageContext.setAttribute("myRB", myRB.getObject("OK"));
%>
<!-- 리소스번들 객체에서 값 꺼내기 -->
${'리소스번들 객체에서 값 꺼내기 :'} ${myRB}
<%//${myRB.OK}는 안됨..%>
<br/><br/><br/><br/><br/>
</body>
</html>