package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
@Component("/member/add.do") //Annotation 적용
public class MemberAddController implements Controller, DataBinding {//Map 객체에 저장할 파라미터에 대한 정보 제공 - DataBinding 인터페이스 구현
	MySqlMemberDao memberDao; //인스턴스 변수
	
	//셋터 메서드
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	//DataBinding 인터페이스의 구현 메서드
	public Object[] getDataBinders() {
		//클라이언트가 보낸 매개변수를 Member 인스턴스에 담아 "member"라는 이름으로 Map객체에 저장해라
		return new Object[] {"member", spms.vo.Member.class};
	}
	
	//Controller 인터페이스의 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		/*if(model.get("member") == null) {//입력폼을 요청할 때 - GET
			return "/member/MemberForm.jsp";
		}else {//회원 등록 요청할 때 - POST
			//MemberDao memberDao = (MemberDao)model.get("memberDao");
			Member member = (Member)model.get("member");
			memberDao.insert(member);
			return "redirect:list.do";
		}*/
		//프런트 컨트롤러가 VO 객체를 무조건 생성할 것이기 때문에 Member 객체 여부로 판단하지 않는다.
		Member member = (Member)model.get("member");
		if(member.getEmail() == null) {//입력폼을 요청할 때
			return "/member/MemberForm.jsp";
		}else {//회원 등록을 요청할 때
			memberDao.insert(member);
			return "redirect:list.do";
	    }
	}
}