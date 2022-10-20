package exam.test04;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		//IoC 컨테이너 준비하여 빈 생성
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test04/beans.xml");
		
		Score score1 = (Score)ctx.getBean("score1");
		System.out.println(score1);
		//System.out.println(score1.name + ", " + score1.kor + ", " + score1.eng + ", " + score1.math);
		
		Score score2 = (Score)ctx.getBean("score2");
		System.out.println(score2);
		//System.out.println(score2.name + ", " + score2.kor + ", " + score2.eng + ", " + score2.math);
		
		Score score3 = (Score)ctx.getBean("score3");
		System.out.println(score3);
		//System.out.println(score3.name + ", " + score3.kor + ", " + score3.eng + ", " + score3.math);
		
		Score score4 = (Score)ctx.getBean("score4");
		System.out.println(score4);
		//System.out.println(score4.name + ", " + score4.kor + ", " + score4.eng + ", " + score4.math);
		
		Score score5 = (Score)ctx.getBean("score5");
		System.out.println(score5);
		//System.out.println(score5.name + ", " + score5.kor + ", " + score5.eng + ", " + score5.math);
	}
	
}