package spms.listeners;

import java.io.InputStream;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import spms.context.ApplicationContext;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

//SqlSessionFactory 객체 준비
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
			//기본 생성자 호출
			applicationContext = new ApplicationContext();
			
			//빌더 패턴(Builder Pattern) 객체 생성
			String resource = "spms/dao/mybatis-config.xml";
			//getResourceAsStream() : 자바 클래스 경로(CLASSPATH)에 있는 파일의 입력 스트림 얻음
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			//ApplicationContext 클래스에 SqlSessionFactory 객체 등록
			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);
			
			//DB 커넥션 객체 준비
			ServletContext sc = event.getServletContext();
			
			//web.xml 파일로부터 프로퍼티 파일 이름과 경로 정보 읽어옴
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			//applicationContext = new ApplicationContext(propertiesPath);
			//프로퍼티 파일 내용에 따라 객체 생성
			applicationContext.prepareObjectsByProperties(propertiesPath);
			//@Component 어노테이션이 붙은 클래스 찾아 객체 생성
			applicationContext.prepareObjectsByAnnotation("");
			//ApplicationContext 클래스에서 관리하는 객체들 의존 객체 주입
			applicationContext.injectDependency();
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}
}