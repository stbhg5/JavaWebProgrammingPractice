package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
public class MemberDeleteController implements Controller, DataBinding {//Map 객체에 저장할 파라미터에 대한 정보 제공 - DataBinding 인터페이스 구현
	MySqlMemberDao memberDao; //인스턴스 변수
	
	//셋터 메서드
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	//DataBinding 인터페이스의 구현 메서드
	public Object[] getDataBinders() {
		return new Object[] {"no", Integer.class};
	}
	
	//Controller 인터페이스의 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		Integer no = (Integer)model.get("no");
		memberDao.delete(no);
		return "redirect:list.do";
	}
}
