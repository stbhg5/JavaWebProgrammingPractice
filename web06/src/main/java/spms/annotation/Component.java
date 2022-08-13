package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//Component 어노테이션 속성 선언
@Retention(RetentionPolicy.RUNTIME) //어노테이션 유지 정책 : 어노테이션 정보를 언제까지 유지할 것인지 설정하는 문법
public @interface Component {
	String value() default ""; //value 속성의 값 지정하지 않으면 default의 ""값이 기본값
}