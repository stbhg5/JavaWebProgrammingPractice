package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.controls.Controller;
import spms.controls.MemberListController;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do") //프런트 컨트롤러의 배치
public class DispatcherServlet extends HttpServlet {//서블릿이기 때문에 HttpServlet 상속
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath(); //서블릿 경로 추출
		try {
			ServletContext sc = this.getServletContext();
			
			//페이지 컨트롤러에게 전달할 Map 객체 준비
			HashMap<String,Object> model = new HashMap<String,Object>();
			model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			//String pageControllerPath = null;
			//인터페이스 타입의 참조 변수 선언 - Controller의 구현체 클래스들 객체 주소 저장 위함
			Controller pageController = null;
			
			//페이지 컨트롤러로 위임
			if("/member/list.do".equals(servletPath)) {
				//pageControllerPath = "/member/list";
				pageController = new MemberListController();
			}else if("/member/add.do".equals(servletPath)) {
				//pageControllerPath = "/member/add";
				pageController = new MemberAddController();
				if(request.getParameter("email") != null) {
					//요청 매개변수로부터 VO 객체 준비
					/*request.setAttribute("member", new Member().setEmail(request.getParameter("email"))
															   .setPassword(request.getParameter("password"))
															   .setName(request.getParameter("name")));*/
					//Map 객체에 VO 객체 준비
					model.put("member", new Member().setEmail(request.getParameter("email"))
													.setPassword(request.getParameter("password"))
													.setName(request.getParameter("name")));
				}
			}else if("/member/update.do".equals(servletPath)) {
				//pageControllerPath = "/member/update";
				pageController = new MemberUpdateController();
				if(request.getParameter("email") != null) {
					//요청 매개변수로부터 VO 객체 준비
					/*request.setAttribute("member", new Member().setNo(Integer.parseInt(request.getParameter("no")))
															   .setEmail(request.getParameter("email"))
															   .setName(request.getParameter("name")));*/
					//Map 객체에 VO 객체 준비
					model.put("member", new Member().setNo(Integer.parseInt(request.getParameter("no")))
													.setEmail(request.getParameter("email"))
													.setName(request.getParameter("name")));
				}else {
					model.put("no", new Integer(request.getParameter("no")));
		        }
			}else if("/member/delete.do".equals(servletPath)) {
				//pageControllerPath = "/member/delete";
				pageController = new MemberDeleteController();
				model.put("no", new Integer(request.getParameter("no")));
			}else if("/auth/login.do".equals(servletPath)) {
				//pageControllerPath = "/auth/login";
				pageController = new LogInController();
				if(request.getParameter("email") != null) {
					model.put("loginInfo", new Member().setEmail(request.getParameter("email"))
													   .setPassword(request.getParameter("password")));
				}
			}else if("/auth/logout.do".equals(servletPath)) {
				//pageControllerPath = "/auth/logout";
				pageController = new LogOutController();
			}
			
			//페이지 컨트롤러로 실행 위임
			/*RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);*/
			
			//페이지 컨트롤러 실행
			String viewUrl = pageController.execute(model);
			
			//Map 객체에 저장된 값을 ServletRequest에 복사
			for(String key : model.keySet()) {//keySet() : key값 가져옴
				request.setAttribute(key, model.get(key)); //model.get(key) : value값 가져옴
			}

			//JSP로 위임
			/*String viewUrl = (String)request.getAttribute("viewUrl");
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}*/
			//JSP로 실행 위임
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
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
