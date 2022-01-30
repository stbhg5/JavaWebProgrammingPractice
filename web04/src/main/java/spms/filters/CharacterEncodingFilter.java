package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/*
@WebFilter(
	urlPatterns="/*",
	initParams={
		@WebInitParam(name="encoding",value="UTF-8")
	})
*/
public class CharacterEncodingFilter implements Filter {
	
	//FilterConfig : 필터 초기화 매개변수의 값을 꺼낼 수 있다.
	FilterConfig config;
	
	/**
	 * init() : 필터 객체가 생성되고 나서 준비 작업을 위해 딱 한 번 호출됨
	 * @param config
	 * @return 
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	/**
	 * doFilter() : 필터와 연결된 URL에 대해 요청이 들어오면 항상 호출된다. 필터가 할 일 실행.
	 * @param request,response,nextFilter
	 * @return 
	 * @throws IOException,ServletException
	 */
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain nextFilter) throws IOException, ServletException {
		
		//서블릿이 실행되기 전, 해야할 작업
		request.setCharacterEncoding(config.getInitParameter("encoding"));
		
		//다음 필터를 호출, 더 이상 필터가 없다면 내부적으로 서블릿의 service()가 호출됨.
		nextFilter.doFilter(request, response);
		
		//서블릿을 실행한 후, 클라이언트에게 응답하기 전에 해야할 작업
		
	}
	
	/**
	 * destroy() : 웹 애플리케이션을 종료하기 전 서블릿 컨테이너에게 호출되어 마무리 작업을 함
	 * @param 
	 * @return 
	 * @throws 
	 */
	@Override
	public void destroy() {}
	
}
