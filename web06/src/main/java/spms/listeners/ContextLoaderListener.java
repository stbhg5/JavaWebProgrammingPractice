package spms.listeners;

//import java.sql.SQLException;

import javax.naming.InitialContext;
//import java.sql.Connection;
//import java.sql.DriverManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;

import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import spms.dao.MemberDao;
//import spms.util.DBConnectionPool;

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
			//DBConnectionPool 객체 생성
			/*connPool = new DBConnectionPool(sc.getInitParameter("driver")
										   ,sc.getInitParameter("url")
										   ,sc.getInitParameter("username")
										   ,sc.getInitParameter("password"));*/ //DB커넥션 생성에 필요한 값 준비
			//BasicDataSource 객체 생성
			/*ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));*/
			//InitialContext 객체 생성 - 톰캣 서버에서 자원 찾기 위함
			InitialContext initialContext = new InitialContext();
			//lookup() 메서드로 JNDI 이름으로 등록된 서버 자원 찾을 수 있다.
			//lookup() 메서드 매개변수는 서버 자원의 JNDI 이름
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/studydb");
			
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
		//try {if(ds != null) ds.close();} catch (SQLException e) {}
		//톰캣 서버에 자동으로 해제하라고 설정되어 있기 때문에 메서드가 비어있다.
		//context.xml 파일의 <Resource> 태그의 closeMethod 속성값이 close라고 되어있기 때문에 contextDestroyed() 메서드는 비어 있게 된다.
	}
}