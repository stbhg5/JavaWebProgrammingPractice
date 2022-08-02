package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
public class MemberAddController implements Controller {
	MySqlMemberDao memberDao; //인스턴스 변수
	  
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {//셋터 메서드
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {//입력폼을 요청할 때 - GET
			return "/member/MemberForm.jsp";
		}else {//회원 등록 요청할 때 - POST
			//MemberDao memberDao = (MemberDao)model.get("memberDao");
			Member member = (Member)model.get("member");
			memberDao.insert(member);
			return "redirect:list.do";
		}
	}
}