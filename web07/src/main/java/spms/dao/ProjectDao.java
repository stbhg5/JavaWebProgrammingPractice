package spms.dao;

import java.util.HashMap;
import java.util.List;

import spms.vo.Project;

//ProjectDao 인터페이스 정의
public interface ProjectDao {
	List<Project> selectList(HashMap<String,Object> paramMap) throws Exception; //프로젝트 목록 반환 메서드
	int insert(Project project) throws Exception; //프로젝트 데이터 등록 메서드
	Project selectOne(int no) throws Exception; //프로젝트 상세정보 조회 메서드
	int update(Project project) throws Exception; //프로젝트 데이터 변경 메서드
	int delete(int no) throws Exception; //프로젝트 데이터 삭제 메서드
}