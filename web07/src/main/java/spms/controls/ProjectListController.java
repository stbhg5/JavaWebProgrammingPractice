package spms.controls;

import java.util.HashMap;
import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlProjectDao;

@Component("/project/list.do")
public class ProjectListController implements Controller, DataBinding {
	
	//인스턴스 변수
	MySqlProjectDao projectDao;
	
	//셋터 메서드
	public ProjectListController setProjectDao(MySqlProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	//DataBinding 인터페이스의 구현 메서드
	@Override
	public Object[] getDataBinders() {
		return new Object[] {"orderCond", String.class};
	}

	//Controller 인터페이스 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		//정렬조건 전달 Map
	  	paramMap.put("orderCond", model.get("orderCond"));
		model.put("projects", projectDao.selectList(paramMap));
		return "/project/ProjectList.jsp";
	}
	
}