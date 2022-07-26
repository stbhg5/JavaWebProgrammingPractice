<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px;">
	SPMS(Simple Project Management System)
	<c:if test="${!empty sessionScope.member and !empty sessionScope.member.email}">
		<span style="float:right;">
			${sessionScope.member.name}
			<a style="color:white;" href="<%=request.getContextPath()%>/auth/logout.do">로그아웃</a>
		</span>
	</c:if>
</div>