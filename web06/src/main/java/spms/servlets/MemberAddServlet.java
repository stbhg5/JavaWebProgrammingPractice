package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

//프런트 컨트롤러 적용
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//신규 회원 링크 클릭시 GET 요청(a태그로 만들어진 링크 클릭)이 발생하기에 doGet() 메소드를 오버라이딩
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html; charset=UTF-8");
		//RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		//rd.forward(request, response);
		//JSP URL 정보 프런트 컨트롤러에게 알리기 위해 ServletRequest 보관소에 저장
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			ServletContext sc = this.getServletContext();
			//ServletContext에 저장된 DAO 객체 사용
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
		    /*memberDao.insert(new Member()
		             .setEmail(request.getParameter("email"))
		             .setPassword(request.getParameter("password"))
		             .setName(request.getParameter("name")));*/
			//Member 객체 ServletRequest 보관소에서 꺼내기
			Member member = (Member)request.getAttribute("member");
			memberDao.insert(member);
			
			//리다이렉트를 이용한 리프래시
			//response.sendRedirect("list");
			//리다이렉트를 위한 뷰 URL 설정
			request.setAttribute("viewUrl", "redirect:list.do");
			
		} catch (Exception e) {//JDBC 프로그래밍에서 예외처리
			throw new ServletException(e);
			/*e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);*/
		} finally {}
	}
}
