package spms.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;

import javax.servlet.annotation.WebListener;

import spms.dao.MemberDao;

//리스너의 배치 : @WebListener 어노테이션 사용
@WebListener
public class ContextLoaderListener implements ServletContextListener {
	Connection conn; //인스턴스 변수
  
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			//DB 커넥션 객체 준비
			ServletContext sc = event.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			//MemberDao 객체 준비하여 ServletContext에 보관
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			sc.setAttribute("memberDao", memberDao);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			conn.close(); //데이터베이스와 연결 끊기
		} catch (Exception e) {}
	}
}