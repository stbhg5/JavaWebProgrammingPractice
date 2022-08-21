package spms.dao;

import java.util.List;

import spms.vo.Project;

//ProjectDao 인터페이스 정의
public interface ProjectDao {
	List<Project> selectList() throws Exception; //프로젝트 목록 반환 메서드
	int insert(Project project) throws Exception; //프로젝트 데이터 등록 메서드
}