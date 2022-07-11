package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		try {
			ServletContext sc = this.getServletContext();
			/*Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password"));*/
			//ServletContext에 저장된 DB 커넥션 사용
			/*conn = (Connection) sc.getAttribute("conn");
			MemberDao memberDao = new MemberDao();
		    memberDao.setConnection(conn);*/
			
			//ServletContext에 저장된 DAO 객체 사용
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
		    memberDao.delete(Integer.parseInt(request.getParameter("no")));
			/*stmt = conn.createStatement();
			stmt.executeUpdate(
					"DELETE FROM MEMBERS WHERE MNO=" + 
					request.getParameter("no"));*/
			response.sendRedirect("list");
		} catch (Exception e) {
			//throw new ServletException(e);
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
}
