package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MySqlProjectDao;

@Component("/project/list.do")
public class ProjectListController implements Controller {
	
	//인스턴스 변수
	MySqlProjectDao projectDao;
	
	//셋터 메서드
	public ProjectListController setProjectDao(MySqlProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}

	//Controller 인터페이스 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("projects", projectDao.selectList());
		return "/project/ProjectList.jsp";
	}
	
}