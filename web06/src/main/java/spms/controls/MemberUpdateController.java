package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
public class MemberUpdateController implements Controller {
	MemberDao memberDao; //인스턴스 변수
	  
	public MemberUpdateController setMemberDao(MemberDao memberDao) {//셋터 메서드
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		if(model.get("member") == null) {//회원정보 상세조회 요청할 때
			Integer no = (Integer)model.get("no");
			Member member = memberDao.selectOne(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		}else {//회원 수정 요청할 때
			Member member = (Member)model.get("member");
			memberDao.update(member);
			return "redirect:list.do";
		}
	}
}
