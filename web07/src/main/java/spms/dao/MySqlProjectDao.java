package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import spms.annotation.Component;
import spms.vo.Project;

//mybatis적용
@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	
	//SqlSessionFactory 인터페이스
	SqlSessionFactory sqlSessionFactory;
	
	/**
	 * SqlSessionFactory 인터페이스 객체 주입
	 * @param sqlSessionFactory
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	/**
	 * 프로젝트 목록 조회
	 * @param paramMap
	 * @return sqlSession.selectList("spms.dao.ProjectDao.selectList")
	 * @throws Exception
	 */
	public List<Project> selectList(HashMap<String,Object> paramMap) throws Exception {
		//SqlSession : SQL 실행하는 도구, sqlSessionFactory 객체 통해서만 얻을 수 있다
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//selectList() : 여러 개의 결과 반환하는 SELECT문 실행시 호출
			return sqlSession.selectList("spms.dao.ProjectDao.selectList", paramMap);
		} finally {
			//close() : SQL문 실행할 때 사용한 자원 해제
			sqlSession.close();
		}
	}

	/**
	 * 프로젝트 등록
	 * @param project
	 * @return count
	 */
	public int insert(Project project) throws Exception  {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//자동 커밋(Auto-commit)을 수행하는 sqlSession 객체 반환
		//SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try {
			//insert() : 두 번째 매개변수로 프로젝트 정보 담은 값 객체 전달
			int count = sqlSession.insert("spms.dao.ProjectDao.insert", project);
			//임시 데이터베이스에 보관된 작업 결과를 운영 데이터베이스에 적용
			sqlSession.commit();
			//임시 데이터베이스에 보관된 작업 결과를 운영 데이터베이스에 적용하지 않고 취소
			//sqlSession.rollback();
			return count;
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	 * 프로젝트 상세정보 조회
	 * @param no
	 * @return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no)
	 */
	public Project selectOne(int no) throws Exception { 
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//selectOne() : 두 번째 매개변수로 int 값 전달(컴파일 시 Integer 객체로 자동 포장(Auto-boxing) : new Integer(no))
			return sqlSession.selectOne("spms.dao.ProjectDao.selectOne", no);
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 프로젝트 수정
	 * @param project
	 * @return count
	 */
	public int update(Project project) throws Exception { 
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//원래의 프로젝트 정보 가져옴
			Project original = sqlSession.selectOne("spms.dao.ProjectDao.selectOne", project.getNo());
			//바뀐 값 저장할 Map 객체
			Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
			//원래의 값과 사용자가 입력한 값 비교
			if(!project.getTitle().equals(original.getTitle())) {
				paramMap.put("title", project.getTitle());
			}
			if(!project.getContent().equals(original.getContent())) {
				paramMap.put("content", project.getContent());
			}
			if(project.getStartDate().compareTo(original.getStartDate()) != 0) {
				paramMap.put("startDate", project.getStartDate());
			}
			if(project.getEndDate().compareTo(original.getEndDate()) != 0) {
				paramMap.put("endDate", project.getEndDate());
			}
			if(project.getState() != original.getState()) {
				paramMap.put("state", project.getState());
			}
			if(!project.getTags().equals(original.getTags())) {
				paramMap.put("tags", project.getTags());
			}
			if(paramMap.size() > 0) {//바뀐값O
				paramMap.put("no", project.getNo());
				//update() : 두 번째 매개변수로 프로젝트 정보 담은 값 객체 전달
				//int count = sqlSession.update("spms.dao.ProjectDao.update", project);
				int count = sqlSession.update("spms.dao.ProjectDao.update", paramMap);
				//임시 데이터베이스에 보관된 작업 결과를 운영 데이터베이스에 적용
				sqlSession.commit();
				return count;
			}else {//바뀐값X
	    		return 0;
	    	}
		} finally {
			sqlSession.close();
		}
	}

	/**
	 * 프로젝트 삭제
	 * @param no
	 * @return count
	 */
	public int delete(int no) throws Exception {  
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			//delete() : 두 번째 매개변수로 int 값 전달(컴파일 시 Integer 객체로 자동 포장(Auto-boxing) : new Integer(no))
			int count = sqlSession.delete("spms.dao.ProjectDao.delete", no);
			//임시 데이터베이스에 보관된 작업 결과를 운영 데이터베이스에 적용
			sqlSession.commit();
			return count;
		} finally {
			sqlSession.close();
		}
	}
	
	/*
	//DataSource 인터페이스
	DataSource ds;
	
	//DataSource 인터페이스 객체 주입
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	//프로젝트 목록 조회
	@Override
	public List<Project> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
				"SELECT PNO, PNAME, STA_DATE, END_DATE, STATE "
			  + "FROM PROJECTS "
			  + "ORDER BY PNO DESC"
			);
			ArrayList<Project> projects = new ArrayList<Project>();
			while(rs.next()) {
				projects.add(new Project().setNo(rs.getInt("PNO"))
										  .setTitle(rs.getString("PNAME"))
										  .setStartDate(rs.getDate("STA_DATE"))
								          .setEndDate(rs.getDate("END_DATE"))
								          .setState(rs.getInt("STATE"))	);
			}
			return projects;
		} catch (Exception e) {
			throw e;
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	//프로젝트 등록
	@Override
	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.prepareStatement(
				"INSERT INTO PROJECTS (PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE, TAGS) "
			  + "VALUES (?, ?, ?, ?, 0, NOW(), ?)");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setString(5, project.getTags());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	//프로젝트 상세정보 조회
	@Override
	public Project selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
				"SELECT PNO, PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE, TAGS "
			  + "FROM PROJECTS "
			  + "WHERE PNO=" + no);
			if(rs.next()) {
				return new Project().setNo(rs.getInt("PNO"))
									.setTitle(rs.getString("PNAME"))
									.setContent(rs.getString("CONTENT"))
									.setStartDate(rs.getDate("STA_DATE"))
									.setEndDate(rs.getDate("END_DATE"))
									.setState(rs.getInt("STATE"))
									.setCreatedDate(rs.getDate("CRE_DATE"))
									.setTags(rs.getString("TAGS"));
			}else {
				throw new Exception("해당 번호의 프로젝트를 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	//프로젝트 수정
	@Override
	public int update(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.prepareStatement(
				"UPDATE PROJECTS SET PNAME=?, CONTENT=?, STA_DATE=?, END_DATE=?, STATE=?, TAGS=? "
			  + "WHERE PNO=?");
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setInt(5, project.getState());
			stmt.setString(6, project.getTags());
			stmt.setInt(7, project.getNo());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	
	//프로젝트 삭제
	@Override
	public int delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.createStatement();
			return stmt.executeUpdate(
				"DELETE FROM PROJECTS WHERE PNO=" + no);
		} catch (Exception e) {
			throw e;
		} finally {
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			try {if(connection != null) connection.close();} catch(Exception e) {}
		}
	}
	*/
}