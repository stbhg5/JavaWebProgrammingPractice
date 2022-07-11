package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

//JSP 적용 - 입력폼 및 오류 처리 
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//신규 회원 링크 클릭시 GET 요청(a태그로 만들어진 링크 클릭)이 발생하기에 doGet() 메소드를 오버라이딩
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		/*PrintWriter out = response.getWriter();
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
		out.println("</body></html>");*/
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		rd.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//JDBC 객체를 보관할 참조 변수 선언
		Connection conn = null;
		PreparedStatement stmt = null;
		try {//데이터베이스 관련 코드는 try 블록에 둔다.
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
		    
			/*stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())"); //? : 입력 매개변수(입력 매개변수의 번호는 1부터 시작)
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			//SQL문 서버에 보냄
			stmt.executeUpdate();*/
		    memberDao.insert(new Member()
		             .setEmail(request.getParameter("email"))
		             .setPassword(request.getParameter("password"))
		             .setName(request.getParameter("name")));
			//리다이렉트를 이용한 리프래시
			response.sendRedirect("list");
		} catch (Exception e) {//JDBC 프로그래밍에서 예외처리
			//throw new ServletException(e);
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally {//회원정보 입력의 성공여부에 상관없이 반드시 수행
			//사용한 자원 해제
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
}
