package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

//JSP 적용 - 변경폼 및 예외 처리
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			//HttpServlet 클래스로부터 상속받은 getServletContext()를 호출하여 ServletContext 객체 준비
			ServletContext sc = this.getServletContext();
			
			//getInitParameter()를 호출하면 web.xml에 선언된 컨텍스트 초기화 매개변수 값을 얻음
			/*Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password"));*/
			//ServletContext에 저장된 DB 커넥션 사용
			//conn = (Connection) sc.getAttribute("conn");
			//회원 상세 정보 출력
			/*stmt = conn.createStatement();
			rs = stmt.executeQuery(
				"SELECT MNO,EMAIL,MNAME,CRE_DATE FROM MEMBERS" + 
				" WHERE MNO=" + request.getParameter("no"));*/
			/*MemberDao memberDao = new MemberDao();
		    memberDao.setConnection(conn);*/
			
			//ServletContext에 저장된 DAO 객체 사용
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
		       
		    Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));
		    request.setAttribute("member", member);
			/*rs.next();
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원정보</title></head>");
			out.println("<body><h1>회원정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" + request.getParameter("no") + "' readonly><br>"); //회원번호 : 주 키(Primary Key) 칼럼이므로 값 변경 불가능. readonly속성은 값없이 속성 이름만 추가 가능.
			out.println("이름: <input type='text' name='name'" + " value='" + rs.getString("MNAME")  + "'><br>");
			out.println("이메일: <input type='text' name='email'" + " value='" + rs.getString("EMAIL")  + "'><br>");
			out.println("가입일: " + rs.getDate("CRE_DATE") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='삭제' " + "onclick='location.href=\"delete?no=" + request.getParameter("no") + "\";'>");
			out.println("<input type='button' value='취소'" + " onclick='location.href=\"list\"'>"); //onclick속성에 자바스크립트로 입력.
			//location : 웹 브라우저의 페이지 이동을 관리하는 자바스크립트 객체
			//href 프로퍼티 : 웹 브라우저가 출력할 페이지의 URL을 설정.
			out.println("</form>");
			out.println("</body></html>");*/
			
			/*if(rs.next()) {
				request.setAttribute("member", new Member().setNo(rs.getInt("MNO"))
														   .setEmail(rs.getString("EMAIL"))
														   .setName(rs.getString("MNAME"))
														   .setCreatedDate(rs.getDate("CRE_DATE")));
			}else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}*/
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			//throw new ServletException(e);
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement stmt = null;
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
			
		    memberDao.update(new Member().setNo(Integer.parseInt(request.getParameter("no")))
		    	      					 .setName(request.getParameter("name"))
		    	      					 .setEmail(request.getParameter("email")));
			/*stmt = conn.prepareStatement(
					"UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()" //now() : MySQL에서 제공하는 데이터베이스 함수. 현재 날짜와 시간 반환.
					+ " WHERE MNO=?");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
			stmt.executeUpdate();*/
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
