package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlProjectDao;
import spms.vo.Project;

@Component("/project/update.do")
public class ProjectUpdateController implements Controller, DataBinding {
	
	//인스턴스 변수
	MySqlProjectDao projectDao;
	
	//셋터 메서드
	public ProjectUpdateController setProjectDao(MySqlProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	//DataBinding 인터페이스 구현 메서드
	@Override
	public Object[] getDataBinders() {
		return new Object[] {"no", Integer.class, "project", spms.vo.Project.class};
	}

	//Controller 인터페이스 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//프런트 컨트롤러가 VO 객체를 무조건 생성할 것이기 때문에 Project 객체 여부로 판단하지 않는다.
		Project project = (Project)model.get("project");
		if(project.getTitle() == null) {//입력폼을 요청할 때
			Integer no = (Integer)model.get("no");
			Project detailInfo = projectDao.selectOne(no);
			model.put("project", detailInfo);
			return "/project/ProjectUpdateForm.jsp";
		}else {//프로젝트 수정을 요청할 때
			projectDao.update(project);
			return "redirect:list.do";
		}
	}

}