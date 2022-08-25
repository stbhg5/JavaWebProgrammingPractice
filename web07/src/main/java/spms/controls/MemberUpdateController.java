package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
@Component("/member/update.do") //Annotation 적용
public class MemberUpdateController implements Controller, DataBinding {//Map 객체에 저장할 파라미터에 대한 정보 제공 - DataBinding 인터페이스 구현
	MySqlMemberDao memberDao; //인스턴스 변수
	
	//셋터 메서드
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	//DataBinding 인터페이스의 구현 메서드
	public Object[] getDataBinders() {
		return new Object[] {"no", Integer.class, "member", spms.vo.Member.class};
	}
	
	//Controller 인터페이스의 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//MemberDao memberDao = (MemberDao)model.get("memberDao");
		/*if(model.get("member") == null) {//회원정보 상세조회 요청할 때
			Integer no = (Integer)model.get("no");
			Member member = memberDao.selectOne(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		}else {//회원 수정 요청할 때
			Member member = (Member)model.get("member");
			memberDao.update(member);
			return "redirect:list.do";
		}*/
		Member member = (Member)model.get("member");
		if(member.getEmail() == null) {//입력폼을 요청할 때
			Integer no = (Integer)model.get("no");
			Member detailInfo = memberDao.selectOne(no);
			model.put("member", detailInfo);
			return "/member/MemberUpdateForm.jsp";
	    }else {//회원 수정을 요청할 때
	    	memberDao.update(member);
	    	return "redirect:list.do";
	    }
	}
}
