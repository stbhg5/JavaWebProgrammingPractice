package spms.controls;

import java.util.HashMap;
import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;

@Component("/member/list.do")
public class MemberListController implements Controller, DataBinding {
	
	//인스턴스 변수
	MySqlMemberDao memberDao;
	
	//셋터 메서드
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
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
		//회원 목록 데이터를 Map 객체에 저장
		model.put("members", memberDao.selectList(paramMap));
		//화면을 출력할 JSP 페이지의 URL 반환
		return "/member/MemberList.jsp";
	}

}