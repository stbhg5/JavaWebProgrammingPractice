package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CalculatorServlet extends GenericServlet {
	
	private static final long serialVersionUID = 1L;
	
	//GenericServlet 추상클래스 : 하위 클래스에게 공통의 필드와 메소드를 상속해 주고자 존재한다.
	//Servlet 인터페이스를 구현 받아 init(), destroy(), getServletConfig(), getServletInfo() 메소드를 미리 구현한 클래스이다.
	//GenericServlet 추상클래스 상속받은 클래스는 Servlet 인터페이스의 메소드 중 service()만 구현하면 된다.

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		
		/* 
		ServletRequest 클래스 : 클라이언트의 요청 정보를 다룰 때 사용
		↓ 주요 메소드
		getRemoteAddr() : 서비스를 요청한 클라이언트의 IP 주소 반환
		getScheme() : 클라이언트가 요청한 URI 형식 Scheme 반환 (URL에서 ':'문자 전에 오는 값 반환)
					  URL의 스킴이란 자원을 식별하는 최상위 분류 기호다. http와 https(프로토콜), ftp와 news(프로토콜X) 등이 있다.
		getProtocol() : 요청 프로토콜의 이름과 버전을 반환 ex) HTTP/1.1
		getParameterNames() : 요청 정보에서 매개변수 이름만 추출하여 반환
		getParameterValues() : 요청 정보에서 매개변수 값만 추출하여 반환
		getParameterMap() : 요청 정보에서 매개변수들을 Map 객체에 담아서 반환
		setCharacterEncoding() : POST 요청의 매개변수에 대해 문자 집합을 설정. 기본값은 ISO-8859-1.
								 매개변수의 문자 집합을 정확히 지정해야 제대로 변환된 유니코드 값을 반환하고
								 매개변수의 문자 집합을 지정하지 않으면 무조건 ISO-8859-1이라 가정하고 유니코드로 변환.
								 주의할 점은 getParameter()를 호출하기 전에 이 메소드를 호출해야만 적용된다.
		getParameter() : GET, POST 요청으로 들어온 매개변수 값을 꺼낼 때 사용
		
		ServletResponse 클래스 : 응답과 관련된 기능을 제공
		↓ 주요 메소드
		setContentType() : 출력할 데이터의 인코딩 형식과 문자 집합 지정.
						   클라이언트에게 출력할 데이터의 정보를 알려 그 형식에 맞게 올바르게 화면에 출력(Rendering)하게 함.
						   (HTML이면 태그규칙에 맞춰 출력, XML이면 각 태그를 트리노드로 표현, text/plain이면 출력할 데이터가 텍스트이고
						   별도의 메타정보가 없는 순수한 텍스트임 지정)
						   (setContentType("text/plain;chartset=UTF-8")처럼 출력 데이터의 문자 집합을 지정할 수 있지만
						   인자형식이 올바르지 않으면 웹 브라우저는 서버에서 받은 결과를 화면에 출력하는 대신 파일 저장 대화 창을 띄우므로
						   잘못된 값을 입력하지 않도록 유의해야 한다)
	    setCharacterEncoding() : 출력할 데이터의 문자 집합 지정. 기본값은 ISO-8859-1.
	    						 (유니코드 값을 UTF-8로 설정하면 한글 출력 가능. 문자 집합을 지정하지 않으면 모든 문자를 영어로 간주하여
	    						 한글은 '?'문자로 변환되어 출력된다.)
	    getWriter() : 클라이언트로 출력할 수 있도록 스트림 객체 반환 PrintWriter 클래스로 데이터를 넣을 수 있다.
	    			  (setContentType()이나 setCharacterEncoding()가 먼저 호출된 이후 호출되어야 정상적으로 유니코드가 지정된
	    			  문자 집합으로 변환된다)
	    getOutputStream() : 이미지나 동영상과 같은 바이너리 데이터 출력
		*/
		
		int a = Integer.parseInt( request.getParameter("a") );
		int b = Integer.parseInt( request.getParameter("b") );

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("a=" + a + "," + "b=" + b + "의 계산결과 입니다.");
		writer.println("a + b = " + (a + b));
		writer.println("a - b = " + (a - b));
		writer.println("a * b = " + (a * b));
		writer.println("a / b = " + ((float)a / (float)b));
		writer.println("a % b = " + (a % b));
		
	}
	
}
