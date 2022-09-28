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

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.context.ApplicationContext;
import spms.controls.Controller;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.listeners.ContextLoaderListener;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do") //프런트 컨트롤러의 배치
public class DispatcherServlet extends HttpServlet {//서블릿이기 때문에 HttpServlet 상속
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String servletPath = request.getServletPath(); //서블릿 경로 추출
		try {
			//ApplicationContext 객체 준비
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			
			//페이지 컨트롤러에게 전달할 Map 객체 준비
			HashMap<String,Object> model = new HashMap<String,Object>();
			//model.put("memberDao", sc.getAttribute("memberDao")); //memberDao 객체는 더이상 Map 객체에 담을 필요 없음.
			model.put("session", request.getSession()); //로그인 및 로그아웃 페이지 컨트롤러에서 사용할 세션 객체

			//ApplicationContext 객체에서 페이지 컨트롤러 꺼낼 때 서블릿 URL 사용
			Controller pageController = (Controller)ctx.getBean(servletPath);
			//찾지 못한다면 오류 발생시킴
			if(pageController == null) {
				throw new Exception("요청한 서비스를 찾을 수 없습니다.");
			}
			
			//DataBinding 구현 여부 검사하여, 해당 인터페이스를 구현한 경우에만 호출
			if(pageController instanceof DataBinding) {
				prepareRequestData(request, model, (DataBinding)pageController);
			}
			
			//페이지 컨트롤러 실행
			String viewUrl = pageController.execute(model); //뷰 URL 반환받음
			
			//Map 객체에 저장된 값을 ServletRequest에 복사
			for(String key : model.keySet()) {//keySet() : key값 가져옴
				request.setAttribute(key, model.get(key)); //model.get(key) : value값 가져옴
			}

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
	
	//페이지 컨트롤러에 필요한 데이터 준비
	private void prepareRequestData(HttpServletRequest request, HashMap<String, Object> model, DataBinding dataBinding) throws Exception {
		//페이지 컨트롤러에게 필요한 데이터 묻기
		Object[] dataBinders = dataBinding.getDataBinders();
		//배열에서 꺼낸 값 보관할 임시 변수
		String dataName = null; //데이터 이름(String)
		Class<?> dataType = null; //데이터 타입(Class)
		Object dataObj = null; //데이터 객체(Object)
		//배열 반복하며 값 꺼내기
		for(int i=0 ; i < dataBinders.length ; i+=2) {
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			model.put(dataName, dataObj);
		}
	}
	
}