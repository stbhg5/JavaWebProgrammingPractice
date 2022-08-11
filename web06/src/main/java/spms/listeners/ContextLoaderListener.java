package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;

import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import spms.context.ApplicationContext;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

//프로퍼티 파일 적용 : ApplicationContext 사용
@WebListener
public class ContextLoaderListener implements ServletContextListener {
	static ApplicationContext applicationContext;
	
	//ApplicationContext 객체 얻을 때 사용
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			//DB 커넥션 객체 준비
			ServletContext sc = event.getServletContext();
			
			//InitialContext 객체 생성 - 톰캣 서버에서 자원 찾기 위함
			//InitialContext initialContext = new InitialContext();
			//lookup() 메서드로 JNDI 이름으로 등록된 서버 자원 찾을 수 있다.
			//lookup() 메서드 매개변수는 서버 자원의 JNDI 이름
			//DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/studydb");
			
			//MemberDao 객체 준비하여 ServletContext에 보관
			//MemberDao memberDao = new MemberDao();
			//MySqlMemberDao memberDao = new MySqlMemberDao();
			
			//memberDao.setDataSource(ds); //DAO에 DataSource 객체 주입
			//sc.setAttribute("memberDao", memberDao); //memberDao 객체를 별도로 꺼내서 사용할 일 없기 때문에 ServletContext에 저장하지 않는다.
			
			//페이지 컨트롤러 객체를 ServletContext에 저장시, 서블릿 요청 URL을 키값으로 하여 저장
			/*sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new LogOutController());
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));*/
			
			//web.xml 파일로부터 프로퍼티 파일 이름과 경로 정보 읽어옴
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			applicationContext = new ApplicationContext(propertiesPath);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}
}