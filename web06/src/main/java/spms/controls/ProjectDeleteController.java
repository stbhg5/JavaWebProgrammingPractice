package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlProjectDao;

@Component("/project/delete.do")
public class ProjectDeleteController implements Controller, DataBinding {
	
	//인스턴스 변수
	MySqlProjectDao projectDao;
	
	//셋터 메서드
	public ProjectDeleteController setProjectDao(MySqlProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}

	//DataBinding 인터페이스 구현 메서드
	@Override
	public Object[] getDataBinders() {
		return new Object[] {"no", Integer.class};
	}

	//Controller 인터페이스 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer no = (Integer)model.get("no");
		projectDao.delete(no);
		return "redirect:list.do";
	}

}