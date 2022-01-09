package lesson03.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet {
	
	//Servlet 구현 : 서블릿 클래스는 반드시 javax.servlet.Servlet 인터페이스를 구현해야 한다.
	//서블릿 컨테이너가 서블릿에 대해 호출할 메서드를 정의한 것이 Servlet 인터페이스다.
	
	/*
	1. 서블릿 생명주기(생성과 실행, 소멸 - Lifecycle)와 관련된 메소드 : init(), service(), destroy()
	2. 서블릿 정보를 추출할 필요가 있을 때 호출하는 메소드(보조 메소드) : getServletConfig(), getServletInfo()
	(서블릿 컨테이너 관리자 페이지에서 서블릿 정보를 출력할 때도 사용)
	*/ 
	
	ServletConfig config;
	
	/**
	 * init() : 서블릿 컨테이너가 서블릿 생성 후 초기화 작업을 수행하기 위해 호출하는 메소드
	 * EX) DB 연결, 외부 스토리지 서버 연결, 프로퍼티 로딩 등 클라이언트 요청 처리의 필요한 자원 미리 준비
	 * @param config
	 * @return 
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init() 호출됨");
		this.config = config;
	}
	
	/**
	 * destroy() : 서블릿 컨테이너가 종료되거나 웹 애플리케이션이 멈출 때, 해당 서블릿을 비활성화 시킬 때 호출되는 메소드
	 * EX) 서비스 수행을 위해 확보했던 자원 해제, 데이터 저장 등 마무리 작업
	 * @param 
	 * @return 
	 * @throws 
	 */
	@Override
	public void destroy() {
		System.out.println("destroy() 호출됨");
	}
	
	/**
	 * service() : 클라이언트가 요청할 때마다 호출되는 메소드, 실질적으로 서비스 작업을 수행
	 * EX) 서블릿이 해야 할 일
	 * @param request, response
	 * @return 
	 * @throws ServletException, IOException
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		System.out.println("service() 호출됨");
	}
	
	/**
	 * getServletConfig() : 서블릿 설정 정보를 다루는 ServletConfig 객체 반환.
	 * EX) 서블릿 이름, 서블릿 초기 매개변수 값, 서블릿 환경정보를 얻을 수 있다.
	 * @param 
	 * @return this.config
	 * @throws 
	 */
	@Override
	public ServletConfig getServletConfig() {
		System.out.println("getServletConfig() 호출됨");
		return this.config;
	}
	
	/**
	 * getServletInfo() : 서블릿을 작성한 사람에 대한 정보, 서블릿 버전, 권리 등을 담은 문자열 반환
	 * EX) 서블릿 작성자 정보, 서블릿 버전, 권리 등을 담은 문자열
	 * @param 
	 * @return "version=1.0;author=eomjinyoung;copyright=eomjinyoung 2013"
	 * @throws 
	 */
	@Override
	public String getServletInfo() {
		System.out.println("getServletInfo() 호출됨");
		return "version=1.0;author=eomjinyoung;copyright=eomjinyoung 2013";
	}
	
}
