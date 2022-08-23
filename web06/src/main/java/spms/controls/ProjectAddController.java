package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlProjectDao;
import spms.vo.Member;
import spms.vo.Project;

@Component("/project/add.do")
public class ProjectAddController implements Controller, DataBinding {
	
	//인스턴스 변수
	MySqlProjectDao projectDao;
	
	//셋터 메서드
	public ProjectAddController setProjectDao(MySqlProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}

	//DataBinding 인터페이스 구현 메서드
	@Override
	public Object[] getDataBinders() {
		//클라이언트가 보낸 매개변수를 Project 인스턴스에 담아 "project"라는 이름으로 Map객체에 저장해라
		return new Object[] {"project", spms.vo.Project.class};
	}

	//Controller 인터페이스 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//프런트 컨트롤러가 VO 객체를 무조건 생성할 것이기 때문에 Project 객체 여부로 판단하지 않는다.
		Project project = (Project)model.get("project");
		if(project.getTitle() == null) {//입력폼을 요청할 때
			return "/project/ProjectForm.jsp";
		}else {//프로젝트 등록을 요청할 때
			projectDao.insert(project);
			return "redirect:list.do";
		}
	}

}