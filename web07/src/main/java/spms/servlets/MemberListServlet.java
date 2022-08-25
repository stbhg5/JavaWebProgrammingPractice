package spms.servlets;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;

//프런트 컨트롤러 적용
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			
			//ServletContext에 저장된 DAO 객체 사용
			MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
			
			//DAO의 selectList() 메서드 반환값을 request에 담기
			request.setAttribute("members", memberDao.selectList());
			
			//response.setContentType("text/html; charset=UTF-8");
			//JSP로 출력을 위임한다.
			//RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
			//rd.include(request, response); //인클루딩
			
			//JSP URL 정보 프런트 컨트롤러에게 알리기 위해 ServletRequest 보관소에 저장
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
		} catch (Exception e) {
			throw new ServletException(e);
			/*
			e.printStackTrace();
			request.setAttribute("error", e); //예외 객체를 request에 보관
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response); //포워딩
			*/
		} finally {}
	}
}
