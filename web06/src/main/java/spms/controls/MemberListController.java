package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MySqlMemberDao;

//의존 객체 주입을 위해 인스턴스 변수와 셋터 메서드 추가, 의존 객체를 꺼내는 기존 코드 변경(주석처리)
@Component("/member/list.do") //Annotation 적용
public class MemberListController implements Controller {
	MySqlMemberDao memberDao; //인스턴스 변수
	  
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {//셋터 메서드
		this.memberDao = memberDao;
		return this; //셋터 메서드 쉽게 사용하기 위해 자신의 인스턴스 값 반환
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//Map 객체에서 MemberDao를 꺼냄
		//MemberDao memberDao = (MemberDao)model.get("memberDao");

		//회원 목록 데이터를 Map 객체에 저장
		model.put("members", memberDao.selectList());

		//화면을 출력할 JSP 페이지의 URL 반환
		return "/member/MemberList.jsp";
	}
}