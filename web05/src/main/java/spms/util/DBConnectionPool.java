package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	
	String url;
	String username;
	String password;
	ArrayList<Connection> connList = new ArrayList<Connection>(); //Connection 객체 보관할 ArrayList
	
	//DB커넥션 생성에 필요한 값 준비
	public DBConnectionPool(String driver, String url, String username, String password) throws Exception {
		this.url = url;
		this.username = username;
		this.password = password;
		
		Class.forName(driver);
	}
	
	//DB 커넥션 객체 가져오기
	public Connection getConnection() throws Exception {
		if(connList.size() > 0) {//유효성 체크
			//Connection conn = connList.remove(0);
			Connection conn = connList.get(0);
			if(conn.isValid(10)) {
				return conn;
			}
		}
		return DriverManager.getConnection(url, username, password);
	}

	//DB 커넥션 객체 connList에 반환(반납)
	public void returnConnection(Connection conn) throws Exception {
		connList.add(conn);
	}

	//웹 애플리케이션 종료 전 데이터베이스와 연결된 것 끊기
	public void closeAll() {
		for(Connection conn : connList) {
			try{conn.close();} catch (Exception e) {}
		}
	}
}