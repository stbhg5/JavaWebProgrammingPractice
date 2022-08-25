package spms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//프런트 컨트롤러 적용
@WebServlet("/auth/logout")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//세션 객체 무효화(객체 제거)
		session.invalidate();
		//response.sendRedirect("login");
		//리다이렉트를 위한 뷰 URL 설정
		request.setAttribute("viewUrl", "redirect:login.do");
	}
}
