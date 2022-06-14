package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

//UI 출력 코드를 제거하고, UI 생성 및 출력을 JSP에게 위임한다.
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {//extends GenericServlet
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//데이터베이스 관련 코드를 위한 try ~ catch 블록 (JDBC API 사용시 예외가 발생할 수 있기 때문)
		try {
			//JDBC 드라이버를 로딩하고 데이터베이스 연결 시 컨텍스트 초기화 매개변수에서 해당 정보 가져와 처리.
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			response.setContentType("text/html; charset=UTF-8");
			/*
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			out.println("<p><a href='add'>신규 회원</a></p>");
			while(rs.next()) {
				out.println(
					rs.getInt("MNO") + "," +		//getInt(1)
					"<a href='update?no=" + rs.getInt("MNO") + "'>" + //회원 상세정보 출력 서블릿 URL 추가
					rs.getString("MNAME") + "</a>," +	//getString(2)
					rs.getString("EMAIL") + "," + 	//getString(3)
					//rs.getDate("CRE_DATE") + "<br>" //getDate(4)
					rs.getDate("CRE_DATE") + 
					"<a href='delete?no=" + rs.getInt("MNO") + "'>[삭제]</a><br>"
				);
			}
			out.println("</body></html>");
			*/
			ArrayList<Member> members = new ArrayList<Member>();
			//데이터베이스에서 회원 정보를 가져와 Member에 담는다.
			//그리고 Member객체를 ArrayList에 추가한다.
			while(rs.next()) {
				members.add(new Member().setNo(rs.getInt("MNO"))
										.setName(rs.getString("MNAME"))
										.setEmail(rs.getString("EMAIL"))
										.setCreatedDate(rs.getDate("CRE_DATE"))	);
			}
			//request에 회원 목록 데이터 보관한다.
			request.setAttribute("members", members);
			//JSP로 출력을 위임한다.
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
			rd.include(request, response);
		} catch (Exception e) {
			//throw new ServletException(e);
			request.setAttribute("error", e); //예외 객체를 request에 보관
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}
}
