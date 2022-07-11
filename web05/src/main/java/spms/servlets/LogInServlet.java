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

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그인 페이지로 포워딩
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			ServletContext sc = this.getServletContext();
			//conn = (Connection)sc.getAttribute("conn");  
			/*stmt = conn.prepareStatement(
					"SELECT MNAME,EMAIL FROM MEMBERS"
					+ " WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			rs = stmt.executeQuery();
			if(rs.next()) {//이메일, 암호 일치하는 회원 있을때 - 로그인 성공
				Member member = new Member().setEmail(rs.getString("EMAIL"))
											.setName(rs.getString("MNAME"));
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				//회원 목록 페이지로 리다이렉트
				response.sendRedirect("../member/list");
			}else {//이메일, 암호 일치하는 회원 없을때 - 로그인 실패
				//로그인 실패 페이지로 포워딩
				RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(request, response);
			}*/
			/*MemberDao memberDao = new MemberDao();
		    memberDao.setConnection(conn);*/
			
			//ServletContext에 저장된 DAO 객체 사용
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
		    Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));
		    if(member != null) {
		    	HttpSession session = request.getSession();
		        session.setAttribute("member", member);
		        response.sendRedirect("../member/list");
		    }else {
		        RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
		        rd.forward(request, response);
		    }
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			try {if (rs != null) rs.close();} catch (Exception e) {}
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
		}
	}
}
