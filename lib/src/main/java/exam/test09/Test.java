package exam.test09;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String[] args) {
		//IoC 컨테이너 준비하여 빈 생성
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("exam/test09/beans.xml");
		
		Car car1 = (Car)ctx.getBean("car1");
		System.out.println(car1);
		for(Tire tire : car1.getTires()) {
			System.out.println(tire);
		}
	}
	
}