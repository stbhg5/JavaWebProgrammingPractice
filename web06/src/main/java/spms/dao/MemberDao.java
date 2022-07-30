package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.util.DBConnectionPool;
import spms.vo.Member;

public class MemberDao {
	//Connection connection;
	
	//Connection 객체 주입(의존성 주입, 역제어)
	/*public void setConnection(Connection connection) {
		this.connection = connection;
	}*/
	
	//DBConnectionPool connPool;
	
	//DBConnectionPool 객체 주입
	/*public void setDbConnectionPool(DBConnectionPool connPool) {
		this.connPool = connPool;
	}*/
	
	DataSource ds; //DataSource 인터페이스
	
	//DataSource 인터페이스 객체 주입 (다른 구현체로 손쉽게 대체하기 위함)
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	//회원 목록 조회
	public List<Member> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			//connection = connPool.getConnection(); //DB 커넥션 객체 가져오기
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			ArrayList<Member> members = new ArrayList<Member>();
			while(rs.next()) {
				members.add(new Member()
					   .setNo(rs.getInt("MNO"))
					   .setName(rs.getString("MNAME"))
					   .setEmail(rs.getString("EMAIL"))
					   .setCreatedDate(rs.getDate("CRE_DATE"))	);
			}
			return members;
		} catch (Exception e) {
			throw e;
		} finally {
			try {if(rs != null) rs.close();} catch(Exception e) {}
			try {if(stmt != null) stmt.close();} catch(Exception e) {}
			//if(connection != null) connPool.returnConnection(connection); //DB 커넥션 객체 반환(반납)
			try {if(connection != null) connection.close();} catch(Exception e) {} //커넥션 대행 객체(PoolableConnection)의 close() 호출
		}
	}
	
	//회원 등록
	public int insert(Member member) throws Exception  {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			//connection = connPool.getConnection(); //DB 커넥션 객체 가져오기
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//if (connection != null) connPool.returnConnection(connection); //DB 커넥션 객체 반환(반납)
			try {if(connection != null) connection.close();} catch(Exception e) {} //커넥션 대행 객체(PoolableConnection)의 close() 호출
		}
	}
	
	//회원 삭제
	public int delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		try {
			//connection = connPool.getConnection(); //DB 커넥션 객체 가져오기
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.createStatement();
			return stmt.executeUpdate(
					"DELETE FROM MEMBERS WHERE MNO=" + no);
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//if (connection != null) connPool.returnConnection(connection); //DB 커넥션 객체 반환(반납)
			try {if(connection != null) connection.close();} catch(Exception e) {} //커넥션 대행 객체(PoolableConnection)의 close() 호출
		}
	}
	
	//회원 상세 정보 조회
	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			//connection = connPool.getConnection(); //DB 커넥션 객체 가져오기
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,EMAIL,MNAME,CRE_DATE FROM MEMBERS" + 
					" WHERE MNO=" + no);    
			if(rs.next()) {
				return new Member().setNo(rs.getInt("MNO"))
								   .setEmail(rs.getString("EMAIL"))
								   .setName(rs.getString("MNAME"))
								   .setCreatedDate(rs.getDate("CRE_DATE"));
			}else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//if (connection != null) connPool.returnConnection(connection); //DB 커넥션 객체 반환(반납)
			try {if(connection != null) connection.close();} catch(Exception e) {} //커넥션 대행 객체(PoolableConnection)의 close() 호출
		}
	}
	
	//회원 정보 수정
	public int update(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			//connection = connPool.getConnection(); //DB 커넥션 객체 가져오기
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.prepareStatement(
					"UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()"
					+ " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//if (connection != null) connPool.returnConnection(connection); //DB 커넥션 객체 반환(반납)
			try {if(connection != null) connection.close();} catch(Exception e) {} //커넥션 대행 객체(PoolableConnection)의 close() 호출
		}
	}
	
	//있으면 Member 객체 리턴, 없으면 null 리턴
	public Member exist(String email, String password) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//connection = connPool.getConnection(); //DB 커넥션 객체 가져오기
			connection = ds.getConnection(); //커넥션 객체 가져오기
			stmt = connection.prepareStatement(
					"SELECT MNAME,EMAIL FROM MEMBERS"
					+ " WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new Member().setName(rs.getString("MNAME"))
								   .setEmail(rs.getString("EMAIL"));
			}else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch (Exception e) {}
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			//if (connection != null) connPool.returnConnection(connection); //DB 커넥션 객체 반환(반납)
			try {if(connection != null) connection.close();} catch(Exception e) {} //커넥션 대행 객체(PoolableConnection)의 close() 호출
		}
	}
}