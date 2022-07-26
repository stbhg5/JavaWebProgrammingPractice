package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

//프런트 컨트롤러 적용
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//HttpServlet 클래스로부터 상속받은 getServletContext()를 호출하여 ServletContext 객체 준비
			ServletContext sc = this.getServletContext();
			//ServletContext에 저장된 DAO 객체 사용
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
		       
		    Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));
		    request.setAttribute("member", member);
			
			/*RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(request, response);*/
		    //JSP URL 정보 프런트 컨트롤러에게 알리기 위해 ServletRequest 보관소에 저장
		    request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
		} catch (Exception e) {
			throw new ServletException(e);
			/*e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);*/
		} finally {}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			ServletContext sc = this.getServletContext();
			//ServletContext에 저장된 DAO 객체 사용
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
		    /*memberDao.update(new Member().setNo(Integer.parseInt(request.getParameter("no")))
		    	      					 .setName(request.getParameter("name"))
		    	      					 .setEmail(request.getParameter("email")));*/
			//Member 객체 ServletRequest 보관소에서 꺼내기
			Member member = (Member)request.getAttribute("member");
		    memberDao.update(member);
		    
			//response.sendRedirect("list");
		    //리다이렉트를 위한 뷰 URL 설정
		    request.setAttribute("viewUrl", "redirect:list.do");
		} catch (Exception e) {
			throw new ServletException(e);
			/*e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);*/
		} finally {}
	}
}
