package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//신규 회원 링크 클릭시 GET 요청(a태그로 만들어진 링크 클릭)이 발생하기에 doGet() 메소드를 오버라이딩
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>"); //action : 실행할 서블릿 URL 주소, method : 서버에 요청하는 방식 지정
														  //a태그의 href와 마찬가지로 action에서도 URL이 '/'로 시작하면 절대 경로, '/'로 시작하지 않으면 상대 경로.
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>"); //submit : 서버에 요청을 보내는 버튼
		out.println("<input type='reset' value='취소'>"); //reset : 입력폼을 초기화시키는 버튼
		out.println("</form>");
		out.println("</body></html>");
	}
	
}
