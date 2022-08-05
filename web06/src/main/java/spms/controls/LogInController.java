package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
public class LogInController implements Controller, DataBinding {//Map 객체에 저장할 파라미터에 대한 정보 제공 - DataBinding 인터페이스 구현
	
	MySqlMemberDao memberDao; //인스턴스 변수
	
	//셋터 메서드
	public LogInController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	//DataBinding 인터페이스의 구현 메서드
	public Object[] getDataBinders() {
		return new Object[] {"loginInfo", spms.vo.Member.class};
	}
	
	//Controller 인터페이스의 구현 메서드
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		/*if(model.get("loginInfo") == null) {//입력폼을 요청할 때
			return "/auth/LogInForm.jsp";
		}else {//회원 로그인을 요청할 때
			//MemberDao memberDao = (MemberDao)model.get("memberDao"); 
			Member loginInfo = (Member)model.get("loginInfo"); //로그인 정보
			Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			if(member != null) {
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			}else {
				return "/auth/LogInFail.jsp";
			}
		}*/
		Member loginInfo = (Member)model.get("loginInfo");
	    if(loginInfo.getEmail() == null) {//입력폼을 요청할 때
	    	return "/auth/LogInForm.jsp";
	    }else {//회원 등록을 요청할 때
	    	Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
	    	if(member != null) {
	    		HttpSession session = (HttpSession)model.get("session");
	    		session.setAttribute("member", member);
	    		return "redirect:../member/list.do";
	    	}else {
	    		return "/auth/LogInFail.jsp";
	    	}
	    }
	}
}