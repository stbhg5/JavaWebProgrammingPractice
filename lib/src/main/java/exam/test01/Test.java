package exam.test01;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		//IoC 컨테이너 준비하여 빈 생성
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test01/beans.xml");
		//빈 꺼내기
		Score score = (Score) ctx.getBean("score");
		
		System.out.println("합계:" + score.sum());
		System.out.println("평균:" + score.average());
	}
	
}
