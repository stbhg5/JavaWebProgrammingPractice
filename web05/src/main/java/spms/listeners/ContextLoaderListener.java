package spms.listeners;

import java.sql.SQLException;

//import java.sql.Connection;
//import java.sql.DriverManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;

import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.BasicDataSource;

import spms.dao.MemberDao;
import spms.util.DBConnectionPool;

//리스너의 배치 : @WebListener 어노테이션 사용
@WebListener
public class ContextLoaderListener implements ServletContextListener {
	//Connection conn; //인스턴스 변수
	//DBConnectionPool connPool;
	BasicDataSource ds;
  
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			//DB 커넥션 객체 준비
			ServletContext sc = event.getServletContext();
			/*Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));*/
			/*connPool = new DBConnectionPool(sc.getInitParameter("driver")
										   ,sc.getInitParameter("url")
										   ,sc.getInitParameter("username")
										   ,sc.getInitParameter("password"));*/ //DB커넥션 생성에 필요한 값 준비
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));
		    
			//MemberDao 객체 준비하여 ServletContext에 보관
			MemberDao memberDao = new MemberDao();
			//memberDao.setConnection(conn);
			//memberDao.setDbConnectionPool(connPool); //DAO에 DBConnectionPool 객체 주입
			memberDao.setDataSource(ds); //DAO에 DataSource 객체 주입
			sc.setAttribute("memberDao", memberDao);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		/*try {
			conn.close(); //데이터베이스와 연결 끊기
		} catch (Exception e) {}*/
		//connPool.closeAll(); //웹 애플리케이션 종료 전 데이터베이스와 연결된 것 끊기
		try {if(ds != null) ds.close();} catch (SQLException e) {}
	}
}