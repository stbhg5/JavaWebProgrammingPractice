package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;

@Component("/auth/logout.do") //Annotation 적용
public class LogOutController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HttpSession session = (HttpSession)model.get("session");
		session.invalidate(); //세션 객체 무효화(객체 제거)
		return "redirect:login.do";
	}
}
