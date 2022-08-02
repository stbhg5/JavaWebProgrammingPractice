package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
public class MemberDeleteController implements Controller {
	MySqlMemberDao memberDao; //인스턴스 변수
	  
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {//셋터 메서드
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		Integer no = (Integer)model.get("no");
		memberDao.delete(no);
		return "redirect:list.do";
	}
}
