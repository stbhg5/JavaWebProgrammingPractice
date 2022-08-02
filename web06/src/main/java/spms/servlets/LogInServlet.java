package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//프런트 컨트롤러 적용
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그인 페이지로 포워딩
		/*RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(request, response);*/
		//JSP URL 정보 프런트 컨트롤러에게 알리기 위해 ServletRequest 보관소에 저장
		request.setAttribute("viewUrl", "/auth/LogInForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			//ServletContext에 저장된 DAO 객체 사용
			MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
			
		    Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));
		    if(member != null) {
		    	HttpSession session = request.getSession();
		        session.setAttribute("member", member);
		        //response.sendRedirect("../member/list");
		        //리다이렉트를 위한 뷰 URL 설정
		        request.setAttribute("viewUrl", "redirect:../member/list.do");
		    }else {
		        /*RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
		        rd.forward(request, response);*/
		    	//JSP URL 정보 프런트 컨트롤러에게 알리기 위해 ServletRequest 보관소에 저장
		    	request.setAttribute("viewUrl", "/auth/LogInFail.jsp");
		    }
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {}
	}
}
