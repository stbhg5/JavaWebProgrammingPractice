package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
	
	//javax.servlet.GenericServlet 클래스 상속
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		//JDBC 객체 주소를 보관할 참조 변수의 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//데이터베이스 관련 코드를 위한 try ~ catch 블록 (JDBC API 사용시 예외가 발생할 수 있기 때문)
		try {
			//JDBC 프로그래밍의 첫 번째 작업 : DriverManager를 이용한 java.sql.Driver 인터페이스 구현체 등록
			
			//MySQL 드라이버의 경우 com.mysql.jdbc.Driver 클래스가 java.sql.Driver 인터페이스 구현한 클래스다(mysql-connector-java-8.0.27.jar에 들어있다).
			DriverManager.registerDriver(new com.mysql.jdbc.Driver()); //registerDriver() 호출하여 구현체 등록
			
			//getConnection() : MySQL 서버에 연결
			conn = DriverManager.getConnection(
					
					//jdbc:mysql : JDBC 드라이버 이름
					// //localhost/studydb : 접속할 서버 주소(localhost)와 데이터베이스 이름(studydb)
					"jdbc:mysql://localhost/studydb", //JDBC URL(드라이버에 따라 조금씩 다름)
					"study",	// DBMS 사용자 아이디
					"study");	// DBMS 사용자 암호
			
			//DB 접속 정보를 다루는 객체 반환 : 데이터베이스에 SQL문을 보내는 객체
			stmt = conn.createStatement();
			
			//SQL문을 서버에 보내는 명령문 : SELECT 명령문을 보낼 때 executeQuery() 사용
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			
			//출력 스트림 얻기 전 setContentType() 호출하여 출력하려는 데이터의 형식(HTML)과 문자 집합(UTF-8)을 지정
			response.setContentType("text/html; charset=UTF-8");
			
			//HTML 태그 출력
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			
			//ResultSet rs 반환객체를 통해 서버에 질의 결과 가져올 수 있다.
			while(rs.next()) {//next()를 호출하면 서버에서 레코드(Record) 즉, 행(Row)을 가져온다. 서버에서 레코드를 받으면 true, 레코드가 없다면 false 반환.
				//서버에서 레코드 받는동안 계속해서 회원 정보를 한 줄의 문자열로 만들어 출력.
				//출력 문자열에서 칼럼들은 콤마(,)로 구분하고 문자열 끝에는 <br>태그를 붙여 줄바꿈.
				out.println(//칼럼이름				//칼럼인덱스(1부터 시작)
					rs.getInt("MNO") + "," +		//getInt(1)
					rs.getString("MNAME") + "," +	//getString(2)
					rs.getString("EMAIL") + "," + 	//getString(3)
					rs.getDate("CRE_DATE") + "<br>" //getDate(4)
				);
			}
			
			//HTML문 끝을 지정하는 태그
			out.println("</body></html>");
		} catch (Exception e) {
			//예외가 발생하면 ServletException 객체에 담아 이 메서드를 호출한 서블릿 컨테이너에 던진다.
			//서블릿 컨테이너는 예외에 따른 적절한 화면을 생성하여 클라이언트에게 출력한다.
			throw new ServletException(e);
			
		//JDBC 프로그래밍의 마무리 : 정상적으로 수행되든 오류가 발생하든 간에 반드시 자원해제 수행.
		} finally {//finally 블록 : try나 catch를 벗어나기 전에 반드시 수행됨.
			//자원해제는 역순으로 처리
			try {if (rs != null) rs.close();} catch(Exception e) {} //오류발생해도 특별히 할 작업이 없기 때문에 catch 블록 비워짐.
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
	
}
