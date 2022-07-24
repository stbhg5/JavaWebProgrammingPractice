package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do") //프런트 컨트롤러의 배치
public class DispatcherServlet extends HttpServlet {//서블릿이기 때문에 HttpServlet 상속
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath(); //서블릿 경로 추출
		try {
			String pageControllerPath = null;
			
			//페이지 컨트롤러로 위임
			if("/member/list.do".equals(servletPath)) {
				pageControllerPath = "/member/list";
			}else if("/member/add.do".equals(servletPath)) {
				pageControllerPath = "/member/add";
				if(request.getParameter("email") != null) {
					//요청 매개변수로부터 VO 객체 준비
					request.setAttribute("member", new Member().setEmail(request.getParameter("email"))
															   .setPassword(request.getParameter("password"))
															   .setName(request.getParameter("name")));
				}
			}else if("/member/update.do".equals(servletPath)) {
				pageControllerPath = "/member/update";
				if(request.getParameter("email") != null) {
					//요청 매개변수로부터 VO 객체 준비
					request.setAttribute("member", new Member().setNo(Integer.parseInt(request.getParameter("no")))
															   .setEmail(request.getParameter("email"))
															   .setName(request.getParameter("name")));
				}
			}else if("/member/delete.do".equals(servletPath)) {
				pageControllerPath = "/member/delete";
			}else if("/auth/login.do".equals(servletPath)) {
				pageControllerPath = "/auth/login";
			}else if("/auth/logout.do".equals(servletPath)) {
				pageControllerPath = "/auth/logout";
			}
			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);

			//JSP로 위임
			String viewUrl = (String)request.getAttribute("viewUrl");
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		} catch (Exception e) {//오류 처리
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
