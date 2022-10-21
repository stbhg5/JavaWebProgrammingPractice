package exam.test06;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		//IoC 컨테이너 준비하여 빈 생성
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test06/beans.xml");
		
		Score score1 = (Score)ctx.getBean("score1");
		System.out.println(score1);

		Score score2 = (Score)ctx.getBean("score2");
		System.out.println(score2);
	}
	
}